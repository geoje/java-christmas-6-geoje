package christmas.controller;

import christmas.domain.Order;
import christmas.domain.VisitingDay;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.function.Supplier;

import static christmas.constant.PromotionCalendar.PERIOD_MONTH;

public class XmasController {
    public static void run() {
        notifyIntroduction();
        VisitingDay visitingDay = requestVisitingDay();
        Order order = requestOrder();
        notifyPreview();
        notifyPromotions(visitingDay, order);
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
                () -> VisitingDay.from(InputView.askVisitingDay(PERIOD_MONTH.getRealMonth())));
    }

    private static Order requestOrder() {
        return requestUntilValidated(() -> Order.from(InputView.askOrder()));
    }

    private static void notifyPreview() {
        OutputView.printPreview(PERIOD_MONTH.getRealMonth(), PERIOD_MONTH.getDay());
    }

    private static void notifyPromotions(VisitingDay visitingDay, Order order) {
    }
}
