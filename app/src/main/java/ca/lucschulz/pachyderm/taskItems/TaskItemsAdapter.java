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
import ca.lucschulz.pachyderm.taskItems.TaskItem;
import ca.lucschulz.pachyderm.taskItems.TaskItemHolder;

public class TaskItemsAdapter extends RecyclerView.Adapter<TaskItemHolder> {

    private List<TaskItem> taskList;
    private Context context;
    public TaskItemHolder holder;


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

        this.holder = holder;

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
            holder.completed.setOnCheckedChangeListener(new myCheckBoxChangeClicker(Integer.parseInt(id), holder));
        }
        else
        {
            holder.completed.setOnCheckedChangeListener(new myCheckBoxChangeClicker(Integer.parseInt(taskId), holder));
        }
    }

    class myCheckBoxChangeClicker implements CheckBox.OnCheckedChangeListener
    {
        private int taskId;

        public myCheckBoxChangeClicker(int taskId, TaskItemHolder holder) {
            this.taskId = taskId;
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



    @Override
    public int getItemCount() {
        return taskList.size();
    }
}