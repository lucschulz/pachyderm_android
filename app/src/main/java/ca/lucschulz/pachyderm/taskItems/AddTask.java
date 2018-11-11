package ca.lucschulz.pachyderm.taskItems;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

import ca.lucschulz.pachyderm.R;
import ca.lucschulz.pachyderm.notifications.Notifications;
import ca.lucschulz.pachyderm.sql.SqlHelper;

public class AddTask {

    private Context context;
    private NotificationChannel channel;
    private NotificationManagerCompat nmc;

    public AddTask(Context context, NotificationChannel channel, NotificationManagerCompat nmc) {
        this.context = context;
        this.channel = channel;
        this.nmc = nmc;
    }


    public void addNewTask(TaskItem taskItem, List<TaskItem> taskList, TaskItemsAdapter tAdapter) throws ParseException {

        String taskDescription = taskItem.getTaskDescription();

        if (taskDescription.length() > 0) {

            SqlHelper helper = new SqlHelper(context);
            helper.insertNewTaskItem(taskItem);

            taskList.clear();

            RetrieveTasks retrieve = new RetrieveTasks(context, channel, nmc);
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
