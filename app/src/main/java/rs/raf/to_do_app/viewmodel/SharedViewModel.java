package rs.raf.to_do_app.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import rs.raf.to_do_app.activity.MainActivity;
import rs.raf.to_do_app.model.CalendarDay;
import rs.raf.to_do_app.model.Task;
import rs.raf.to_do_app.util.DateUtil;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<List<CalendarDay>> calendarDays;

    private MutableLiveData<List<Task>> tasks = new MutableLiveData<>();

    private MutableLiveData<Long> currentCalendarDayId = new MutableLiveData<>();

    private MutableLiveData<Task> currentTask = new MutableLiveData<>();

    private MutableLiveData<Boolean> isEditButtonClicked = new MutableLiveData<>();

    private List<Task> allTasks;

    public SharedViewModel() {
        this.calendarDays = new MutableLiveData<>();
        this.calendarDays.setValue(MainActivity.appDatabase.calendarDayDao().getAllCalendarDayWithTasks());
        this.allTasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        List<Task> taskList = allTasks;
        taskList.add(task);
        List<CalendarDay> calendarDayList = calendarDays.getValue();
        calendarDayList.get((int) task.getCalendarDayId() - 2).setTaskList(taskList);
        setCalendarDays(calendarDayList);
        setTasks(taskList);
    }

    public void removeTask(Task task) {
        List<Task> taskList = allTasks;
        taskList.remove(task);
        List<CalendarDay> calendarDayList = calendarDays.getValue();
        calendarDayList.get((int) task.getCalendarDayId() - 2).setTaskList(taskList);
        setCalendarDays(calendarDayList);
        setTasks(taskList);
    }

    public void updateTask(Task task) {
        List<Task> taskList = allTasks;
        for(Task updateTask: taskList) {
            if (updateTask.getTaskId() == task.getTaskId()) {
                taskList.remove(updateTask);
                break;
            }
        }
        taskList.add(task);
        List<CalendarDay> calendarDayList = calendarDays.getValue();
        calendarDayList.get((int) task.getCalendarDayId() - 2).setTaskList(taskList);
        setCalendarDays(calendarDayList);
        setTasks(taskList);
    }

    public void filterTasks(String filter) {
        List<Task> filteredTaskList = tasks.getValue().stream().filter(task -> task.getName().toLowerCase().startsWith(filter.toLowerCase())).collect(Collectors.toList());
        tasks.setValue(filteredTaskList);
    }

    public void showAllTasks() {
        //passedTasks = tasks.getValue().stream().filter(task -> DateUtil.hasTaskPassed(task)).collect(Collectors.toList());
        tasks.setValue(allTasks);
    }

    public void showActiveTasks() {
        List<Task> activeTasks = allTasks.stream().filter(task -> !DateUtil.hasTaskPassed(task)).collect(Collectors.toList());
        tasks.setValue(activeTasks);
    }

    public MutableLiveData<Long> getCurrentCalendarDayId() {
        return currentCalendarDayId;
    }

    public void setCurrentCalendarDayId(Long currentCalendarDayId) {
        this.currentCalendarDayId.setValue(currentCalendarDayId);
    }

    public MutableLiveData<List<Task>> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.allTasks = tasks;
        this.tasks.setValue(tasks);
        showActiveTasks();
    }

    public MutableLiveData<Task> getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask.setValue(currentTask);
    }

    public MutableLiveData<Boolean> getIsEditButtonClicked() {
        return isEditButtonClicked;
    }

    public void setIsEditButtonClicked(Boolean isEditButtonClicked) {
        this.isEditButtonClicked.setValue(isEditButtonClicked);
    }

    public MutableLiveData<List<CalendarDay>> getCalendarDays() {
        return calendarDays;
    }

    public void setCalendarDays(List<CalendarDay> list) {
        this.calendarDays.setValue(list);
    }
}
