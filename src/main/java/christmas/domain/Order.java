package christmas.domain;

import christmas.constant.Menu;
import christmas.constant.MenuType;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static christmas.constant.ErrorMessage.ORDER_INVALID;
import static christmas.constant.ErrorMessage.ORDER_MENU_COUNT_EXCEED;
import static christmas.constant.ReceiptMessage.CONTENT_AMOUNT;

public record Order(Menus menus) {
    private static final String MENU_ENTRY_DELIMITER = ",";
    private static final String MENU_NAME_COUNT_DELIMITER = "-";
    private static final int MAX_MENU_COUNT = 20;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+");
    private static final DecimalFormat FORMATTER = new DecimalFormat("#,###");

    public Order {
        validateMaxMenuCount(menus);
        validateNotOnlyDrink(menus);
    }

    public static Order from(String menus) {
        List<Map.Entry<Menu, Integer>> entries = Arrays.stream(menus.split(MENU_ENTRY_DELIMITER))
                .filter(menu -> !menu.isBlank())
                .map(Order::parseNameAndCountWithValidation)
                .toList();
        return new Order(convertListToMapWithValidation(entries));
    }

    private static Map.Entry<Menu, Integer> parseNameAndCountWithValidation(String menu) {
        validateNameCountDelimiter(menu);
        int indexDelimiter = menu.lastIndexOf(MENU_NAME_COUNT_DELIMITER);
        String name = menu.substring(0, indexDelimiter);
        String count = menu.substring(indexDelimiter + 1);

        validateNumeric(count);
        int parsedCount = Integer.parseInt(count);

        validateCount(parsedCount);
        validateExistMenu(name); // Menu.getByName(name).isPresent() checked here
        return Map.entry(Menu.getByName(name).get(), parsedCount);
    }

    private static Menus convertListToMapWithValidation(List<Map.Entry<Menu, Integer>> menus) {
        validateNotDuplicated(menus);
        return new Menus(menus.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    private static void validateNameCountDelimiter(String menu) {
        if (!menu.contains(MENU_NAME_COUNT_DELIMITER)) {
            throw new IllegalArgumentException(ORDER_INVALID.toString());
        }
    }

    private static void validateNumeric(String count) {
        if (!NUMBER_PATTERN.matcher(count).matches()) {
            throw new IllegalArgumentException(ORDER_INVALID.toString());
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

    private static void validateNotDuplicated(List<Map.Entry<Menu, Integer>> entries) {
        if (entries.stream().map(Map.Entry::getKey).collect(Collectors.toSet()).size()
                != entries.size()) {
            throw new IllegalArgumentException(ORDER_INVALID.toString());
        }
    }

    private static void validateNotOnlyDrink(Menus menus) {
        if (menus.totalCount() > 0 && menus.totalCount() == menus.countMenuType(MenuType.DRINK)) {
            throw new IllegalArgumentException(ORDER_INVALID.toString());
        }
    }

    private static void validateMaxMenuCount(Menus menus) {
        if (menus.totalCount() > MAX_MENU_COUNT) {
            throw new IllegalArgumentException(ORDER_MENU_COUNT_EXCEED.toString());
        }
    }

    public String buildTotalAmountAsString() {
        return String.format(CONTENT_AMOUNT.toString(), FORMATTER.format(menus.totalAmount()));
    }

    @Override
    public String toString() {
        return menus.toString();
    }
}
