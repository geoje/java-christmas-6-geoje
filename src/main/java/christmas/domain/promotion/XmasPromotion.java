package christmas.domain.promotion;

import christmas.constant.MenuType;
import christmas.constant.ReceiptMessage;
import christmas.domain.Order;
import christmas.domain.VisitingDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static christmas.constant.Menu.CHAMPAGNE;
import static christmas.constant.ReceiptMessage.*;

public class XmasPromotion {
    private static final int AMOUNT_GIFT_CONDITION = 120000;
    private static final int DISCOUNT_WEEKDAY_OR_WEEKEND = 2023;
    private static final int DAY_END_OF_D_DAY_DISCOUNT = 25;
    private static final int AMOUNT_DEFAULT_D_DAY_DISCOUNT = 1000;
    private static final int AMOUNT_PER_DAY_D_DAY_DISCOUNT = 100;
    private static final List<Integer> SPECIAL_DAYS = List.of(3, 10, 17, 24, 25, 31);
    private static final int AMOUNT_SPECIAL_DAY_DISCOUNT = 1000;

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

    public Map<ReceiptMessage, Integer> benefits() {
        Map<ReceiptMessage, Integer> result = new HashMap<>();

        getMenuDiscount().forEach(entry -> result.put(entry.getKey(), entry.getValue()));
        getTotalDiscount().forEach(entry -> result.put(entry.getKey(), entry.getValue()));
        if (SPECIAL_DAYS.contains(visitingDay.day())) {
            result.put(BENEFIT_SPECIAL, AMOUNT_SPECIAL_DAY_DISCOUNT);
        }

        return result;
    }

    private List<Map.Entry<ReceiptMessage, Integer>> getMenuDiscount() {
        List<Map.Entry<ReceiptMessage, Integer>> discounts = new ArrayList<>();

        if (visitingDay.isWeekday()) {
            discounts.add(Map.entry(BENEFIT_WEEKDAY,
                    DISCOUNT_WEEKDAY_OR_WEEKEND * order.countMenuType(MenuType.DESSERT)));
        }
        if (visitingDay.isWeekend()) {
            discounts.add(Map.entry(BENEFIT_WEEKEND,
                    DISCOUNT_WEEKDAY_OR_WEEKEND * order.countMenuType(MenuType.MAIN)));
        }
        return discounts;
    }

    private List<Map.Entry<ReceiptMessage, Integer>> getTotalDiscount() {
        List<Map.Entry<ReceiptMessage, Integer>> discounts = new ArrayList<>();

        if (visitingDay.day() <= DAY_END_OF_D_DAY_DISCOUNT) {
            discounts.add(Map.entry(BENEFIT_D_DAY, AMOUNT_DEFAULT_D_DAY_DISCOUNT +
                    AMOUNT_PER_DAY_D_DAY_DISCOUNT * (visitingDay.day() - 1)));
        }
        if (SPECIAL_DAYS.contains(visitingDay.day())) {
            discounts.add(Map.entry(BENEFIT_SPECIAL, AMOUNT_SPECIAL_DAY_DISCOUNT));
        }
        return discounts;
    }

    public int amountTotalBenefits() {
        return benefits().values().stream().mapToInt(v -> v).sum();
    }

    public int amountAfterDiscount() {
        Map<ReceiptMessage, Integer> benefits = benefits();
        benefits.remove(TITLE_GIFT_MENU);
        return order.totalAmount() - benefits.values().stream().mapToInt(v -> v).sum();
    }
}
