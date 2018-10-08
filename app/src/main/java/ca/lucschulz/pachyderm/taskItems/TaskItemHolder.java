package ca.lucschulz.pachyderm.taskItems;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import ca.lucschulz.pachyderm.R;

public class TaskItemHolder extends RecyclerView.ViewHolder {
    public TextView itmTaskID;
    public TextView itmTaskDescription;
    public TextView itmDateAdded;
    public TextView itmTimeAdded;
    public TextView itmDateDue;
    public TextView itmTimeDue;
    public CheckBox itmCheckBox;

    public TaskItemHolder(View view) {
        super(view);

        itmTaskID = view.findViewById(R.id.tvTaskId);
        itmTaskDescription = view.findViewById(R.id.taskItem);
        itmDateAdded = view.findViewById(R.id.item_dateAdded);
        itmTimeAdded = view.findViewById(R.id.item_timeAdded);
        itmDateDue = view.findViewById(R.id.item_dateDue);
        itmTimeDue = view.findViewById(R.id.item_timeDue);
        itmCheckBox = view.findViewById(R.id.cbCompleted);
    }
}