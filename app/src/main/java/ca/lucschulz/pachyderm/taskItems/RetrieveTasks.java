package ca.lucschulz.pachyderm.taskItems;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationManagerCompat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.lucschulz.pachyderm.notifications.Notifications;
import ca.lucschulz.pachyderm.sql.SqlHelper;
import ca.lucschulz.pachyderm.sql.SqlStrings;

public class RetrieveTasks extends PopulateTaskItems {

    private Context context;
    private Notifications notifications;

    public RetrieveTasks(Context context, NotificationChannel channel, NotificationManagerCompat nmc) {
        this.context = context;
        this.notifications = new Notifications(context, channel, nmc);
    }


    public Notifications getNotifications() {
        return notifications;
    }


    // taskList contains the items displayed in the Recycler. This method fills it up with what's in the database.
    // tAdapter is a reference to the adapter for the Recycler. Allows refreshing after the list is filled up.
    public void retrieveTaskItems(List<TaskItem> taskList, TaskItemsAdapter tAdapter) throws ParseException {

        List<TaskItem> taskItemList = retrieveItems();

        for (TaskItem task : taskItemList) {

            TaskItem taskItem = new TaskItem();

            taskItem.setTaskId(task.getTaskId());
            taskItem.setTaskDescription(task.getTaskDescription());
            taskItem.setDateAdded(task.getDateAdded());
            taskItem.setDateDue(task.getDateDue());
            taskItem.setCompleted(task.getCompleted());

            taskList.add(taskItem);
            tAdapter.notifyDataSetChanged();

            setDueDateReminder(task);
        }
    }

    private void setDueDateReminder(TaskItem task) {
        String title = task.getTaskDescription();
        Date dueDate = task.getDateDue();
        boolean completed = task.getCompleted();
        int idId = Integer.parseInt(task.getTaskId());

        if (!completed) {
            notifications.createNewNotification(title, "This task is overdue.", dueDate, idId);
        }
    }

    private List<TaskItem> retrieveItems() throws ParseException {
        SqlHelper helper = new SqlHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        SqlStrings sqlCommands = new SqlStrings();

        Cursor cursor = db.rawQuery(sqlCommands.retrieveAllItems(), null);

        List<TaskItem> taskItems = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                TaskItem ti = super.populateTaskItem(cursor);
                taskItems.add(ti);

                cursor.moveToNext();
            }
        }

        return taskItems;
    }
}
