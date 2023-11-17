package christmas.constant;

public enum MenuType {
    APPETIZER("애피타이저"),
    MAIN("메인"),
    DESSERT("디저트"),
    DRINK("음료");
    private final String name;

    MenuType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
