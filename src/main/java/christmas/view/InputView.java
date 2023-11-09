package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.constant.GeneralMessage;

import static christmas.constant.GeneralMessage.ASK_VISITING_DAY;

public class InputView {
    private static String readWithGeneralMessage(GeneralMessage message) {
        System.out.println(message.toString());
        return Console.readLine().trim();
    }

    public static String askVisitingDay() {
        return readWithGeneralMessage(ASK_VISITING_DAY);
    }
}
