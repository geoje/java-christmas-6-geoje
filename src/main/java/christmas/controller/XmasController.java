package christmas.controller;

import christmas.domain.Order;
import christmas.domain.VisitingDay;
import christmas.domain.promotion.Promotion;
import christmas.domain.promotion.XmasPromotion;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.function.Supplier;

import static christmas.constant.PromotionCalendar.PERIOD_MONTH;
import static christmas.constant.ReceiptMessage.*;

public class XmasController {
    public static void run() {
        notifyIntroduction();
        VisitingDay visitingDay = requestVisitingDay();
        Order order = requestOrder();

        notifyPreview(visitingDay);
        notifyOrder(order);
        notifyPromotion(new XmasPromotion(visitingDay, order));
    }

    private static <T> T requestUntilValidated(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private static void notifyIntroduction() {
        OutputView.printIntroduction(PERIOD_MONTH.getRealMonth());
    }

    private static VisitingDay requestVisitingDay() {
        return requestUntilValidated(
                () -> VisitingDay.from(InputView.readVisitingDay(PERIOD_MONTH.getRealMonth())));
    }

    private static Order requestOrder() {
        return requestUntilValidated(() -> Order.from(InputView.readOrder()));
    }

    private static void notifyPreview(VisitingDay visitingDay) {
        OutputView.printPreview(PERIOD_MONTH.getRealMonth(), visitingDay.day());
    }

    private static void notifyOrder(Order order) {
        OutputView.printReceiptMessage(TITLE_ORDER_MENU, order.toString());
        OutputView.printReceiptMessage(TITLE_AMOUNT_BEFORE_DISCOUNT, order.buildTotalAmountAsString());
    }

    private static void notifyPromotion(Promotion promotion) {
        OutputView.printReceiptMessage(TITLE_GIFT_MENU, promotion.giftMenu().toString());
        OutputView.printReceiptMessage(TITLE_BENEFIT_DETAILS, promotion.buildBenefitsAsString());
        OutputView.printReceiptMessage(TITLE_TOTAL_BENEFIT_AMOUNT, promotion.buildAmountBenefitsAsString());
        OutputView.printReceiptMessage(TITLE_AMOUNT_AFTER_DISCOUNT, promotion.buildAmountAfterDiscountAsString());
        OutputView.printReceiptMessage(TITLE_EVENT_BADGE, promotion.badge().toString(), PERIOD_MONTH.getRealMonth());
    }
}
