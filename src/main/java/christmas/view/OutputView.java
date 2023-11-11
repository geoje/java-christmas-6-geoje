package christmas.view;

import christmas.constant.GeneralMessage;
import christmas.constant.ReceiptMessage;

import java.text.DecimalFormat;

import static christmas.constant.ErrorMessage.EXCEPTION_PREFIX;
import static christmas.constant.GeneralMessage.NOTIFY_INTRODUCTION;
import static christmas.constant.GeneralMessage.NOTIFY_PREVIEW;

public class OutputView {
    private static final DecimalFormat formatter = new DecimalFormat("#,###");

    private static void printGeneralMessage(GeneralMessage message, Object... args) {
        System.out.printf(message + "%n", args);
    }

    public static void printReceiptMessage(ReceiptMessage titleMessage, String content, Object... args) {
        System.out.printf(titleMessage.toStringWithAngleBracket() + "%n", args);
        System.out.printf("%s%n%n", content);
    }

    public static void printErrorMessage(String message) {
        System.out.println(EXCEPTION_PREFIX + message);
    }

    public static void printIntroduction(int month) {
        printGeneralMessage(NOTIFY_INTRODUCTION, month);
    }

    public static void printPreview(int month, int day) {
        printGeneralMessage(NOTIFY_PREVIEW, month, day);
        System.out.println();
    }

    public static String formatAmount(int amount) {
        return formatter.format(amount);
    }
}
