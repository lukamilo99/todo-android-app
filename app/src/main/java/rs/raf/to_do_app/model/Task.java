package rs.raf.to_do_app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long taskId;

    private long calendarDayId;

    private String name;

    private String creationDate;

    private String expireDate;

    private String priority;

    private String description;

    public Task(long taskId, long calendarDayId, String name, String creationDate, String expireDate, String priority, String description) {
        this.taskId = taskId;
        this.calendarDayId = calendarDayId;
        this.name = name;
        this.creationDate = creationDate;
        this.expireDate = expireDate;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
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
