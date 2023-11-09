package christmas.controller;

import christmas.view.InputView;
import christmas.view.OutputView;

public class XmasController {
    public static void run() {
        OutputView.printIntroduction();
        InputView.askVisitingDay();
    }
}
