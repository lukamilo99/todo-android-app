package rs.raf.to_do_app.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Calendar;

import rs.raf.to_do_app.model.Task;

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

    public static String getDate(int dayOfYear) {
        LocalDate date = LocalDate.ofYearDay(LocalDate.now().getYear(), dayOfYear);
        Month month = date.getMonth();
        int day = date.getDayOfMonth();
        return day +  "." + month.getValue() + ".2023.";
    }

    private static int isDayOver(int dayOfYear) {
        Calendar calendar = Calendar.getInstance();
        int currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        return Integer.compare(dayOfYear, currentDayOfYear);
    }

    public static boolean hasTaskPassed(Task task) {
        if(isDayOver((int) task.getCalendarDayId()) == -1) {
            return true;
        }
        else if(isDayOver((int) task.getCalendarDayId()) == 1) {
            return false;
        }
        else {
            LocalTime time = LocalTime.parse(task.getEndTime());
            LocalTime now = LocalTime.now();
            return now.isAfter(time);
        }
    }
}
