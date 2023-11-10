package christmas.constant;

public enum SpecsTitle {
    ORDER_MENU("주문 메뉴"),
    AMOUNT_BEFORE_DISCOUNT("할인 전 총주문 금액"),
    GIFT_MENU("증정 메뉴"),
    BENEFIT_DETAILS("혜택 내역"),
    TOTAL_BENEFIT_AMOUNT("총혜택 금액"),
    AMOUNT_AFTER_DISCOUNT("할인 후 예상 결제 금액"),
    EVENT_BADGE("%d월 이벤트 배지");

    private final String message;

    SpecsTitle(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
