package christmas.domain;

import christmas.constant.Menu;
import christmas.constant.MenuType;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static christmas.constant.ReceiptMessage.CONTENT_NOTHING;
import static christmas.constant.ReceiptMessage.CONTENT_ORDER_MENU;

public class Menus {
    private final Map<Menu, Integer> menus;

    public Menus(Map<Menu, Integer> menus) {
        this.menus = menus;
    }

    public int totalCount() {
        return menus.values().stream().mapToInt(v -> v).sum();
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

    @Override
    public String toString() {
        if (menus.isEmpty()) {
            return CONTENT_NOTHING.toString();
        }
        return menus.entrySet().stream()
                .map(entry -> String.format(CONTENT_ORDER_MENU.toString(), entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
