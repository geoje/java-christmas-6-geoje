package christmas.domain.promotion;

import christmas.constant.MenuType;
import christmas.constant.ReceiptMessage;
import christmas.domain.Menus;
import christmas.domain.Order;
import christmas.domain.VisitingDay;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private static final DecimalFormat FORMATTER = new DecimalFormat("#,###");

    private final VisitingDay visitingDay;
    private final Order order;

    public XmasPromotion(VisitingDay visitingDay, Order order) {
        this.visitingDay = visitingDay;
        this.order = order;
    }

    private static String formatAmount(int amount) {
        return String.format(CONTENT_AMOUNT.toString(), FORMATTER.format(amount));
    }

    public Menus giftMenu() {
        if (order.menus().totalAmount() >= AMOUNT_GIFT_CONDITION) {
            return new Menus(Map.of(CHAMPAGNE, 1));
        }
        return new Menus(Map.of());
    }

    public Map<ReceiptMessage, Integer> benefits() {
        Map<ReceiptMessage, Integer> result = new HashMap<>();

        findMenuDiscount().forEach(entry -> result.put(entry.getKey(), entry.getValue()));
        findTotalDiscount().forEach(entry -> result.put(entry.getKey(), entry.getValue()));
        Menus giftMenu = giftMenu();
        if (giftMenu.totalAmount() > 0) {
            result.put(BENEFIT_GIFT, giftMenu().totalAmount());
        }

        return result;
    }

    private List<Map.Entry<ReceiptMessage, Integer>> findMenuDiscount() {
        List<Map.Entry<ReceiptMessage, Integer>> discounts = new ArrayList<>();

        int dessertCount = order.menus().countMenuType(MenuType.DESSERT);
        int mainCount = order.menus().countMenuType(MenuType.MAIN);
        if (visitingDay.isWeekdayDiscount() && dessertCount > 0) {
            discounts.add(Map.entry(BENEFIT_WEEKDAY, DISCOUNT_WEEKDAY_OR_WEEKEND * dessertCount));
        }
        if (visitingDay.isWeekendDiscount() && mainCount > 0) {
            discounts.add(Map.entry(BENEFIT_WEEKEND, DISCOUNT_WEEKDAY_OR_WEEKEND * mainCount));
        }
        return discounts;
    }

    private List<Map.Entry<ReceiptMessage, Integer>> findTotalDiscount() {
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

    public int amountBenefits() {
        return benefits().values().stream().mapToInt(v -> v).sum();
    }

    public int amountAfterDiscount() {
        Map<ReceiptMessage, Integer> benefits = benefits();
        benefits.remove(TITLE_GIFT_MENU);
        return order.menus().totalAmount() - benefits().values().stream().mapToInt(v -> v).sum();
    }

    public String buildBenefitsAsString() {
        Map<ReceiptMessage, Integer> benefits = benefits();
        if (benefits.isEmpty()) {
            return CONTENT_NOTHING.toString();
        }
        return benefits.entrySet().stream()
                .map(entry -> String.format(CONTENT_BENEFIT.toString(),
                        entry.getKey().toString(),
                        formatAmount(-entry.getValue())))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String buildAmountBenefitsAsString() {
        return formatAmount(-amountBenefits());
    }

    public String buildAmountAfterDiscountAsString() {
        return formatAmount(amountAfterDiscount());
    }
}
