package christmas.controller;

import christmas.domain.Order;
import christmas.domain.VisitingDay;
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

        notifyPreview();
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

    private static void notifyPreview() {
        OutputView.printPreview(PERIOD_MONTH.getRealMonth(), PERIOD_MONTH.getDay());
    }

    private static void notifyOrder(Order order) {
        OutputView.printReceiptMessage(TITLE_ORDER_MENU, order.toString());
        OutputView.printReceiptMessage(TITLE_AMOUNT_BEFORE_DISCOUNT,
                String.format(CONTENT_AMOUNT.toString(), OutputView.formatAmount(order.totalAmount())));
    }

    private static void notifyPromotion(XmasPromotion promotion) {
        OutputView.printReceiptMessage(TITLE_GIFT_MENU, promotion.giftMenu().toString());
    }
}
