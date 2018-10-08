package ca.lucschulz.pachyderm.taskItems;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import ca.lucschulz.pachyderm.sql.SqlHelper;

public class AddTask {

    private Context context;

    public AddTask(Context context) {
        this.context = context;
    }


    public void addNewTask(EditText taskDescription, List<TaskItem> taskList, TaskItemsAdapter tAdapter) {

        String taskName = String.valueOf(taskDescription.getText());

        if (taskName.length() > 0) {
            SqlHelper helper = new SqlHelper(context);
            helper.insertNewTaskItem(taskName);

            taskList.clear();

            RetrieveTasks retrieve = new RetrieveTasks();
            retrieve.retrieveTaskItems(context, taskList, tAdapter);
            tAdapter.notifyDataSetChanged();

            Toast.makeText(context, "Item added.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Please enter a task description.", Toast.LENGTH_SHORT).show();
        }
    }
}
