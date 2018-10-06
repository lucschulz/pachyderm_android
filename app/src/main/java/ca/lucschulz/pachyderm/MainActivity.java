package ca.lucschulz.pachyderm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ca.lucschulz.pachyderm.sql.SqlHelper;

public class MainActivity extends AppCompatActivity {

    private List<TaskItem> taskList = new ArrayList<>();
    private TaskItemsAdapter tAdapter;
    private Calendar dueDateCalendar = Calendar.getInstance();
    private EditText etDueDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        RecyclerView recyclerView = findViewById(R.id.rvMainList);

        tAdapter = new TaskItemsAdapter(this, taskList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tAdapter);

        configureEventListeners();
        retrieveTaskItems();

        etDueDate = findViewById(R.id.etDueDate);
        configureDueDateCalendar(this);
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
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, dueDateCalendar
                        .get(Calendar.YEAR), dueDateCalendar.get(Calendar.MONTH),
                        dueDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateDueDate() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDueDate.setText(sdf.format(dueDateCalendar.getTime()));
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
                String text = String.valueOf(et.getText());
                addNewTaskItem(text);
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

    private void addNewTaskItem(String taskName) {

        SqlHelper helper = new SqlHelper(this);
        helper.insertNewTaskItem(taskName);

        taskList.clear();

        retrieveTaskItems();
        tAdapter.notifyDataSetChanged();

        Toast.makeText(getApplicationContext(), "Item added.", Toast.LENGTH_SHORT).show();
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
