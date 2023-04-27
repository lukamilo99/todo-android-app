package rs.raf.to_do_app.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.function.Consumer;

import rs.raf.to_do_app.R;
import rs.raf.to_do_app.activity.MainActivity;
import rs.raf.to_do_app.model.Task;
import rs.raf.to_do_app.util.DateUtil;
import rs.raf.to_do_app.view.PageAdapter;
import rs.raf.to_do_app.viewmodel.SharedViewModel;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.ViewHolder> {

    private final Consumer<Task> onTaskClicked;

    public TaskAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback, Consumer<Task> onTaskClicked) {
        super(diffCallback);
        this.onTaskClicked = onTaskClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_plan_list_item, parent, false);
        return new TaskAdapter.ViewHolder(view, position -> {
            Task task = getItem(position);
            onTaskClicked.accept(task);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        Task task = getItem(position);
        holder.imageViewDeleteButton.setOnClickListener(view -> {
            Snackbar snackbar = Snackbar.make(view, "Are you sure you want to delete this item?", Snackbar.LENGTH_LONG)
                    .setAction("Delete", v -> {
                        MainActivity.appDatabase.calendarDayDao().deleteTaskWithId(task.getTaskId());
                        holder.sharedViewModel.removeTask(task);
                    });
            snackbar.show();
        });

        holder.imageViewEditButton.setOnClickListener(view -> {
            holder.sharedViewModel.setCurrentTask(task);
            holder.sharedViewModel.setIsEditButtonClicked(Boolean.TRUE);
            ((MainActivity) view.getContext()).navigate(PageAdapter.FRAGMENT_DAILY_PLAN_CHANGE, View.GONE);
        });
        holder.bind(task);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewEditButton;
        private ImageView imageViewDeleteButton;
        private SharedViewModel sharedViewModel;

        public ViewHolder(@NonNull View itemView, Consumer<Integer> onItemClicked) {
            super(itemView);
            imageViewEditButton = itemView.findViewById(R.id.imageViewEditButton);
            imageViewDeleteButton = itemView.findViewById(R.id.imageViewDeleteButton);
            sharedViewModel = new ViewModelProvider((MainActivity) itemView.getContext()).get(SharedViewModel.class);
            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }

        public void bind(Task task) {
            String text = task.getName();
            String time = task.getStartTime() + " - " + task.getEndTime();
            ((TextView) itemView.findViewById(R.id.daily_task_name)).setText(text);
            ((TextView) itemView.findViewById(R.id.daily_task_time)).setText(time);
            itemView.findViewById(R.id.imageViewIcon).setBackgroundResource(getPriorityColor(task));
        }

        private int getPriorityColor(Task task) {
            switch (task.getPriority()) {
                case "Low" : return R.color.green;
                case "Mid" : return R.color.orange;
                case "High" : return R.color.red;
                default: return R.color.gray;
            }
        }
    }
}
