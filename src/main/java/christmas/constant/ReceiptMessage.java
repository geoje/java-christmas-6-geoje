package christmas.constant;

public enum ReceiptMessage {
    TITLE_ORDER_MENU("주문 메뉴"),
    TITLE_AMOUNT_BEFORE_DISCOUNT("할인 전 총주문 금액"),
    TITLE_GIFT_MENU("증정 메뉴"),
    TITLE_BENEFIT_DETAILS("혜택 내역"),
    TITLE_TOTAL_BENEFIT_AMOUNT("총혜택 금액"),
    TITLE_AMOUNT_AFTER_DISCOUNT("할인 후 예상 결제 금액"),
    TITLE_EVENT_BADGE("%d월 이벤트 배지"),

    CONTENT_ORDER_MENU("%s %d개"),
    CONTENT_AMOUNT("%s원"),
    CONTENT_BENEFIT("%s: %s"),
    CONTENT_NOTHING("없음"),

    BENEFIT_D_DAY("크리스마스 디데이 할인"),
    BENEFIT_WEEKDAY("평일 할인"),
    BENEFIT_WEEKEND("주말 할인"),
    BENEFIT_SPECIAL("특별 할인"),
    BENEFIT_GIFT("증정 이벤트"),

    BADGE_START("별"),
    BADGE_TREE("트리"),
    BADGE_SANTA("산타");

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
