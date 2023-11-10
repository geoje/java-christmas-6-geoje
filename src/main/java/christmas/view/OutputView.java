package christmas.view;

import christmas.constant.GeneralMessage;

import static christmas.constant.GeneralMessage.NOTIFY_INTRODUCTION;

public class OutputView {
    private static void printGeneralMessage(GeneralMessage message, Object... args) {
        System.out.printf(message.toString() + System.lineSeparator(), args);
    }

    public static void printIntroduction(int month) {
        printGeneralMessage(NOTIFY_INTRODUCTION, month);
    }
}
