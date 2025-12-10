package com.assignment.eleczbill;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    BillDatabase db;

    ArrayList<Integer> billIds = new ArrayList<>();
    ArrayList<String> displayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        setTitle("Saved Bills");

        listView = findViewById(R.id.listBills);
        db = new BillDatabase(this);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        loadData();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            int selectedId = billIds.get(position);

            Intent i = new Intent(ListActivity.this, DetailActivity.class);
            i.putExtra("bill_id", selectedId);
            startActivity(i);
        });
    }

    private void loadData() {

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT * FROM bills ORDER BY id DESC", null);

        billIds.clear();
        displayList.clear();

        while (c.moveToNext()) {
            billIds.add(c.getInt(0));

            String month = c.getString(1);
            double finalCost = c.getDouble(5);

            displayList.add(month + " → RM " + String.format("%.2f", finalCost));
        }
        c.close();

        BillAdapter adapter = new BillAdapter(this, displayList);
        listView.setAdapter(adapter);
    }
}
