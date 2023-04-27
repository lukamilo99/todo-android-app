package rs.raf.to_do_app.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rs.raf.to_do_app.model.Task;

public class TaskInformationChecker {

    private static final Pattern TIME_PATTERN = Pattern.compile("^(\\d{1,2}):(\\d{2})$");

    public static boolean isTimePeriodAvailable(List<Task> taskList, String startTime, String endTime) {
        for (Task task : taskList) {
            if(task.getStartTime().equals(startTime) && task.getEndTime().equals(endTime))
                return false;
        }
        return true;
    }

    public static boolean checkTimeFormat(String startTime, String endTime) {
        Matcher matcher1 = TIME_PATTERN.matcher(startTime);
        Matcher matcher2 = TIME_PATTERN.matcher(endTime);
        return matcher1.matches() && matcher2.matches();
    }
}
