package rs.raf.to_do_app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Comparator;

import rs.raf.to_do_app.R;
import rs.raf.to_do_app.activity.MainActivity;
import rs.raf.to_do_app.model.Task;
import rs.raf.to_do_app.recycler.adapter.TaskAdapter;
import rs.raf.to_do_app.recycler.differ.TaskDifferItemCallback;
import rs.raf.to_do_app.util.DateUtil;
import rs.raf.to_do_app.view.PageAdapter;
import rs.raf.to_do_app.viewmodel.SharedViewModel;

public class DailyPlanFragment extends Fragment {

    private MainActivity mainActivity;

    private RecyclerView recyclerView;

    private SharedViewModel sharedViewModel;

    private TaskAdapter taskAdapter;

    private CheckBox pastObligationsCheckBox;

    private EditText searchEditText;

    private FloatingActionButton addButton;

    public DailyPlanFragment() {
        super(R.layout.fragment_daily_plan);
    }

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
        return inflater.inflate(R.layout.fragment_daily_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    private void initialize() {
        initializeModel();
        initializeView();
        initializeRecycler();
        initializeButton();
        initializeListeners();
    }

    private void initializeButton() {
        addButton = getView().findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(view1 -> {
            sharedViewModel.setIsEditButtonClicked(Boolean.FALSE);
            mainActivity.navigate(PageAdapter.FRAGMENT_DAILY_PLAN_CHANGE, View.GONE);
        });
    }

    private void initializeView() {
        View view = getView();
        recyclerView = view.findViewById(R.id.recyclerViewDailyPlan);
        searchEditText = view.findViewById(R.id.searchEditText);
        pastObligationsCheckBox = view.findViewById(R.id.pastObligationsCheckBox);
    }

    private void initializeModel() {
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    private void initializeRecycler() {
        sharedViewModel.getTasks().observe(getViewLifecycleOwner(), list -> {
            taskAdapter = new TaskAdapter(new TaskDifferItemCallback(),
                    task -> {
                        sharedViewModel.setCurrentTask(task);
                        mainActivity.navigate(PageAdapter.FRAGMENT_DAILY_PLAN_DETAIL, View.GONE);
                    });
            LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(taskAdapter);
        });
    }

    private void initializeListeners() {
        sharedViewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            tasks.sort(Comparator.comparing(Task::getStartTime));
            taskAdapter.submitList(tasks);
        });
        sharedViewModel.getCurrentCalendarDayId().observe(getViewLifecycleOwner(),
                calendarDayId -> ((TextView) getView().findViewById(R.id.dailyPlanDate)).setText(DateUtil.getDate(Math.toIntExact(calendarDayId))));
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty() && pastObligationsCheckBox.isChecked()) {
                    sharedViewModel.showAllTasks();
                }
                else if(editable.toString().isEmpty() && !pastObligationsCheckBox.isChecked()) {
                    sharedViewModel.showActiveTasks();
                }
                else sharedViewModel.filterTasks(editable.toString());
            }
        });
        pastObligationsCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if(pastObligationsCheckBox.isChecked()) {
                sharedViewModel.showAllTasks();
            }
            else sharedViewModel.showActiveTasks();
        });
    }
}
