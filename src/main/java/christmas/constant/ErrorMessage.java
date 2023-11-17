package christmas.constant;

public enum ErrorMessage {
    EXCEPTION_PREFIX("[ERROR] "),
    DAY_INVALID("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    ORDER_INVALID("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    ORDER_MENU_COUNT_EXCEED("메뉴는 총 20개 까지만 주문할 수 있습니다. 다시 입력해 주세요.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
