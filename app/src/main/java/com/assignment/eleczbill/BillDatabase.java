package com.assignment.eleczbill;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BillDatabase extends SQLiteOpenHelper {

    public BillDatabase(Context context) {
        super(context, "eleczbill.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE bills (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "month TEXT," +
                        "units INTEGER," +
                        "rebate REAL," +
                        "total REAL," +
                        "final REAL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void insertBill(String month, int units, double rebate, double total, double finalCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO bills (month, units, rebate, total, final) VALUES (?, ?, ?, ?, ?)",
                new Object[]{month, units, rebate, total, finalCost});
    }
}
