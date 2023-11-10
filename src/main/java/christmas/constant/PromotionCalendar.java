package christmas.constant;

import java.util.Calendar;

public enum PromotionCalendar {
    PERIOD_MONTH(2023, Calendar.DECEMBER, 1);

    private final Calendar calendar;

    PromotionCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        this.calendar = calendar;
    }

    public static boolean isDuringPromotion(Calendar calendar) {
        return calendar.get(Calendar.YEAR) == PERIOD_MONTH.calendar.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == PERIOD_MONTH.calendar.get(Calendar.MONTH);
    }

    public int get(int field) {
        return calendar.get(field);
    }

    public int getRealMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }
}
