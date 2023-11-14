package christmas.constant;

import java.util.Calendar;

public enum PromotionCalendar {
    PERIOD_MONTH(2024, Calendar.JANUARY, 1);

    private final Calendar calendar;

    PromotionCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        this.calendar = calendar;
    }

    public static Calendar getPeriodCalendarWithDay(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(PERIOD_MONTH.getYear(),
                PERIOD_MONTH.getRealMonth() - 1,
                day,
                0,
                0,
                0);
        return calendar;
    }

    public boolean containDay(int day) {
        return calendar.getActualMinimum(Calendar.DAY_OF_MONTH) <= day &&
                day <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public int getRealMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }
}
