package ca.lucschulz.pachyderm;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TaskItemsAdapter extends RecyclerView.Adapter<TaskItemsAdapter.TaskItemHolder> {

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

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        holder.dateAdded.setText(df.format(taskList.get(position).getDateAdded()));
        holder.completed.setChecked(taskList.get(position).getCompleted());

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
            if (isChecked) {
                //TODO Perform query that marks the app completed.

                Log.d("Testing", "Checked.");
                holder.taskItem.setPaintFlags(holder.taskItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            } else {
                //TODO Perform query that marks the app not completed.

                Log.d("Testing", "Unchecked.");
                holder.taskItem.setPaintFlags(holder.taskItem.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    public class TaskItemHolder extends RecyclerView.ViewHolder {
        public TextView taskId;
        public TextView taskItem;
        public TextView dateAdded;
        public CheckBox completed;

        public TaskItemHolder(View view) {
            super(view);

            taskId = view.findViewById(R.id.tvTaskId);
            taskItem = view.findViewById(R.id.taskItem);
            dateAdded = view.findViewById(R.id.dateAdded);
            completed = view.findViewById(R.id.cbCompleted);
        }
    }
}
