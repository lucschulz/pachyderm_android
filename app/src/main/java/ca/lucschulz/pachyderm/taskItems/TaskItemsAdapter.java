package ca.lucschulz.pachyderm.taskItems;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ca.lucschulz.pachyderm.R;
import ca.lucschulz.pachyderm.sql.SqlHelper;

public class TaskItemsAdapter extends RecyclerView.Adapter<TaskItemHolder> {

    private List<TaskItem> taskList;
    private Context context;


    public TaskItemsAdapter(Context context, List<TaskItem> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public TaskItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item_row, parent, false);

        return new TaskItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskItemHolder holder, int position) {

        holder.taskId.setText(taskList.get(position).getTaskId());
        holder.taskItem.setText(taskList.get(position).getTaskItem());

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat time = new SimpleDateFormat("HH:mm");

        Date dt = taskList.get(position).getDateAdded();

        if (dt != null) {
            holder.dateAdded.setText(date.format(dt));
            holder.timeAdded.setText(time.format(dt));
        }

        Boolean isCompleted = taskList.get(position).getCompleted();
        holder.completed.setChecked(isCompleted);

        if (isCompleted) {
            holder.taskItem.setPaintFlags(holder.taskItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        String taskId = taskList.get(position).getTaskId();

        if (taskId == null)
        {
            SqlHelper sqlHelper = new SqlHelper(context);
            String id = sqlHelper.getMostRecentId();
            holder.completed.setOnCheckedChangeListener(new CheckBoxHandler(Integer.parseInt(id), holder, context));
        }
        else
        {
            holder.completed.setOnCheckedChangeListener(new CheckBoxHandler(Integer.parseInt(taskId), holder, context));
        }
    }



    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
