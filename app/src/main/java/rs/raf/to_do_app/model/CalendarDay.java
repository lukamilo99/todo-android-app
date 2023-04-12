package rs.raf.to_do_app.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class CalendarDay {

    @PrimaryKey
    private long calendarDayId;

    @Ignore
    private List<Task> taskList;

    public CalendarDay(long calendarDayId) {
        this.calendarDayId = calendarDayId;
    }

    public long getCalendarDayId() {
        return calendarDayId;
    }

    public void setCalendarDayId(long calendarDayId) {
        this.calendarDayId = calendarDayId;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
