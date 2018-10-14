package ca.lucschulz.pachyderm.taskItems;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ca.lucschulz.pachyderm.R;

public class TaskItemHolder extends RecyclerView.ViewHolder {

    public RelativeLayout itmLayout;
    public TextView itmTaskID;
    public TextView itmTaskDescription;
    public TextView itmDateAdded;
    public TextView itmDateDue;
    public CheckBox itmCheckBox;
    public TextView itmPlus;

    public TaskItemHolder(View view) {
        super(view);

        itmLayout = view.findViewById(R.id.layout_individual_item);
        itmTaskID = view.findViewById(R.id.tvTaskId);
        itmTaskDescription = view.findViewById(R.id.taskItem);
        itmDateAdded = view.findViewById(R.id.item_dateAdded);
        itmDateDue = view.findViewById(R.id.item_dateDue);
        itmCheckBox = view.findViewById(R.id.cbCompleted);
        itmPlus = view.findViewById(R.id.itmPlus);
    }
}