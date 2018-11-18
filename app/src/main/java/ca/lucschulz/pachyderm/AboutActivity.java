package ca.lucschulz.pachyderm;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView tvHeader = findViewById(R.id.tvAppTitleVersion);
        String appTitle = getString(R.string.app_name);
        String appVersion = getString(R.string.app_versionNumber);
        tvHeader.setText(appTitle + " " + appVersion);

        configureLink();
    }

    private void configureLink() {
        TextView link = findViewById(R.id.tvWebSiteLink);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(R.string.app_homepage)));
                startActivity(browserIntent);
            }
        });
    }
}
