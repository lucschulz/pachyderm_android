package ca.lucschulz.pachyderm.taskItems;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        // Format the added and due dates.
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        // Retrieve data elements from the query results.
        Date dtAdded = taskList.get(position).getDateAdded();
        Date dtDue = taskList.get(position).getDateDue();
        Boolean isCompleted = taskList.get(position).getCompleted();
        String taskId = taskList.get(position).getTaskId();

        holder.itmTaskID.setText(taskList.get(position).getTaskId());
        holder.itmTaskDescription.setText(taskList.get(position).getTaskDescription());
        holder.itmCheckBox.setChecked(isCompleted);

        // Check if there is an "added" date and populate.
        if (dtAdded != null) {
            holder.itmDateAdded.setText(date.format(dtAdded));
        }

        // Check if there is a "due" date and populate.
        if (dtDue != null) {
            holder.itmDateDue.setText(date.format(dtDue));
        }

        // Check if marked complete or not.
        if (isCompleted) {
            holder.itmTaskDescription.setPaintFlags(holder.itmTaskDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.itmLayout.setBackgroundColor(Color.rgb(170, 230, 250));
        }

        // If the entry is the last one created (as opposed to having been pulled from the DB), fetch ID and append to allow referencing.
        if (taskId == null)
        {
            SqlHelper sqlHelper = new SqlHelper(context);
            String id = sqlHelper.getMostRecentId();
            holder.itmCheckBox.setOnCheckedChangeListener(new CheckBoxHandler(Integer.parseInt(id), holder, context));
        }
        else
        {
            holder.itmCheckBox.setOnCheckedChangeListener(new CheckBoxHandler(Integer.parseInt(taskId), holder, context));
        }
    }



    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
