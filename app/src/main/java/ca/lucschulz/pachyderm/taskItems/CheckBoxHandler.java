package ca.lucschulz.pachyderm.taskItems;

import android.content.Context;
import android.graphics.Color;
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
            holder.itmTaskDescription.setPaintFlags(holder.itmTaskDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.itmLayout.setBackgroundColor(Color.rgb(170, 230, 250));
            sqlHelper.updateTaskCompleted(taskId, true);

        } else {
            holder.itmTaskDescription.setPaintFlags(holder.itmTaskDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.itmLayout.setBackgroundColor(Color.rgb(255, 255, 255));
            sqlHelper.updateTaskCompleted(taskId, false);
        }
    }
}
