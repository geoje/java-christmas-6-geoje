package christmas.view;

import christmas.constant.GeneralMessage;
import christmas.constant.ReceiptTitle;

import static christmas.constant.ErrorMessage.EXCEPTION_PREFIX;
import static christmas.constant.GeneralMessage.NOTIFY_INTRODUCTION;
import static christmas.constant.GeneralMessage.NOTIFY_PREVIEW;

public class OutputView {
    private static void printGeneralMessage(GeneralMessage message, Object... args) {
        System.out.printf(message + "%n", args);
    }

    private static void printErrorMessage(String message) {
        System.out.println(EXCEPTION_PREFIX + message);
    }

    public static void printIntroduction(int month) {
        printGeneralMessage(NOTIFY_INTRODUCTION, month);
    }

    public static void printPreview(int month, int day) {
        printGeneralMessage(NOTIFY_PREVIEW, month, day);
    }

    public static void printReceipt(ReceiptTitle title, String message) {
        System.out.printf("<%s>%n%s%n%n", title, message);
    }
}
