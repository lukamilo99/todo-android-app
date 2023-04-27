package rs.raf.to_do_app.dao;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import rs.raf.to_do_app.model.CalendarDay;
import rs.raf.to_do_app.model.Task;

@androidx.room.Dao
public abstract class Dao {

    @Insert
    public abstract void insertCalendarDay(CalendarDay calendarDay);

    @Insert
    public abstract void insertTaskList(List<Task> taskList);

    @Insert
    public abstract void insertTask(Task task);

    @Update
    public abstract void updateTask(Task task);

    @Query("SELECT * FROM Task WHERE taskId =:id")
    public abstract Task getTaskWithId(long id);

    @Query("DELETE FROM Task WHERE taskId =:id")
    public abstract void deleteTaskWithId(long id);

    @Query("SELECT * FROM CalendarDay WHERE calendarDayId =:id")
    public abstract CalendarDay getCalendarDay(long id);

    @Query("SELECT * FROM Task WHERE calendarDayId =:calendarDayId")
    public abstract List<Task> getTaskList(int calendarDayId);

    @Query("SELECT * FROM CalendarDay")
    public abstract List<CalendarDay> getAll();

    public void insertCalendarDayWithTasks(CalendarDay calendarDay) {
        List<Task> tasks = calendarDay.getTaskList();
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setCalendarDayId(calendarDay.getCalendarDayId()); // ovde je mozda greska, bilo je setTaskId umesto setCalDayId, ali mozda!!!!
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

    public List<CalendarDay> getAllCalendarDayWithTasks() {
        List<CalendarDay> result = new ArrayList<>();
        for(int i = 2; i < 366; i++) {
            result.add(getCalendarDayWithTasks(i));
        }
        return result;
    }
}
