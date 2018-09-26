package ca.lucschulz.pachyderm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private List<TaskItem> taskList = new ArrayList<>();
    private TaskItemsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rvMainList);

        mAdapter = new TaskItemsAdapter(taskList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

//        SqlHelper helper = new SqlHelper(this);
//        helper.clearTable();

        retrieveTaskItems();


        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.etAddItem);
                String text = String.valueOf(et.getText());

                Date currentDate = Calendar.getInstance(TimeZone.getTimeZone("EST")).getTime();
                addNewTaskItem(text, currentDate);
            }
        });
    }

    private void addNewTaskItem(String taskName, Date date) {

        TaskItem ti = new TaskItem();
        ti.setTaskItem(taskName);
        ti.setDateAdded(date);
        taskList.add(ti);
        mAdapter.notifyDataSetChanged();

        SqlHelper helper = new SqlHelper(this);
        helper.insertNewTaskItem(taskName, date);

        Toast.makeText(getApplicationContext(), "Item added.", Toast.LENGTH_SHORT).show();
    }

    private void populateList(String taskName, Date date) {

        TaskItem ti = new TaskItem();
        ti.setTaskItem(taskName);
        ti.setDateAdded(date);
        taskList.add(ti);
        mAdapter.notifyDataSetChanged();

        Toast.makeText(getApplicationContext(), "List populated.", Toast.LENGTH_SHORT).show();
    }

    private void retrieveTaskItems() {
        SqlHelper helper = new SqlHelper(this);
        List<TaskItem> list = helper.retrieveItems();

        for (TaskItem ti : list) {
            String name = ti.getTaskItem();
            populateList(name, new Date());
        }
    }
}
