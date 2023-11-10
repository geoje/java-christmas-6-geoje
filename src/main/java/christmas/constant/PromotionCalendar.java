package christmas.constant;

import java.util.Calendar;

public enum PromotionCalendar {
    PERIOD_MONTH(2023, Calendar.DECEMBER, 1);

    private final Calendar calendar;

    PromotionCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        this.calendar = calendar;
    }

    public boolean containDay(int day) {
        return calendar.getActualMinimum(Calendar.DAY_OF_MONTH) <= day &&
                day <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getRealMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }
}
