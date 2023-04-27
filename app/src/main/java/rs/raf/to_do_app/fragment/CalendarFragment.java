package rs.raf.to_do_app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.util.Objects;

import rs.raf.to_do_app.R;
import rs.raf.to_do_app.activity.MainActivity;
import rs.raf.to_do_app.recycler.adapter.CalendarDayAdapter;
import rs.raf.to_do_app.recycler.differ.CalendarDayDifferItemCallback;
import rs.raf.to_do_app.util.DateUtil;
import rs.raf.to_do_app.view.PageAdapter;
import rs.raf.to_do_app.viewmodel.SharedViewModel;

public class CalendarFragment extends Fragment {

    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private CalendarDayAdapter calendarDayAdapter;
    private SharedViewModel sharedViewModel;

    public CalendarFragment() {
        super(R.layout.fragment_calendar);
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
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        initialize(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initialize(View view) {
        initializeModel();
        initializeView(view);
        initializeRecycler();
        initializeListeners();
    }

    private void initializeModel() {
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    private void initializeView(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewCalendar);
    }

    private void initializeListeners() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                GridLayoutManager layoutManager = ((GridLayoutManager)recyclerView.getLayoutManager());
                int last = 0;

                if (layoutManager != null) {
                    last = layoutManager.findLastCompletelyVisibleItemPosition();
                }
                TextView textView = mainActivity.findViewById(R.id.monthName);
                changeMonth(DateUtil.getMonthOfYear(last - 15), textView);
            }
        });
        sharedViewModel.getCalendarDays().observe(getViewLifecycleOwner(), list -> calendarDayAdapter.submitList(list));
    }

    private void initializeRecycler() {
        calendarDayAdapter = new CalendarDayAdapter(new CalendarDayDifferItemCallback(), calendarDay ->
        {
            sharedViewModel.setTasks(calendarDay.getTaskList());
            sharedViewModel.setCurrentCalendarDayId(calendarDay.getCalendarDayId());
            mainActivity.navigate(PageAdapter.FRAGMENT_DAILY_PLAN, View.VISIBLE);
        });
        GridLayoutManager layoutManager = new GridLayoutManager(mainActivity, 7);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(calendarDayAdapter);
        Objects.requireNonNull(recyclerView.getLayoutManager()).scrollToPosition(DateUtil.getDayOfYear(new Date(System.currentTimeMillis())));
    }

    private void changeMonth(int monthNumber, TextView textView) {
        switch(monthNumber) {
            case 0:
                textView.setText(R.string.january);
                break;
            case 1:
                textView.setText(R.string.february);
                break;
            case 2:
                textView.setText(R.string.march);
                break;
            case 3:
                textView.setText(R.string.april);
                break;
            case 4:
                textView.setText(R.string.may);
                break;
            case 5:
                textView.setText(R.string.june);
                break;
            case 6:
                textView.setText(R.string.july);
                break;
            case 7:
                textView.setText(R.string.august);
                break;
            case 8:
                textView.setText(R.string.september);
                break;
            case 9:
                textView.setText(R.string.october);
                break;
            case 10:
                textView.setText(R.string.november);
                break;
            case 11:
                textView.setText(R.string.december);
                break;
        }
    }
}
