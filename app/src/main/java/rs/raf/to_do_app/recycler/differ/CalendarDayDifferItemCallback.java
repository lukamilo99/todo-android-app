package rs.raf.to_do_app.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import rs.raf.to_do_app.model.CalendarDay;

public class CalendarDayDifferItemCallback extends DiffUtil.ItemCallback<CalendarDay> {

    @Override
    public boolean areItemsTheSame(@NonNull CalendarDay oldItem, @NonNull CalendarDay newItem) {
        return oldItem.getCalendarDayId() == newItem.getCalendarDayId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull CalendarDay oldItem, @NonNull CalendarDay newItem) {
        return oldItem.getTaskList().equals(newItem.getTaskList());
    }
}
