package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.constant.GeneralMessage;

import static christmas.constant.GeneralMessage.REQUEST_ORDER;
import static christmas.constant.GeneralMessage.REQUEST_VISITING_DAY;

public class InputView {
    private static String readWithGeneralMessage(GeneralMessage message, Object... args) {
        System.out.printf(message.toString() + "%n", args);
        return Console.readLine().trim();
    }

    public static String readVisitingDay(int month) {
        return readWithGeneralMessage(REQUEST_VISITING_DAY, month);
    }

    public static String readOrder() {
        return readWithGeneralMessage(REQUEST_ORDER);
    }
}
