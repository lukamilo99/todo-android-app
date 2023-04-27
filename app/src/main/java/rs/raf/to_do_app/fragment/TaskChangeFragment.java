package rs.raf.to_do_app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import rs.raf.to_do_app.R;
import rs.raf.to_do_app.activity.MainActivity;
import rs.raf.to_do_app.model.Task;
import rs.raf.to_do_app.util.TaskInformationChecker;
import rs.raf.to_do_app.view.PageAdapter;
import rs.raf.to_do_app.viewmodel.SharedViewModel;
import timber.log.Timber;

public class TaskChangeFragment extends Fragment {

    private MainActivity mainActivity;
    private SharedViewModel sharedViewModel;
    private EditText titleEditText;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private EditText descriptionEditText;
    private Button createButton;
    private Button editButton;

    private Button cancelButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton lowRadioButton;
    private RadioButton midRadioButton;
    private RadioButton highRadioButton;

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
        return inflater.inflate(R.layout.fragment_daily_plan_change, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    private void initialize() {
        initializeView();
        initializeModel();
        initializeButtons();
    }

    private void initializeView() {
        View view = getView();
        titleEditText = view.findViewById(R.id.titleEditText);
        startTimeEditText = view.findViewById(R.id.startTimeEditText);
        endTimeEditText = view.findViewById(R.id.endTimeEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        priorityRadioGroup = view.findViewById(R.id.priorityRadioButton);
        lowRadioButton = view.findViewById(R.id.lowRadioButton);
        midRadioButton = view.findViewById(R.id.midRadioButton);
        highRadioButton = view.findViewById(R.id.highRadioButton);
    }

    private void initializeModel() {
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getIsEditButtonClicked().observe(getViewLifecycleOwner(), isEditClicked -> {
            if (isEditClicked) createButton.setVisibility(View.GONE);
            else editButton.setVisibility(View.GONE);
        });
        sharedViewModel.getCurrentCalendarDayId().observe(getViewLifecycleOwner(), calendarDayId -> {
            initializeCreateButtonListener(calendarDayId);
        });
        sharedViewModel.getCurrentTask().observe(getViewLifecycleOwner(), task -> {
            initializeEditButtonListener(task);
        });
    }

    private void initializeButtons() {
        createButton = getView().findViewById(R.id.createButton);
        editButton = getView().findViewById(R.id.editButton);
        cancelButton = getView().findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> {
            mainActivity.navigate(PageAdapter.FRAGMENT_DAILY_PLAN, View.VISIBLE);
        });
    }

    private void initializeCreateButtonListener(long calendarDayId) {
        createButton.setOnClickListener(newView -> {
            String name = titleEditText.getText().toString().trim();
            String startTime = startTimeEditText.getText().toString().trim();
            String endTime = endTimeEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String priority = getPriority();

            if (TaskInformationChecker.checkTimeFormat(startTime, endTime)) {
                if (!TaskInformationChecker.isTimePeriodAvailable(sharedViewModel.getTasks().getValue(), startTime, endTime)) {
                    Snackbar snackbar = Snackbar.make(getView(), "There is another task at selected time!", Snackbar.LENGTH_LONG)
                            .setAction("Continue", v -> {
                                Task task = new Task(calendarDayId, name, startTime, endTime, priority, description);
                                MainActivity.appDatabase.calendarDayDao().insertTask(task);
                                sharedViewModel.addTask(task);
                                mainActivity.navigate(PageAdapter.FRAGMENT_DAILY_PLAN, View.VISIBLE);
                            });
                    snackbar.show();
                }else {
                    Task task = new Task(calendarDayId, name, startTime, endTime, priority, description);
                    MainActivity.appDatabase.calendarDayDao().insertTask(task);
                    sharedViewModel.addTask(task);
                    mainActivity.navigate(PageAdapter.FRAGMENT_DAILY_PLAN, View.VISIBLE);
                }
            }else{
                Toast.makeText(requireActivity(), "Bad time format", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeEditButtonListener(Task task) {
        editButton.setOnClickListener(newView -> {
            String name = titleEditText.getText().toString().trim();
            String startTime = startTimeEditText.getText().toString().trim();
            String endTime = endTimeEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String priority = getPriority();

            if (TaskInformationChecker.checkTimeFormat(startTime, endTime)) {
                if (!TaskInformationChecker.isTimePeriodAvailable(sharedViewModel.getTasks().getValue(), startTime, endTime)) {
                    Snackbar snackbar = Snackbar.make(getView(), "There is another task at selected time!", Snackbar.LENGTH_LONG)
                            .setAction("Continue", v -> {
                                Task taskToUpdate = MainActivity.appDatabase.calendarDayDao().getTaskWithId(task.getTaskId());
                                taskToUpdate.setName(name);
                                taskToUpdate.setStartTime(startTime);
                                taskToUpdate.setEndTime(endTime);
                                taskToUpdate.setDescription(description);
                                taskToUpdate.setPriority(priority);
                                MainActivity.appDatabase.calendarDayDao().updateTask(taskToUpdate);
                                sharedViewModel.updateTask(taskToUpdate);
                                mainActivity.navigate(PageAdapter.FRAGMENT_DAILY_PLAN, View.VISIBLE);
                            });
                    snackbar.show();
                }
                else {
                    Task taskToUpdate = MainActivity.appDatabase.calendarDayDao().getTaskWithId(task.getTaskId());
                    taskToUpdate.setName(name);
                    taskToUpdate.setStartTime(startTime);
                    taskToUpdate.setEndTime(endTime);
                    taskToUpdate.setDescription(description);
                    taskToUpdate.setPriority(priority);
                    MainActivity.appDatabase.calendarDayDao().updateTask(taskToUpdate);
                    sharedViewModel.updateTask(taskToUpdate);
                    mainActivity.navigate(PageAdapter.FRAGMENT_DAILY_PLAN, View.VISIBLE);
                }
            }else {
                Toast.makeText(requireActivity(), "Bad time format", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getPriority() {
        if (lowRadioButton.isChecked()) return "Low";
        else if (midRadioButton.isChecked()) return "Mid";
        else return "High";
    }
}
