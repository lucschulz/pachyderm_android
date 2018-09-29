package ca.lucschulz.pachyderm;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TaskItemsAdapter extends RecyclerView.Adapter<TaskItemsAdapter.TaskItemHolder> {

    private List<TaskItem> taskList;

    public TaskItemsAdapter(List<TaskItem> taskList) {
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
        holder.taskItem.setText(taskList.get(position).getTaskItem());
        holder.dateAdded.setText(taskList.get(position).getDateAdded().toString());
        holder.completed.setChecked(taskList.get(position).getCompleted());

        holder.completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Testing", "Click listener works.");
            }
        });
    }


    @Override
    public int getItemCount() { return taskList.size();}


    public class TaskItemHolder extends RecyclerView.ViewHolder {
        public TextView taskItem;
        public TextView dateAdded;
        public CheckBox completed;

        public TaskItemHolder(View view) {
            super(view);

            taskItem = view.findViewById(R.id.taskItem);
            dateAdded = view.findViewById(R.id.dateAdded);
            completed = view.findViewById(R.id.cbCompleted);
        }
    }
}
