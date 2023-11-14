package christmas.domain.promotion;

import christmas.constant.Badge;
import christmas.constant.Menu;
import christmas.constant.MenuType;
import christmas.constant.ReceiptMessage;
import christmas.domain.Menus;
import christmas.domain.Order;
import christmas.domain.VisitingDay;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static christmas.constant.Badge.*;
import static christmas.constant.ReceiptMessage.BENEFIT_DRINK_FRIDAY;
import static christmas.constant.ReceiptMessage.BENEFIT_GIFT;

public class NewyearPromotion extends Promotion {
    private static final int AMOUNT_GIFT_CONDITION = 20000;
    private static final int DISCOUNT_DRINK_FRIDAY = 1024;

    private final Badge xmasBadge;

    public NewyearPromotion(VisitingDay visitingDay, Order order, Badge xmasBadge) {
        super(visitingDay, order);
        this.xmasBadge = xmasBadge;
    }

    @Override
    public Menus giftMenu() {
        Map<Menu, Integer> result = new HashMap<>();

        if (order.menus().totalAmount() >= AMOUNT_GIFT_CONDITION) {
            result.put(Menu.ZERO_COKE, 1);
        }
        if (order.menus().totalAmount() >= AMOUNT_GIFT_CONDITION * 4 &&
                xmasBadge == SANTA) {
            result.put(Menu.CHOCOLATE_CAKE, 1);
        }
        if (order.menus().totalAmount() >= AMOUNT_GIFT_CONDITION * 2 &&
                xmasBadge == TREE) {
            result.put(Menu.ICE_CREAM, 2);
        }
        if (order.menus().totalAmount() >= AMOUNT_GIFT_CONDITION &&
                xmasBadge == STAR) {
            result.put(Menu.ICE_CREAM, 1);
        }
        return new Menus(result);
    }

    @Override
    public Map<ReceiptMessage, Integer> benefits() {
        Map<ReceiptMessage, Integer> result = new HashMap<>();

        if (visitingDay.isDayOfWeek(Calendar.FRIDAY)) {
            result.put(BENEFIT_DRINK_FRIDAY, DISCOUNT_DRINK_FRIDAY * order.menus().countMenuType(MenuType.DRINK));
        }
        if (giftMenu().totalAmount() > 0) {
            result.put(BENEFIT_GIFT, giftMenu().totalAmount());
        }

        return result;
    }

    @Override
    public Badge badge() {
        return NOTHING;
    }
}
