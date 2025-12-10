package com.assignment.eleczbill;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle("About ElecZ Bill");

        TextView link = findViewById(R.id.textLink);
        Button btnBack = findViewById(R.id.btnBack);

        link.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://example.com")); // replace later
            startActivity(i);
        });

        btnBack.setOnClickListener(v -> finish());
    }
}
