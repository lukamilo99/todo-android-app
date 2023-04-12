package rs.raf.to_do_app.util;

import java.sql.Date;
import java.util.Calendar;

public class DateUtil {

    public static int getDayOfMonth(int dayOfYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static int getMonthOfYear(int dayOfYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return calendar.get(Calendar.MONTH);
    }
}
