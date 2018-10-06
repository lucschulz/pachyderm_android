package ca.lucschulz.pachyderm.taskItems;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import ca.lucschulz.pachyderm.R;

public class TaskItemHolder extends RecyclerView.ViewHolder {
    public TextView taskId;
    public TextView taskItem;
    public TextView dateAdded;
    public TextView timeAdded;
    public CheckBox completed;

    public TaskItemHolder(View view) {
        super(view);

        taskId = view.findViewById(R.id.tvTaskId);
        taskItem = view.findViewById(R.id.taskItem);
        dateAdded = view.findViewById(R.id.dateAdded);
        timeAdded = view.findViewById(R.id.timeAdded);
        completed = view.findViewById(R.id.cbCompleted);
    }
}