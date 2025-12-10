package com.assignment.eleczbill;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerMonth;
    EditText editUnits, editRebate;
    Button btnCalculate, btnViewHistory, btnAbout;
    TextView textResult;
    BillDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("ElecZ Bill Calculator");

        spinnerMonth = findViewById(R.id.spinnerMonth);
        editUnits = findViewById(R.id.editUnits);
        editRebate = findViewById(R.id.editRebate);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnViewHistory = findViewById(R.id.btnView);
        btnAbout = findViewById(R.id.btnAbout);
        textResult = findViewById(R.id.textResult);

        db = new BillDatabase(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{
                        "- Please select Month -",
                        "Jan","Feb","Mar","Apr","May","Jun",
                        "Jul","Aug","Sep","Oct","Nov","Dec"
                }
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);

        btnCalculate.setOnClickListener(v -> calculateAndSave());
        btnViewHistory.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ListActivity.class)));
        btnAbout.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AboutActivity.class)));
    }

    private void calculateAndSave() {

        if (spinnerMonth.getSelectedItem().toString().equals("- Please select Month -")) {
            Toast.makeText(this, "Please select a month", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editUnits.getText().toString().isEmpty()
                || editRebate.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int units = Integer.parseInt(editUnits.getText().toString());
        double rebate = Double.parseDouble(editRebate.getText().toString());

        if (units < 0) {
            Toast.makeText(this, "Units cannot be negative", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rebate < 0 || rebate > 5) {
            Toast.makeText(this, "Rebate must be between 0% and 5%", Toast.LENGTH_SHORT).show();
            return;
        }

        String month = spinnerMonth.getSelectedItem().toString();

        double total = calculateCharges(units);
        double finalCost = total - (total * rebate / 100);

        textResult.setText(
                "Total Charges: RM " + String.format("%.2f", total) +
                        "\nFinal After Rebate: RM " + String.format("%.2f", finalCost)
        );

        db.insertBill(month, units, rebate, total, finalCost);

        Toast.makeText(this, "Saved to history!", Toast.LENGTH_SHORT).show();

        editUnits.setText("");
        editRebate.setText("");
        spinnerMonth.setSelection(0);
    }

    private double calculateCharges(int u) {
        if (u <= 200)
            return u * 0.218;
        else if (u <= 300)
            return (200 * 0.218) + (u - 200) * 0.334;
        else if (u <= 600)
            return (200 * 0.218) + (100 * 0.334) + (u - 300) * 0.516;
        else
            return (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + (u - 600) * 0.546;
    }
}
