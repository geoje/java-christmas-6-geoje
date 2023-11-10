package christmas.constant;

public enum GeneralMessage {
    NOTIFY_INTRODUCTION("안녕하세요! 우테코 식당 %d월 이벤트 플래너입니다."),
    NOTIFY_PREVIEW("%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"),

    ASK_VISITING_DAY("%d월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"),
    ASK_ORDER("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)"),

    TITLE_ORDER_MENU("<주문 메뉴>"),
    TITLE_AMOUNT_BEFORE_DISCOUNT("<할인 전 총주문 금액>"),
    TITLE_GIFT_MENU("<증정 메뉴>"),
    TITLE_BENEFIT_DETAILS("<혜택 내역>"),
    TITLE_TOTAL_BENEFIT_AMOUNT("<총혜택 금액>"),
    TITLE_AMOUNT_AFTER_DISCOUNT("<할인 후 예상 결제 금액>"),
    TITLE_EVENT_BADGE("<%d월 이벤트 배지>");

    private final String message;

    GeneralMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
