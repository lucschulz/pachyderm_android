package ca.lucschulz.pachyderm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TaskItems> taskList = new ArrayList<>();
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

        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText et = (EditText)findViewById(R.id.etAddItem);
                String text = String.valueOf(et.getText());

                addNewTaskItem(text, new Date());
            }
        });


    }

    private void addNewTaskItem(String taskName, Date date) {

        TaskItems ti = new TaskItems();
        ti.setTaskItem(taskName);
        ti.setDateAdded(date);
        taskList.add(ti);
        mAdapter.notifyDataSetChanged();

        Toast.makeText(getApplicationContext(), "Item added.", Toast.LENGTH_SHORT).show();
    }

    private void addTaskItems() {
        TaskItems ti = new TaskItems("Example task", new Date(), false);
        taskList.add(ti);

        TaskItems ti2 = new TaskItems("Another example", new Date(), false);
        taskList.add(ti2);

        mAdapter.notifyDataSetChanged();
    }
}
