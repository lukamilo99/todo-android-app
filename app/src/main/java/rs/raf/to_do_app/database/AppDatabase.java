package rs.raf.to_do_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import rs.raf.to_do_app.dao.Dao;
import rs.raf.to_do_app.model.CalendarDay;
import rs.raf.to_do_app.model.Task;

@Database(entities = {CalendarDay.class, Task.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract Dao calendarDayDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,"appDatabase")
                    .allowMainThreadQueries()
                    .build();
            //addCalendarDays(instance);
            }
        return instance;
    }

    private static void addCalendarDays(AppDatabase instance) {
        for(int i = 2; i < 366; i++) {
            CalendarDay calendarDay = new CalendarDay(i);
            instance.calendarDayDao().insertCalendarDayWithTasks(calendarDay);
        }
    }
}
