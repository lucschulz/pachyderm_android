package ca.lucschulz.pachyderm;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import ca.lucschulz.pachyderm.sql.SqlHelper;
import ca.lucschulz.pachyderm.taskItems.RetrieveTasks;
import ca.lucschulz.pachyderm.taskItems.TaskItem;
import ca.lucschulz.pachyderm.taskItems.TaskItemsAdapter;

public class MainActivity extends AppCompatActivity {

    private final String PACHYDERM_NOTIFICATION_ID = "pachyderm_notification_id";
    private List<TaskItem> taskList = new ArrayList<>();
    private TaskItemsAdapter tAdapter;

    private TimeZone tz;
    private Calendar calDueDate;
    private EditText etDueDate;
    private EditText etDueTime;

    private NotificationChannel channel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        configureNotificationChannel();

        configureCalendar();

        RecyclerView recyclerView = findViewById(R.id.rvMainList);
        tAdapter = new TaskItemsAdapter(this, taskList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tAdapter);

        configureEventListeners();

        RetrieveTasks retrieve = new RetrieveTasks(this);
        try {
            retrieve.retrieveTaskItems(taskList, tAdapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        etDueDate = findViewById(R.id.etDueDate);
        etDueTime = findViewById(R.id.etDueTime);
        configureDueDateCalendar(this);
        configureDueTime(this);

        configureNotifications();
        configureNotificationTapAction();


    }

    private void configureNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notif_ChannelName);
            String description = getString(R.string.notif_ChannelDescription);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(PACHYDERM_NOTIFICATION_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        channel = new NotificationChannel(PACHYDERM_NOTIFICATION_ID, "Notification", NotificationManager.IMPORTANCE_DEFAULT);
    }

    private void configureNotifications() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channel.getId())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Test notification")
                .setContentText("Test content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notif = NotificationManagerCompat.from(this);
        notif.notify(0, mBuilder.build());
    }

    private void configureNotificationTapAction() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, PACHYDERM_NOTIFICATION_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
    }

    private void configureCalendar() {
        tz = TimeZone.getDefault();
        TimeZone.setDefault(tz);
        calDueDate = Calendar.getInstance(tz);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                showAboutInfo();
                break;

            case R.id.action_settings:
                showSettings();
                break;

            default:
                break;
        }
        return false;
    }


    private void configureDueDateCalendar(final Context context) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calDueDate.set(Calendar.YEAR, year);
                calDueDate.set(Calendar.MONTH, month);
                calDueDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDueDate();
            }
        };

        etDueDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, calDueDate.get(Calendar.YEAR), calDueDate.get(Calendar.MONTH), calDueDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void configureDueTime(final Context context) {
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calDueDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calDueDate.set(Calendar.MINUTE, minute);
                updateDueDate();
            }
        };

        etDueTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(context, time, calDueDate.get(Calendar.HOUR_OF_DAY), calDueDate.get(Calendar.MINUTE), true).show();
            }
        });
    }

    private void updateDueDate() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        etDueDate.setText(date.format(calDueDate.getTime()));

        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        etDueTime.setText(time.format(calDueDate.getTime()));
    }

    private void configureEventListeners() {
        Button btnAddItem = findViewById(R.id.btnAddItem);
        EditText taskDescription = findViewById(R.id.etAddItem);
        EditText dueDate = findViewById(R.id.etDueDate);
        EditText dueTime = findViewById(R.id.etDueTime);

        AddItemClickHandler handler = new AddItemClickHandler(this, taskList, tAdapter);
        handler.configureAddItemClickListener(btnAddItem, taskDescription, dueDate, dueTime);

        clearTaskDescription();
        setClearItemsClickListener(this, tAdapter);
    }

    private void setClearItemsClickListener(final Context context, final TaskItemsAdapter adapter) {
        Button btnClearItems = findViewById(R.id.btnClearItems);
        btnClearItems.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlHelper helper = new SqlHelper(context);
                helper.clearTable();

                taskList.clear();
                adapter.notifyDataSetChanged();

                String msg = getString(R.string.toastTaskListCleared);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showAboutInfo() {
        Intent aboutScreen = new Intent(this, AboutActivity.class);
        startActivity(aboutScreen);
    }

    private void showSettings() {
        Intent settingsScreen = new Intent(this, Settings.class);
        startActivity(settingsScreen);
    }

    private void clearTaskDescription() {
        EditText etDescription = findViewById(R.id.etAddItem);
        EditText etDueDate = findViewById(R.id.etDueDate);
        EditText etDueTime = findViewById(R.id.etDueTime);

        etDescription.setText(null);
        etDueDate.setText(null);
        etDueTime.setText(null);
    }
}
