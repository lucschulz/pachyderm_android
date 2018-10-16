package ca.lucschulz.pachyderm.taskItems;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;

import ca.lucschulz.pachyderm.sql.SqlHelper;
import ca.lucschulz.pachyderm.sql.SqlStrings;

public class RetrieveSingleTask extends PopulateTaskItems {

    private Context context;

    public RetrieveSingleTask(Context context) {
        this.context = context;
    }

    public TaskItem retrieveTask(int taskId) throws ParseException {
        SqlHelper helper = new SqlHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        SqlStrings sqlCommands = new SqlStrings();

        Cursor cursor = db.rawQuery(sqlCommands.retrieveSingleItemWithDetails(taskId), null);

        TaskItem taskItem = new TaskItem();
        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                taskItem = super.populateTaskItem(cursor);

                cursor.moveToNext();
            }
        }

        return taskItem;
    }
}
