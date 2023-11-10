package christmas.controller;

import christmas.view.InputView;
import christmas.view.OutputView;

import static christmas.constant.PromotionCalendar.PERIOD_MONTH;

public class XmasController {
    public static void run() {
        OutputView.printIntroduction(PERIOD_MONTH.getRealMonth());
        InputView.askVisitingDay(PERIOD_MONTH.getRealMonth());
    }
}
