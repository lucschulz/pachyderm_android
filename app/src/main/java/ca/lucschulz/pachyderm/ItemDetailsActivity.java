package ca.lucschulz.pachyderm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ca.lucschulz.pachyderm.sql.SqlHelper;

public class ItemDetailsActivity extends AppCompatActivity {

    int taskId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        String key = getString(R.string.keyTaskID);
        Bundle bundle = getIntent().getExtras();

        int id = bundle.getInt(key);
        this.taskId = id;

        TextView tv = findViewById(R.id.details_tvDescription);
//        tv.setText(String.valueOf(id));
        tv.setText("DESCRIPTION OF A TASK");
    }

    private void retrieveDetails() {
        SqlHelper helper = new SqlHelper(this);

    }
}
