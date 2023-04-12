package rs.raf.to_do_app.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import rs.raf.to_do_app.activity.MainActivity;
import rs.raf.to_do_app.model.CalendarDay;

public class RecyclerViewModel extends ViewModel {

    private final MutableLiveData<List<CalendarDay>> calendarDays = new MutableLiveData<>();

    public RecyclerViewModel() {
        calendarDays.setValue(MainActivity.appDatabase.calendarDayDao().getAll());
    }

    public MutableLiveData<List<CalendarDay>> getCalendarDays() {
        return calendarDays;
    }
}
