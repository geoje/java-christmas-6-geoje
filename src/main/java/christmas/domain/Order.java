package christmas.domain;

import christmas.constant.Menu;

import java.util.List;

public class Order {
    private List<Menu> menus;

    public Order(List<Menu> menus) {
        this.menus = menus;
    }

    public static Order from(String menus) {
        return new Order(List.of());
    }
}
