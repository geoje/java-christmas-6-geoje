package christmas.domain.promotion;

import christmas.domain.Order;
import christmas.domain.VisitingDay;

import java.util.Map;

import static christmas.constant.Menu.CHAMPAGNE;

public class XmasPromotion {
    private static final int AMOUNT_GIFT_CONDITION = 120000;

    private final VisitingDay visitingDay;
    private final Order order;

    public XmasPromotion(VisitingDay visitingDay, Order order) {
        this.visitingDay = visitingDay;
        this.order = order;
    }

    public Order giftMenu() {
        if (order.totalAmount() >= AMOUNT_GIFT_CONDITION) {
            return new Order(Map.of(CHAMPAGNE, 1));
        }
        return new Order(Map.of());
    }
}
