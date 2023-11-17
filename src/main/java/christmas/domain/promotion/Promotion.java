package christmas.domain.promotion;

import christmas.constant.Badge;
import christmas.constant.ReceiptMessage;
import christmas.domain.Menus;
import christmas.domain.Order;
import christmas.domain.VisitingDay;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.stream.Collectors;

import static christmas.constant.ReceiptMessage.*;

public abstract class Promotion {
    private static final DecimalFormat FORMATTER = new DecimalFormat("#,###");

    protected final VisitingDay visitingDay;
    protected final Order order;

    public Promotion(VisitingDay visitingDay, Order order) {
        this.visitingDay = visitingDay;
        this.order = order;
    }

    protected static String formatAmount(int amount) {
        return String.format(CONTENT_AMOUNT.toString(), FORMATTER.format(amount));
    }

    public abstract Menus giftMenu();

    public abstract Map<ReceiptMessage, Integer> benefits();

    public abstract Badge badge();

    public int amountBenefits() {
        return benefits().values().stream().mapToInt(v -> v).sum();
    }

    public int amountAfterDiscount() {
        Map<ReceiptMessage, Integer> benefitsWithoutGift = benefits();
        benefitsWithoutGift.remove(BENEFIT_GIFT);
        return order.menus().totalAmount() - benefitsWithoutGift.values().stream().mapToInt(v -> v).sum();
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
