package christmas.constant;

import java.util.Calendar;

public enum PromotionCalendar {
    START(2023, Calendar.DECEMBER, 1),
    END(2023, Calendar.DECEMBER, 31);

    private final Calendar calendar;

    PromotionCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        this.calendar = calendar;
    }

    public static boolean isDuringPromotion(Calendar calendar) {
        return calendar.compareTo(START.calendar) >= 0 && calendar.compareTo(END.calendar) <= 0;
    }

    public int get(int field) {
        return calendar.get(field);
    }
}
