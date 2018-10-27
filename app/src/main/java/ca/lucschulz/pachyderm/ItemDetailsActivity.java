package ca.lucschulz.pachyderm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import ca.lucschulz.pachyderm.sql.SqlHelper;
import ca.lucschulz.pachyderm.taskItems.RetrieveSingleTask;
import ca.lucschulz.pachyderm.taskItems.TaskItem;

public class ItemDetailsActivity extends AppCompatActivity {

    int taskId;
    TextView tvTaskDescription;
    TextView tvDateAdded;
    TextView tvDueDate;
    TextView tvNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        String key = getString(R.string.keyTaskID);
        Bundle bundle = getIntent().getExtras();

        int id = bundle.getInt(key);
        this.taskId = id;

        instantiateControls();
        configureSaveButton();
        retrieveDetails();
    }

    private void instantiateControls() {
        tvTaskDescription = findViewById(R.id.details_tvDescription);
        tvDateAdded = findViewById(R.id.details_tvDateAdded);
        tvDueDate = findViewById(R.id.details_tvDueDate);
        tvNotes = findViewById(R.id.details_tvNotes);
    }

    private void configureSaveButton() {
        Button btnSave = findViewById(R.id.details_btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChangesToItemDetails();
            }
        });
    }

    private void saveChangesToItemDetails() {
        // TODO Save changes to database and close activity.
        // Switch back to main screen.

        String notes = String.valueOf(tvNotes.getText());

        SqlHelper helper = new SqlHelper(this);
        boolean success = helper.saveNotesToRecord(notes, taskId);
    }

    private void retrieveDetails() {
        RetrieveSingleTask retrieve = new RetrieveSingleTask(this);

        TaskItem ti = null;
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            ti = retrieve.retrieveTask(taskId);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (ti != null) {
            tvTaskDescription.setText(ti.getTaskDescription());
            tvDateAdded.setText(date.format(ti.getDateAdded()));
            tvDueDate.setText(date.format(ti.getDateDue()));
        }
    }
}
