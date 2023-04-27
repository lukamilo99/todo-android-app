package rs.raf.to_do_app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey//(autoGenerate = true)
    private long taskId;

    private long calendarDayId;

    private String name;

    private String startTime;

    private String endTime;

    private String priority;

    private String description;

    public Task(long calendarDayId, String name, String startTime, String endTime, String priority, String description) {
        this.taskId = System.currentTimeMillis();
        this.calendarDayId = calendarDayId;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.description = description;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getCalendarDayId() {
        return calendarDayId;
    }

    public void setCalendarDayId(long calendarDayId) {
        this.calendarDayId = calendarDayId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
