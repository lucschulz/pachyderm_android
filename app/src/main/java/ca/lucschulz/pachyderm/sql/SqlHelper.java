package ca.lucschulz.pachyderm.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import ca.lucschulz.pachyderm.taskItems.TaskItem;

public class SqlHelper extends SQLiteOpenHelper {

    private SqlStrings cmds;

    public SqlHelper(Context context) {
        super(context, SqlStrings.getDatabaseName(), null, SqlStrings.getDatabaseVersion());

        cmds = new SqlStrings();
    }

    public void onCreate(SQLiteDatabase db) {
        SqlTables sqlTables = new SqlTables();
        String taskItems = sqlTables.createTaskItemsTable();
        String settings = sqlTables.createSettingsTable();

        db.execSQL(taskItems);
        db.execSQL(settings);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(cmds.dropTable());
        onCreate(db);
    }

    public void insertNewTaskItem(TaskItem taskItem) {
        try
        {
            String taskDescription = taskItem.getTaskDescription();
            Date dateAdded = new Date();
            Date dateDue = taskItem.getDateDue();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String sdfDateAdded = sdf.format(dateAdded);
            String sdfDueDate = sdf.format(dateDue);


            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues insertValues = new ContentValues();
            insertValues.put(SqlStrings.getKeyTaskDescription(), taskDescription);
            insertValues.put(SqlStrings.getKeyDateAdded(), sdfDateAdded);
            insertValues.put(SqlStrings.getKeyDateDue(), sdfDueDate);

            db.insert(SqlStrings.getTableTaskItems(), null, insertValues);
        }
        catch (Exception e)
        {
            Log.e("ERROR", e.toString());
        }
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SqlStrings.getTableTaskItems(), null, null);
    }

    public void updateTaskCompleted(int id, boolean isCompleted) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strFilter = "id = " + id;
        ContentValues args = new ContentValues();
        args.put(SqlStrings.getKeyCompleted(), isCompleted);
        db.update(SqlStrings.getTableTaskItems(), args, strFilter, null);
    }

    public String getMostRecentId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Max(ID) as id FROM " + SqlStrings.getTableTaskItems(), null);

        String id = null;

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                id = cursor.getString(cursor.getColumnIndex(SqlStrings.getKeyId()));
            }
        }

        return id;
    }
}
