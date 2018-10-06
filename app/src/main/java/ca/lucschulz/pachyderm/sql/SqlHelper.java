package ca.lucschulz.pachyderm.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.lucschulz.pachyderm.TaskItem;
import ca.lucschulz.pachyderm.Utils;

public class SqlHelper extends SQLiteOpenHelper {

    private SqlCommands cmds;

    private String TABLE_TASK_ITEMS;
    private String KEY_ID;
    private String KEY_TASK_NAME;
    private String KEY_DATE_ADDED;
    private String KEY_COMPLETED;

    public SqlHelper(Context context) {
        super(context, SqlCommands.getDatabaseName(), null, SqlCommands.getDatabaseVersion());

        cmds = new SqlCommands();

        TABLE_TASK_ITEMS = SqlCommands.getTableTaskItems();
        KEY_ID = SqlCommands.getKeyId();
        KEY_TASK_NAME = SqlCommands.getKeyTaskName();
        KEY_DATE_ADDED = SqlCommands.getKeyDateAdded();
        KEY_COMPLETED = SqlCommands.getKeyCompleted();
    }

    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = cmds.createTable();
        db.execSQL(sqlQuery);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(cmds.dropTable());
        onCreate(db);
    }

    public void insertNewTaskItem(String taskItem) {
        try
        {
            ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Canada/Eastern"));
            String date = zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues insertValues = new ContentValues();
            insertValues.put(KEY_TASK_NAME, taskItem);
            insertValues.put(KEY_DATE_ADDED, date);
            db.insert(TABLE_TASK_ITEMS, null, insertValues);
        }
        catch (Exception e)
        {
            Log.e("ERROR", e.toString());
        }
    }

    public List<TaskItem> retrieveItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(cmds.retrieveItems(), null);

        List<TaskItem> taskItems = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                String id = cursor.getString(cursor.getColumnIndex(KEY_ID));
                String name = cursor.getString(cursor.getColumnIndex(KEY_TASK_NAME));
                String date = cursor.getString(cursor.getColumnIndex(KEY_DATE_ADDED));
                int comp = cursor.getInt(cursor.getColumnIndex(KEY_COMPLETED));



                Boolean completed;
                if (comp > 0)
                    completed = true;
                else
                    completed = false;

                Date dt = null;
                try {
                    dt = Utils.convertStringToDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TaskItem ti = new TaskItem();
                ti.setTaskId(id);
                ti.setTaskItem(name);
                ti.setDateAdded(dt);
                ti.setCompleted(completed);

                taskItems.add(ti);

                cursor.moveToNext();
            }
        }

        return taskItems;
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK_ITEMS, null, null);
    }

    public void UpdateTaskCompleted(int id, boolean isCompleted) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strFilter = "id = " + id;
        ContentValues args = new ContentValues();
        args.put(KEY_COMPLETED, isCompleted);
        db.update(TABLE_TASK_ITEMS, args, strFilter, null);
    }

    public String getMostRecentId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Max(ID) as id FROM " + TABLE_TASK_ITEMS, null);

        String id = null;

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                id = cursor.getString(cursor.getColumnIndex(KEY_ID));
            }
        }

        return id;
    }
}
