package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.constant.GeneralMessage;

import static christmas.constant.GeneralMessage.ASK_VISITING_DAY;

public class InputView {
    private static String readWithGeneralMessage(GeneralMessage message, Object... args) {
        System.out.printf(message.toString() + System.lineSeparator(), args);
        return Console.readLine().trim();
    }

    public static String askVisitingDay(int month) {
        return readWithGeneralMessage(ASK_VISITING_DAY, month);
    }
}
