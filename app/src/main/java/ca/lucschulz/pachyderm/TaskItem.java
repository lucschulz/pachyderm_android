package ca.lucschulz.pachyderm;

import java.util.Date;

public class TaskItem {

    private String taskId;
    private String taskItem;
    private Date dateAdded;
    private boolean completed;

    public TaskItem() {
    }

    public TaskItem(String taskId, String taskItem, Date dateAdded, boolean completed) {
        this.taskId = taskId;
        this.taskItem = taskItem;
        this.dateAdded = dateAdded;
        this.completed = completed;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskItem() {
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
}
