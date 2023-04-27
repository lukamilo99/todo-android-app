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
import rs.raf.to_do_app.model.Task;
import rs.raf.to_do_app.util.DateUtil;

public class CalendarDayAdapter extends ListAdapter<CalendarDay, CalendarDayAdapter.ViewHolder> {

    private final Consumer<CalendarDay> onCalendarDayClicked;

    public CalendarDayAdapter(@NonNull DiffUtil.ItemCallback<CalendarDay> diffCallback, Consumer<CalendarDay> onCalendarDayClicked) {
        super(diffCallback);
        this.onCalendarDayClicked = onCalendarDayClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_day_list_item, parent, false);
        return new ViewHolder(view, position -> {
            CalendarDay calendarDay = getItem(position);
            onCalendarDayClicked.accept(calendarDay);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarDayAdapter.ViewHolder holder, int position) {
        CalendarDay calendarDay = getItem(position);
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
            ((TextView) itemView.findViewById(R.id.dayInMonth)).setBackgroundResource(getPriorityColor(calendarDay));
        }

        private int getPriorityColor(CalendarDay calendarDay) {
            String priority = getHighestPriority(calendarDay.getTaskList());
            switch (priority) {
                case "Low" : return R.color.green;
                case "Mid" : return R.color.orange;
                case "High" : return R.color.red;
                default: return R.color.gray;
            }
        }

        public String getHighestPriority(List<Task> tasks) {
            if (tasks.isEmpty()) return "Default";

            String highestPriority = "Low";
            for (Task task : tasks) {
                String priority = task.getPriority();
                if (priority.equals("High")) {
                    highestPriority = "High";
                    break;
                } else if (priority.equals("Mid")) {
                    highestPriority = "Mid";
                }
            }
            return highestPriority;
        }
    }
}
