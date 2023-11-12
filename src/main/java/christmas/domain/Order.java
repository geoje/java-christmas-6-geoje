package christmas.domain;

import christmas.constant.Menu;
import christmas.constant.MenuType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static christmas.constant.ErrorMessage.ORDER_INVALID;
import static christmas.constant.ErrorMessage.ORDER_MENU_COUNT_EXCEED;
import static christmas.constant.ReceiptMessage.CONTENT_NOTHING;
import static christmas.constant.ReceiptMessage.CONTENT_ORDER_MENU;

public class Order {
    private static final String MENU_ENTRY_DELIMITER = ",";
    private static final String MENU_NAME_COUNT_DELIMITER = "-";
    private static final int MAX_MENU_COUNT = 20;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+");

    private final Map<Menu, Integer> menus;

    public Order(Map<Menu, Integer> menus) {
        validateMaxMenuCount(menus);
        validateNotOnlyDrink(menus);
        this.menus = menus;
    }

    public static Order from(String menus) {
        List<Map.Entry<Menu, Integer>> entries = Arrays.stream(menus.split(MENU_ENTRY_DELIMITER))
                .filter(menu -> !menu.isBlank())
                .map(Order::parseNameAndCountWithValidation)
                .toList();
        return new Order(convertEntriesToMapWithValidation(entries));
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

    private static Map<Menu, Integer> convertEntriesToMapWithValidation(List<Map.Entry<Menu, Integer>> menus) {
        validateNotDuplicated(menus);
        return menus.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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

    private static void validateNotOnlyDrink(Map<Menu, Integer> menus) {
        if (!menus.isEmpty() && menus.keySet().stream().allMatch(menu -> menu.getType().equals(MenuType.DRINK))) {
            throw new IllegalArgumentException(ORDER_INVALID.toString());
        }
    }

    private static void validateMaxMenuCount(Map<Menu, Integer> menus) {
        if (menus.values().stream().mapToInt(Integer::intValue).sum() > MAX_MENU_COUNT) {
            throw new IllegalArgumentException(ORDER_MENU_COUNT_EXCEED.toString());
        }
    }

    @Override
    public String toString() {
        if (menus.isEmpty()) {
            return CONTENT_NOTHING.toString();
        }
        return menus.entrySet().stream()
                .map(entry -> String.format(CONTENT_ORDER_MENU.toString(), entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public int totalAmount() {
        AtomicInteger totalAmount = new AtomicInteger();
        menus.forEach((menu, count) -> totalAmount.addAndGet(menu.getPrice() * count));
        return totalAmount.get();
    }

    public int countMenuType(MenuType type) {
        return menus.entrySet().stream()
                .filter(entry -> entry.getKey().getType().equals(type))
                .mapToInt(Map.Entry::getValue)
                .sum();
    }
}
