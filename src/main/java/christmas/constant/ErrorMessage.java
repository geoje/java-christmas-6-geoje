package christmas.constant;

public enum ErrorMessage {
    EXCEPTION_PREFIX("[ERROR] "),
    DAY_NOT_NUMERIC("숫자가 아닙니다. 다시 입력해 주세요."),
    DAY_INVALID("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    ORDER_INVALID("유효하지 않은 주문입니다. 다시 입력해 주세요.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}