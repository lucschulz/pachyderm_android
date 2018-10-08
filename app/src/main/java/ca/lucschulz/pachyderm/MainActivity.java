package ca.lucschulz.pachyderm;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import ca.lucschulz.pachyderm.sql.SqlHelper;
import ca.lucschulz.pachyderm.taskItems.TaskItem;
import ca.lucschulz.pachyderm.taskItems.TaskItemsAdapter;

public class MainActivity extends AppCompatActivity {

    private List<TaskItem> taskList = new ArrayList<>();
    private TaskItemsAdapter tAdapter;


    private TimeZone tz = TimeZone.getTimeZone(ZoneId.of("Canada/Eastern"));
    private Calendar dueDateCalendar;
    private EditText etDueDate;
    private EditText etDueTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        RecyclerView recyclerView = findViewById(R.id.rvMainList);

        TimeZone.setDefault(tz);
        dueDateCalendar = Calendar.getInstance(tz);

        tAdapter = new TaskItemsAdapter(this, taskList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tAdapter);

        configureEventListeners();
        retrieveTaskItems();

        etDueDate = findViewById(R.id.etDueDate);
        etDueTime = findViewById(R.id.etDueTime);
        configureDueDateCalendar(this);
        configureDueTime(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
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
                EditText et = findViewById(R.id.etAddItem);
                addNewTaskItem(et);
            }
        });
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

    private void addNewTaskItem(EditText et) {
        String taskName = String.valueOf(et.getText());

        if (taskName.length() > 0) {
            SqlHelper helper = new SqlHelper(this);
            helper.insertNewTaskItem(taskName);

            taskList.clear();

            retrieveTaskItems();
            tAdapter.notifyDataSetChanged();

            Toast.makeText(getApplicationContext(), "Item added.", Toast.LENGTH_SHORT).show();

            clearTaskDescription();
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter a task description.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearTaskDescription() {
        EditText et = findViewById(R.id.etAddItem);
        et.setText(null);
    }

    private void retrieveTaskItems() {
        SqlHelper helper = new SqlHelper(this);
        List<TaskItem> list = helper.retrieveItems();

        for (TaskItem ti : list) {
            String taskId = ti.getTaskId();
            String name = ti.getTaskItem();
            Date date = ti.getDateAdded();
            Boolean completed = ti.getCompleted();

            populateList(taskId, name, date, completed);
        }
    }

    private void populateList(String taskId, String taskName, Date date, Boolean completed) {
        TaskItem ti = new TaskItem();
        ti.setTaskId(taskId);
        ti.setTaskItem(taskName);
        ti.setDateAdded(date);
        ti.setCompleted(completed);

        taskList.add(ti);
        tAdapter.notifyDataSetChanged();
    }
}
