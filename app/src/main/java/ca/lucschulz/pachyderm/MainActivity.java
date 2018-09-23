package ca.lucschulz.pachyderm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
//import java.lang.Object;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Book> bookList = new ArrayList<>();
    private BookAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rvMainList);

        mAdapter = new BookAdapter(bookList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        initBookData();

        SqlHelper sqlHelper = new SqlHelper(this);

        Toast.makeText(getApplicationContext(), "This is a toast message.", Toast.LENGTH_SHORT).show();
    }


    private void initBookData()
    {
        Book book = new Book("Hello Android", "Ed Burnette");
        bookList.add(book);

        Book book2 = new Book("Beginning Android 3", "Mark Murphy");
        bookList.add(book2);

        mAdapter.notifyDataSetChanged();
    }
}
