package christmas.domain;

import christmas.constant.Menu;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static christmas.constant.ErrorMessage.*;
import static christmas.constant.ReceiptMessage.CONTENT_ORDER_MENU;

public class Order {
    private static String MENU_ENTRY_DELIMITER = ",";
    private static String MENU_NAME_COUNT_DELIMITER = "-";
    private Map<String, Integer> menus;

    public Order(Map<String, Integer> menus) {
        this.menus = menus;
    }

    public static Order from(String menus) {
        List<Map.Entry<String, Integer>> entries = Arrays.stream(menus.split(MENU_ENTRY_DELIMITER))
                .filter(menu -> !menu.isBlank())
                .map(Order::parseNameAndCountWithValidation)
                .toList();
        return new Order(convertEntriesToMapWithValidation(entries));
    }

    private static Map.Entry<String, Integer> parseNameAndCountWithValidation(String menu) {
        validateNameCountDelimiter(menu);
        int indexDelimiter = menu.lastIndexOf(MENU_NAME_COUNT_DELIMITER);
        String name = menu.substring(0, indexDelimiter);
        String count = menu.substring(indexDelimiter + 1);

        validateNumeric(count);
        int parsedCount = Integer.parseInt(count);

        validateCount(parsedCount);
        validateExistMenu(name);
        return Map.entry(name, parsedCount);
    }

    private static Map<String, Integer> convertEntriesToMapWithValidation(List<Map.Entry<String, Integer>> menus) {
        validateNotDuplicated(menus);
        return menus.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static void validateNameCountDelimiter(String menu) {
        if (!menu.contains(MENU_NAME_COUNT_DELIMITER)) {
            throw new IllegalArgumentException(ORDER_NO_HYPHEN.toString());
        }
    }

    private static void validateNumeric(String count) {
        if (!Pattern.compile("-?\\d+").matcher(count).matches()) {
            throw new IllegalArgumentException(ORDER_COUNT_NOT_NUMERIC.toString());
        }
    }

    private static void validateCount(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException(ORDER_INVALID.toString());
        }
    }

    private static void validateExistMenu(String name) {
        if (Menu.getByName(name).isEmpty()) {
            throw new IllegalArgumentException(ORDER_INVALID.toString());
        }
    }

    private static void validateNotDuplicated(List<Map.Entry<String, Integer>> entries) {
        if (entries.stream().map(Map.Entry::getKey).collect(Collectors.toSet()).size()
                != entries.size()) {
            throw new IllegalArgumentException(ORDER_INVALID.toString());
        }
    }

    @Override
    public String toString() {
        return menus.entrySet().stream()
                .map(entry -> String.format(CONTENT_ORDER_MENU.toString(), entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
