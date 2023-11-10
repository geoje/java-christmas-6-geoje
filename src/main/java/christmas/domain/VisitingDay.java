package christmas.domain;

import java.util.regex.Pattern;

import static christmas.constant.ErrorMessage.DAY_INVALID;
import static christmas.constant.ErrorMessage.DAY_NOT_NUMERIC;
import static christmas.constant.PromotionCalendar.PERIOD_MONTH;

public record VisitingDay(int day) {
    public VisitingDay {
        validateCanPeriodDay(day);
    }

    public static VisitingDay from(String day) {
        validateNumeric(day);
        return new VisitingDay(Integer.parseInt(day));
    }

    private static void validateNumeric(String day) {
        if (!Pattern.compile("-?\\d+").matcher(day).matches()) {
            throw new IllegalArgumentException(DAY_NOT_NUMERIC.toString());
        }
    }

    private static void validateCanPeriodDay(int day) {
        if (!PERIOD_MONTH.containDay(day)) {
            throw new IllegalArgumentException(DAY_INVALID.toString());
        }
    }
}
