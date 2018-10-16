package ca.lucschulz.pachyderm.taskItems;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ca.lucschulz.pachyderm.sql.SqlHelper;
import ca.lucschulz.pachyderm.sql.SqlStrings;

public class RetrieveTasks extends PopulateTaskItems {

    private Context context;

    public RetrieveTasks(Context context) {
        this.context = context;
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
