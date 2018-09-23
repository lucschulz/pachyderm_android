package ca.lucschulz.pachyderm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    }
}
