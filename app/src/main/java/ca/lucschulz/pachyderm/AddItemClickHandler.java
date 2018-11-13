package ca.lucschulz.pachyderm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ca.lucschulz.pachyderm.notifications.Notifications;
import ca.lucschulz.pachyderm.taskItems.AddTask;
import ca.lucschulz.pachyderm.taskItems.TaskItem;
import ca.lucschulz.pachyderm.taskItems.TaskItemsAdapter;

public class AddItemClickHandler {

    private Context context;
    private List<TaskItem> taskList;
    private TaskItemsAdapter tAdapter;
    private NotificationChannel channel;
    private NotificationManagerCompat nmc;

    public AddItemClickHandler(Context context, List<TaskItem> taskList, TaskItemsAdapter tAdapter, NotificationChannel channel, NotificationManagerCompat nmc) {
        this.context = context;
        this.taskList = taskList;
        this.tAdapter = tAdapter;
        this.channel = channel;
        this.nmc = nmc;
    }

    public void configureAddItemClickListener(Button btnAddItem, final EditText taskDescription, final EditText dueDate, final EditText dueTime) {
//        Button btnAddItem = getActivity().findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                EditText tvTaskDescription = findViewById(R.id.etAddItem);
//                EditText tvDueDate = findViewById(R.id.etDueDate);
//                EditText dueTime = findViewById(R.id.etDueTime);

                String stringDueDate = String.valueOf(dueDate.getText());
                String stringDueTime = String.valueOf(dueTime.getText());
                Date formattedDate = null;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                try {
                    formattedDate = sdf.parse(stringDueDate + " " + stringDueTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TaskItem taskItem = new TaskItem();
                taskItem.setTaskDescription(String.valueOf(taskDescription.getText()));
                taskItem.setDateDue(formattedDate);


                AddTask addTask = new AddTask(context, channel, nmc);
                try {
                    addTask.addNewTask(taskItem, taskList, tAdapter);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
