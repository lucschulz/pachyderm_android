package ca.lucschulz.pachyderm.taskItems;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import ca.lucschulz.pachyderm.R;
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
            int backColor = ContextCompat.getColor(context, R.color.checkedItemBackground);

            holder.itmTaskDescription.setPaintFlags(holder.itmTaskDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.itmLayout.setBackgroundColor(backColor);
            sqlHelper.updateTaskCompleted(taskId, true);

        } else {
            holder.itmTaskDescription.setPaintFlags(holder.itmTaskDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.itmLayout.setBackgroundColor(Color.WHITE);
            sqlHelper.updateTaskCompleted(taskId, false);
        }
    }
}
