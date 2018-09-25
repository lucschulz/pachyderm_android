package ca.lucschulz.pachyderm;

import java.util.Date;

public class TaskItem {

    private String taskItem;
    private Date dateAdded;
    private boolean completed;

    public TaskItem(){}

    public TaskItem(String taskItem, Date dateAdded, boolean completed) {
        this.taskItem = taskItem;
        this.dateAdded = dateAdded;
        this.completed = completed;
    }

    public String getTaskItem() {
        return taskItem;
    }

    public  void setTaskItem(String taskItem) {
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
