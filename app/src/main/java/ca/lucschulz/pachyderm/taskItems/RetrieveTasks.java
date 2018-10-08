package ca.lucschulz.pachyderm.taskItems;

import android.content.Context;

import java.util.Date;
import java.util.List;

import ca.lucschulz.pachyderm.sql.SqlHelper;

public class RetrieveTasks {

    public void retrieveTaskItems(Context context, List<TaskItem> taskList, TaskItemsAdapter tAdapter) {
        SqlHelper helper = new SqlHelper(context);
        List<TaskItem> list = helper.retrieveItems();

        for (TaskItem ti : list) {
            String taskId = ti.getTaskId();
            String name = ti.getTaskDescription();
            Date dateAdded = ti.getDateAdded();
            Boolean completed = ti.getCompleted();

            TaskItem taskItem = new TaskItem();
            taskItem.setTaskId(taskId);
            taskItem.setTaskItem(name);
            taskItem.setDateAdded(dateAdded);
            taskItem.setCompleted(completed);

            taskList.add(taskItem);
            tAdapter.notifyDataSetChanged();
        }
    }
}
