package ca.lucschulz.pachyderm;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import ca.lucschulz.pachyderm.sql.SqlHelper;
import ca.lucschulz.pachyderm.taskItems.AddTask;
import ca.lucschulz.pachyderm.taskItems.RetrieveTasks;
import ca.lucschulz.pachyderm.taskItems.TaskItem;
import ca.lucschulz.pachyderm.taskItems.TaskItemsAdapter;

public class MainActivity extends AppCompatActivity {

    private List<TaskItem> taskList = new ArrayList<>();
    private TaskItemsAdapter tAdapter;

    private TimeZone tz;
    private Calendar dueDateCalendar;
    private EditText etDueDate;
    private EditText etDueTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        configureCalendar();

        RecyclerView recyclerView = findViewById(R.id.rvMainList);
        tAdapter = new TaskItemsAdapter(this, taskList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tAdapter);

        configureEventListeners();

        RetrieveTasks retrieve = new RetrieveTasks();
        retrieve.retrieveTaskItems(this, taskList, tAdapter);

        etDueDate = findViewById(R.id.etDueDate);
        etDueTime = findViewById(R.id.etDueTime);
        configureDueDateCalendar(this);
        configureDueTime(this);
    }

    private void configureCalendar() {
        tz = TimeZone.getDefault();
        TimeZone.setDefault(tz);
        dueDateCalendar = Calendar.getInstance(tz);
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
                dueDateCalendar.set(Calendar.YEAR, year);
                dueDateCalendar.set(Calendar.MONTH, month);
                dueDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDueDate();
            }
        };

        etDueDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, dueDateCalendar.get(Calendar.YEAR), dueDateCalendar.get(Calendar.MONTH), dueDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void configureDueTime(final Context context) {
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                dueDateCalendar.set(Calendar.HOUR, hourOfDay);
                dueDateCalendar.set(Calendar.MINUTE, minute);
                updateDueDate();
            }
        };

        etDueTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(context, time, dueDateCalendar.get(Calendar.HOUR), dueDateCalendar.get(Calendar.MINUTE), true).show();
            }
        });
    }

    private void updateDueDate() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        etDueDate.setText(date.format(dueDateCalendar.getTime()));

        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        etDueTime.setText(time.format(dueDateCalendar.getTime()));
    }

    private void configureEventListeners() {
        setAddItemClickListener();
        setClearItemsClickListener(this, tAdapter);
    }

    private void setAddItemClickListener() {
        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText taskDescription = findViewById(R.id.etAddItem);

                AddTask addTask = new AddTask(getApplicationContext());
                addTask.addNewTask(taskDescription, taskList, tAdapter);

                clearTaskDescription();
            }
        });
    }

    private void clearTaskDescription() {
        EditText et = findViewById(R.id.etAddItem);
        et.setText(null);
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

                Toast.makeText(getApplicationContext(), "Task list cleared.", Toast.LENGTH_SHORT).show();
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
}
