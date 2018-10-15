package ca.lucschulz.pachyderm.taskItems;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.lucschulz.pachyderm.sql.SqlHelper;
import ca.lucschulz.pachyderm.sql.SqlStrings;

public class RetrieveTasks {

    public enum QueryType {
        AllItems,
        ItemDetails
    }

    private Context context;
    private QueryType queryType;

    public RetrieveTasks(Context context, QueryType queryType) {
        this.context = context;
        this.queryType = queryType;
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

            if (queryType == QueryType.ItemDetails) {
                taskItem.setNotes(task.getNotes());
            }

            taskList.add(taskItem);
            tAdapter.notifyDataSetChanged();
        }
    }

    private List<TaskItem> retrieveItems() throws ParseException {
        SqlHelper helper = new SqlHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        SqlStrings cmds = new SqlStrings();
        Cursor cursor = db.rawQuery(cmds.retrieveItems(), null);

        List<TaskItem> taskItems = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                TaskItem ti = populateTaskItem(cursor);
                taskItems.add(ti);

                cursor.moveToNext();
            }
        }

        return taskItems;
    }

    private TaskItem populateTaskItem(Cursor cursor) throws ParseException {
        String sqlID = cursor.getString(cursor.getColumnIndex(SqlStrings.getKeyId()));
        String sqlDescription = cursor.getString(cursor.getColumnIndex(SqlStrings.getKeyTaskDescription()));
        String sqlDateAdded = cursor.getString(cursor.getColumnIndex(SqlStrings.getKeyDateAdded()));
        String sqlDateDue = cursor.getString(cursor.getColumnIndex(SqlStrings.getKeyDateDue()));
        int sqlCompleted = cursor.getInt(cursor.getColumnIndex(SqlStrings.getKeyCompleted()));

        Boolean completed;
        if (sqlCompleted > 0)
            completed = true;
        else
            completed = false;


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dtDateAdded = sdf.parse(sqlDateAdded);
        Date dtDateDue = sdf.parse(sqlDateDue);

        TaskItem ti = new TaskItem();
        ti.setTaskId(sqlID);
        ti.setTaskDescription(sqlDescription);
        ti.setDateAdded(dtDateAdded);
        ti.setDateDue(dtDateDue);
        ti.setCompleted(completed);

        if (queryType == QueryType.ItemDetails) {
            String notes = cursor.getString(cursor.getColumnIndex(SqlStrings.getKeyNotes()));
            ti.setNotes(notes);
        }

        return ti;
    }
}
