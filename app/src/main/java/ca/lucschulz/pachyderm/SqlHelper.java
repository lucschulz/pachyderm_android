package ca.lucschulz.pachyderm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SqlHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_items.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK_ITEMS = "task_items";
    private static final String KEY_ID = "id";
    private static final String KEY_TASK_NAME = "task_name";
    private static final String KEY_DATE_ADDED = "date_added";
    private static final String KEY_COMPLETED = "completed";

    SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(TABLE_TASK_ITEMS).append("(")
                .append(KEY_ID)
                .append(" INTEGER PRIMARY KEY, ")
                .append(KEY_TASK_NAME)
                .append(" TEXT, ")
                .append(KEY_DATE_ADDED)
                .append(" DATE, ")
                .append(KEY_COMPLETED)
                .append(" BIT)");

        String sqlQuery = sb.toString();

        db.execSQL(sqlQuery);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK_ITEMS);

        onCreate(db);
    }

    void insertNewTaskItem(String taskItem, Date dateAdded) {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues insertValues = new ContentValues();
            insertValues.put(KEY_TASK_NAME, taskItem);
            insertValues.put(KEY_DATE_ADDED, Utils.getDateTime(dateAdded));
            db.insert(TABLE_TASK_ITEMS, null, insertValues);
        }
        catch (Exception e)
        {
            Log.e("ERROR", e.toString());
        }
    }

    List<TaskItem> retrieveItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TASK_ITEMS, null);

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

    void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK_ITEMS, null, null);
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
