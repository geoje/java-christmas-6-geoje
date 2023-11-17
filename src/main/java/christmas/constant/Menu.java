package christmas.constant;

import java.util.Arrays;
import java.util.Optional;

public enum Menu {
    MUSHROOM_SOUP("양송이수프", MenuType.APPETIZER, 6000),
    TAPAS("타파스", MenuType.APPETIZER, 5500),
    CAESAR_SALAD("시저샐러드", MenuType.APPETIZER, 8000),

    T_BONE_STEAK("티본스테이크", MenuType.MAIN, 55000),
    BARBECUE_RIBS("바비큐립", MenuType.MAIN, 54000),
    SEAFOOD_PASTA("해산물파스타", MenuType.MAIN, 35000),
    CHRISTMAS_PASTA("크리스마스파스타", MenuType.MAIN, 25000),

    CHOCOLATE_CAKE("초코케이크", MenuType.DESSERT, 15000),
    ICE_CREAM("아이스크림", MenuType.DESSERT, 5000),

    ZERO_COKE("제로콜라", MenuType.DRINK, 3000),
    RED_WINE("레드와인", MenuType.DRINK, 60000),
    CHAMPAGNE("샴페인", MenuType.DRINK, 25000);

    private final String name;
    private final MenuType type;
    private final int price;

    Menu(String name, MenuType type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public static Optional<Menu> getByName(String name) {
        return Arrays.stream(values()).filter(menu -> menu.name.equals(name)).findFirst();
    }

    public String getName() {
        return name;
    }

    public MenuType getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }
}
