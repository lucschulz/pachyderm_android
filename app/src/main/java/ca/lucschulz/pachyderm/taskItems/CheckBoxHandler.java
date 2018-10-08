package ca.lucschulz.pachyderm.taskItems;

import android.content.Context;
import android.graphics.Paint;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import ca.lucschulz.pachyderm.sql.SqlHelper;

public class CheckBoxHandler implements CheckBox.OnCheckedChangeListener {

    private int taskId;
    private TaskItemHolder holder;
    private Context context;

    public CheckBoxHandler(int taskId, TaskItemHolder holder, Context context) {
        this.taskId = taskId;
        this.holder = holder;
        this.context = context;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        SqlHelper sqlHelper = new SqlHelper(context);

        if (isChecked) {
            holder.taskItem.setPaintFlags(holder.taskItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            sqlHelper.UpdateTaskCompleted(taskId, true);

        } else {
            holder.taskItem.setPaintFlags(holder.taskItem.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            sqlHelper.UpdateTaskCompleted(taskId, false);
        }
    }
}
