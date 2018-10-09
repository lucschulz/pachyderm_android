package ca.lucschulz.pachyderm.taskItems;

import android.content.Context;
import java.util.Date;
import java.util.List;
import ca.lucschulz.pachyderm.sql.SqlHelper;

public class RetrieveTasks {

    private Context context;

    public RetrieveTasks(Context context) {
        this.context = context;
    }


    // taskList contains the items displayed in the Recycler. This method fills it up with what's in the database.
    // tAdapter is a reference to the adapter for the Recycler. Allows refreshing after the list is filled up.
    public void retrieveTaskItems(List<TaskItem> taskList, TaskItemsAdapter tAdapter) {

        SqlHelper sqlHelper = new SqlHelper(context);

        List<TaskItem> taskItemList = sqlHelper.retrieveItems();

        for (TaskItem task : taskItemList) {

            String id = task.getTaskId();
            String description = task.getTaskDescription();
            Date dateAdded = task.getDateAdded();
            Date dateDue = task.getDateDue();
            Boolean completed = task.getCompleted();

            TaskItem taskItem = new TaskItem();
            taskItem.setTaskId(id);
            taskItem.setTaskDescription(description);
            taskItem.setDateAdded(dateAdded);
            taskItem.setDateDue(dateDue);
            taskItem.setCompleted(completed);

            taskList.add(taskItem);
            tAdapter.notifyDataSetChanged();
        }
    }
}
