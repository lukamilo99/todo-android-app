package rs.raf.to_do_app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import rs.raf.to_do_app.R;
import rs.raf.to_do_app.activity.MainActivity;
import rs.raf.to_do_app.model.Task;
import rs.raf.to_do_app.view.PageAdapter;
import rs.raf.to_do_app.viewmodel.SharedViewModel;

public class TaskDetailFragment extends Fragment {

    private MainActivity mainActivity;
    private SharedViewModel sharedViewModel;
    private TextView titleTextView;
    private TextView startTextView;
    private TextView endTextView;
    private TextView descriptionTextView;
    private Button deleteButton;
    private Button editButton;

    private Task currentTask;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_plan_detail, container, false);
        initialize(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initialize(View view) {
        initializeModel();
        initializeButtons(view);
        initializeView(view);
    }

    private void initializeView(View view) {
        titleTextView = view.findViewById(R.id.titleTextView);
        startTextView = view.findViewById(R.id.startTimeTextView);
        endTextView = view.findViewById(R.id.endTimeTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
    }

    private void initializeModel() {
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getCurrentTask().observe(getViewLifecycleOwner(),
                currentTask -> {
                    this.currentTask = currentTask;
                    titleTextView.setText(currentTask.getName());
                    startTextView.setText(currentTask.getStartTime());
                    endTextView.setText(currentTask.getEndTime());
                    descriptionTextView.setText(currentTask.getDescription());
                });
    }

    private void initializeButtons(View view) {
        deleteButton = view.findViewById(R.id.deleteButtonDetails);
        deleteButton.setOnClickListener(newView -> {
            Snackbar snackbar = Snackbar.make(view, "Are you sure you want to delete this item?", Snackbar.LENGTH_LONG)
                    .setAction("Delete", v -> {
                        MainActivity.appDatabase.calendarDayDao().deleteTaskWithId(currentTask.getTaskId());
                        sharedViewModel.removeTask(currentTask);
                        mainActivity.navigate(PageAdapter.FRAGMENT_DAILY_PLAN, View.VISIBLE);
                    });
            snackbar.show();
        });

        editButton = view.findViewById(R.id.editButtonDetails);
        editButton.setOnClickListener(newView ->{
            sharedViewModel.setIsEditButtonClicked(new Boolean(true));
            mainActivity.navigate(PageAdapter.FRAGMENT_DAILY_PLAN_CHANGE, View.GONE);
        });
    }
}
