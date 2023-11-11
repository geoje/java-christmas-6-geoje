package christmas.constant;

public enum ReceiptMessage {
    TITLE_ORDER_MENU("주문 메뉴"),
    TITLE_AMOUNT_BEFORE_DISCOUNT("할인 전 총주문 금액"),
    TITLE_GIFT_MENU("증정 메뉴"),
    TITLE_BENEFIT_DETAILS("혜택 내역"),
    TITLE_TOTAL_BENEFIT_AMOUNT("총혜택 금액"),
    TITLE_AMOUNT_AFTER_DISCOUNT("할인 후 예상 결제 금액"),
    TITLE_EVENT_BADGE("%d월 이벤트 배지"),

    CONTENT_ORDER_MENU("%s %d개");

    private final String message;

    ReceiptMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public String toStringWithAngleBracket() {
        return String.format("<%s>", message);
    }
}
