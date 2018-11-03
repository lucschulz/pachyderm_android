package ca.lucschulz.pachyderm.taskItems;

import android.app.NotificationManager;
import android.content.Context;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

import ca.lucschulz.pachyderm.R;
import ca.lucschulz.pachyderm.notifications.Notifications;
import ca.lucschulz.pachyderm.sql.SqlHelper;

public class AddTask {

    private Context context;
    private NotificationManager notificationManager;

    public AddTask(Context context, NotificationManager notificationManager) {
        this.context = context;
        this.notificationManager = notificationManager;
    }


    public void addNewTask(TaskItem taskItem, List<TaskItem> taskList, TaskItemsAdapter tAdapter) throws ParseException {

        String taskDescription = taskItem.getTaskDescription();

        if (taskDescription.length() > 0) {

            SqlHelper helper = new SqlHelper(context);
            helper.insertNewTaskItem(taskItem);

            taskList.clear();

            RetrieveTasks retrieve = new RetrieveTasks(context, notificationManager);
            retrieve.retrieveTaskItems(taskList, tAdapter);
            tAdapter.notifyDataSetChanged();

            String msg = context.getResources().getString(R.string.toastItemAdded);
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
        else {
            String msg = context.getResources().getString(R.string.toastEnterTaskDescription);
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
