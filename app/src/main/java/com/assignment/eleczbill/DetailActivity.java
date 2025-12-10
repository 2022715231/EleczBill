package com.assignment.eleczbill;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class DetailActivity extends AppCompatActivity {

    TextView textDetail;
    BillDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("Bill Details");

        textDetail = findViewById(R.id.textDetail);
        Button btnBackList = findViewById(R.id.btnBackList);
        Button btnBackMenu = findViewById(R.id.btnBackMenu);

        db = new BillDatabase(this);

        // Get selected bill ID from ListActivity
        int id = getIntent().getIntExtra("bill_id", -1);

        if (id != -1) {
            loadBillDetails(id);
        } else {
            textDetail.setText("Error: Bill details not found.");
        }

        // Go back to History List
        btnBackList.setOnClickListener(v -> finish());

        // Go back to Main Menu
        btnBackMenu.setOnClickListener(v ->
                startActivity(new Intent(DetailActivity.this, MainActivity.class))
        );
    }

    private void loadBillDetails(int id) {

        // IMPORTANT FIX → correct column name is "final", NOT "finalCost"
        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT id, month, units, rebate, total, final FROM bills WHERE id = ?",
                new String[]{String.valueOf(id)}
        );

        if (c.moveToFirst()) {

            String month = c.getString(1);
            int units = c.getInt(2);
            double rebate = c.getDouble(3);
            double total = c.getDouble(4);
            double finalCost = c.getDouble(5);  // index 5 = column 'final'

            String details =
                    "📅 Month: " + month +
                            "\n⚡ Units Used: " + units +
                            "\n💸 Rebate: " + rebate + "%" +
                            "\n🧾 Total Charges: RM " + String.format("%.2f", total) +
                            "\n✅ Final Cost: RM " + String.format("%.2f", finalCost);

            textDetail.setText(details);
        } else {
            textDetail.setText("No details found for this bill.");
        }

        c.close();
    }
}
