package rs.raf.to_do_app.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

import rs.raf.to_do_app.R;
import rs.raf.to_do_app.model.CalendarDay;
import rs.raf.to_do_app.util.DateUtil;

public class CalendarDayAdapter extends ListAdapter<CalendarDay, CalendarDayAdapter.ViewHolder> {

    private final Consumer<CalendarDay> onCalendarDayClicked;

    private List<CalendarDay> calendarDays;

    public CalendarDayAdapter(@NonNull DiffUtil.ItemCallback<CalendarDay> diffCallback, Consumer<CalendarDay> onCarClicked, List<CalendarDay> calendarDays) {
        super(diffCallback);
        this.calendarDays = calendarDays;
        this.onCalendarDayClicked = onCarClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_day_list_item, parent, false);
        return new ViewHolder(view, position -> {
            CalendarDay calendarDay = calendarDays.get(position);
            if(calendarDay.getCalendarDayId() < 1) view.setVisibility(View.GONE);
            onCalendarDayClicked.accept(calendarDay);
        });
    }

    @Override
    public int getItemCount() {
        return calendarDays.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarDayAdapter.ViewHolder holder, int position) {
        CalendarDay calendarDay = calendarDays.get(position);
        holder.bind(calendarDay);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView, Consumer<Integer> onItemClicked) {

            super(itemView);
            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }

        public void bind(CalendarDay calendarDay) {
            String text = String.valueOf(DateUtil.getDayOfMonth((int) calendarDay.getCalendarDayId()));
            ((TextView) itemView.findViewById(R.id.dayInMonth)).setText(text);
        }
    }
}
