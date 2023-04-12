package rs.raf.to_do_app.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import rs.raf.to_do_app.model.CalendarDay;
import rs.raf.to_do_app.model.Task;

@Dao
public abstract class CalendarDayDao {

    @Insert
    public abstract void insertCalendarDay(CalendarDay calendarDay);

    @Insert
    public abstract void insertTaskList(List<Task> taskList);

    @Query("SELECT * FROM CalendarDay WHERE calendarDayId =:id")
    public abstract CalendarDay getCalendarDay(int id);

    @Query("SELECT * FROM Task WHERE calendarDayId =:calendarDayId")
    public abstract List<Task> getTaskList(int calendarDayId);

    @Query("SELECT * FROM CalendarDay")
    public abstract List<CalendarDay> getAll();

    public void insertCalendarDayWithTasks(CalendarDay calendarDay) {
        List<Task> tasks = calendarDay.getTaskList();
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setTaskId(calendarDay.getCalendarDayId());
        }
        insertTaskList(tasks);
        insertCalendarDay(calendarDay);
    }

    public CalendarDay getCalendarDayWithTasks(int id) {
        CalendarDay calendarDay = getCalendarDay(id);
        List<Task> tasks = getTaskList(id);
        calendarDay.setTaskList(tasks);
        return calendarDay;
    }
}
