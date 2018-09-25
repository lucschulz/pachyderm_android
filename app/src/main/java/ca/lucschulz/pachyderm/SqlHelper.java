package ca.lucschulz.pachyderm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_items";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK_ITEMS = "task_items";
    private static final String KEY_ID = "id";
    private static final String KEY_TASK_NAME = "task_name";
    private static final String KEY_DATE_ADDED = "date_added";
    private static final String KEY_COMPLETED = "completed";

    public SqlHelper(Context context) {
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

    public void insertNewTaskItem(String taskItem, Date dateAdded) {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues insertValues = new ContentValues();
            insertValues.put(KEY_TASK_NAME, taskItem);
            insertValues.put(KEY_DATE_ADDED, dateAdded.toString());
            db.insert(TABLE_TASK_ITEMS, null, insertValues);
        }
        catch (Exception e)
        {
            Log.e("ERROR", e.toString());
        }
    }

    public List<TaskItem> retrieveItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TASK_ITEMS, null);

        List<String> list = new ArrayList<>();
        List<TaskItem> taskItems = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(KEY_TASK_NAME));

                TaskItem ti = new TaskItem();
                ti.setTaskItem(name);
                taskItems.add(ti);

                list.add(name);
                cursor.moveToNext();
            }
        }

        return taskItems;
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK_ITEMS, null, null);
    }
}
