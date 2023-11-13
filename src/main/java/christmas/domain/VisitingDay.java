package christmas.domain;

import christmas.constant.PromotionCalendar;

import java.util.Calendar;
import java.util.regex.Pattern;

import static christmas.constant.ErrorMessage.DAY_INVALID;
import static christmas.constant.PromotionCalendar.PERIOD_MONTH;

public record VisitingDay(int day) {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+");

    public VisitingDay {
        validateCanPeriodDay(day);
    }

    public static VisitingDay from(String day) {
        validateNumeric(day);
        return new VisitingDay(Integer.parseInt(day));
    }

    private static void validateNumeric(String day) {
        if (!NUMBER_PATTERN.matcher(day).matches()) {
            throw new IllegalArgumentException(DAY_INVALID.toString());
        }
    }

    private static void validateCanPeriodDay(int day) {
        if (!PERIOD_MONTH.containDay(day)) {
            throw new IllegalArgumentException(DAY_INVALID.toString());
        }
    }

    public boolean isWeekend() {
        Calendar calendar = PromotionCalendar.getPeriodCalendarWithDay(day);
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }

    public boolean isWeekday() {
        return !isWeekend();
    }
}
