package rs.raf.to_do_app.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import rs.raf.to_do_app.model.Task;

public class TaskDifferItemCallback extends DiffUtil.ItemCallback<Task> {

    @Override
    public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getTaskId() == newItem.getTaskId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getDescription().equals(newItem.getDescription())
                && oldItem.getPriority().equals(newItem.getPriority())
                && oldItem.getName().equals(newItem.getName());
    }
}