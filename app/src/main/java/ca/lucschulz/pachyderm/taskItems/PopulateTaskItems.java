package ca.lucschulz.pachyderm.taskItems;

import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ca.lucschulz.pachyderm.sql.SqlStrings;

public abstract class PopulateTaskItems {

    public TaskItem populateTaskItem(Cursor cursor) throws ParseException {
        String sqlID = cursor.getString(cursor.getColumnIndex(SqlStrings.getKeyId()));
        String sqlDescription = cursor.getString(cursor.getColumnIndex(SqlStrings.getKeyTaskDescription()));
        String sqlDateAdded = cursor.getString(cursor.getColumnIndex(SqlStrings.getKeyDateAdded()));
        String sqlDateDue = cursor.getString(cursor.getColumnIndex(SqlStrings.getKeyDateDue()));
        int sqlCompleted = cursor.getInt(cursor.getColumnIndex(SqlStrings.getKeyCompleted()));
        String notes = cursor.getString(cursor.getColumnIndex((SqlStrings.getKeyNotes())));

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

        if (notes != null) {
            ti.setNotes(notes);
        }

        return ti;
    }
}
