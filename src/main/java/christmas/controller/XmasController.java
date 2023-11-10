package christmas.controller;

import christmas.view.InputView;
import christmas.view.OutputView;

import static christmas.constant.PromotionCalendar.PERIOD_MONTH;

public class XmasController {
    public static void run() {
        notifyIntroduction();
        requestVisitingDay();
        requestOrder();
        notifyPreview();
        notifyPromotions();
    }

    private static void notifyIntroduction() {
        OutputView.printIntroduction(PERIOD_MONTH.getRealMonth());
    }

    private static void requestVisitingDay() {
        InputView.askVisitingDay(PERIOD_MONTH.getRealMonth());
    }

    private static void requestOrder() {
        InputView.askOrder();
    }

    private static void notifyPreview() {
        OutputView.printPreview(PERIOD_MONTH.getRealMonth(), PERIOD_MONTH.getDay());
    }

    private static void notifyPromotions() {
    }
}
