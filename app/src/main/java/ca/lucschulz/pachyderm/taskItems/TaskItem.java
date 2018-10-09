package ca.lucschulz.pachyderm.taskItems;

import java.util.Date;

public class TaskItem {

    private String taskId;
    private String taskItem;
    private Date dateAdded;
    private Date dateDue;
    private boolean completed;

    public TaskItem() {
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

    public void setTaskDescription(String taskItem) {
        this.taskItem = taskItem;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
