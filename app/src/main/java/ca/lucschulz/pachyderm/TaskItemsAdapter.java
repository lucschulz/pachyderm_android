package ca.lucschulz.pachyderm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    }

    @Override
    public int getItemCount() { return taskList.size();}


    public class TaskItemHolder extends RecyclerView.ViewHolder {
        public TextView taskItem;
        public TextView dateAdded;

        public TaskItemHolder(View view) {
            super(view);

            taskItem = view.findViewById(R.id.taskItem);
            dateAdded = view.findViewById(R.id.dateAdded);
        }
    }
}
