package ca.lucschulz.pachyderm.taskItems;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskItem {

    private String taskId;
    private String taskItem;
    private Date dateAdded;
    private Date dateDue;
    private Date timeDue;
    private boolean completed;

    public TaskItem() {
    }

    public TaskItem(String taskId, String taskItem, Date dateAdded, boolean completed) {
        this.taskId = taskId;
        this.taskItem = taskItem;
        this.dateAdded = dateAdded;
        this.setDateDue(dateDue);
        this.setTimeDue(timeDue);
        this.completed = completed;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskItem;
    }

    public void setTaskItem(String taskItem) {
        this.taskItem = taskItem;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getTimeAdded() {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        return time.format(dateAdded);
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Date getTimeDue() {
        return timeDue;
    }

    public void setTimeDue(Date timeDue) {
        this.timeDue = timeDue;
    }
}
