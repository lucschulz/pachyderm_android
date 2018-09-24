package ca.lucschulz.pachyderm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //private List<Book> bookList = new ArrayList<>();
    private List<TaskItems> taskList = new ArrayList<>();

    private TaskItemsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rvMainList);

//        mAdapter = new BookAdapter(bookList);
        mAdapter = new TaskItemsAdapter(taskList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        addTaskItems();

//        SqlHelper sqlHelper = new SqlHelper(this);

        Toast.makeText(getApplicationContext(), "This is a toast message.", Toast.LENGTH_SHORT).show();
    }

    private void addTaskItems() {
        TaskItems ti = new TaskItems("Example task", new Date(), false);
        taskList.add(ti);

        TaskItems ti2 = new TaskItems("Another example", new Date(), false);
        taskList.add(ti2);
    }
}
