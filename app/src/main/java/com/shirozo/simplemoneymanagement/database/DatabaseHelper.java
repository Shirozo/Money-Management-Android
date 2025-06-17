package com.shirozo.simplemoneymanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.shirozo.simplemoneymanagement.classes.Money;
import com.shirozo.simplemoneymanagement.classes.Transaction;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "money", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS money( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "description TEXT, " +
                "amount FLOAT, " +
                "type INTEGER," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS money");
    }


    public ArrayList<Money> getPerMonth() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT strftime('%Y-%m', created_at) AS month_year," +
                "SUM(CASE WHEN type = 0 THEN amount ELSE 0 END) AS total_income," +
                "SUM(CASE WHEN type = 1 THEN amount ELSE 0 END) AS total_expenses," +
                "SUM(CASE WHEN type = 0 THEN amount ELSE 0 END) - SUM(CASE WHEN type = 1 THEN amount ELSE 0 END) AS saved " +
                "FROM money GROUP BY month_year " +
                "ORDER BY month_year DESC", new String[]{});

        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

        ArrayList<Money> monies = new ArrayList<>();

        int id = 1;
        while (cursor.moveToNext()) {

            String date = cursor.getString(0);
            String[] parts = date.split("-");
            String newDate = months[Integer.parseInt(parts[1]) - 1] + " " + parts[0];

            Money money = new Money(id,
                    cursor.getFloat(1),
                    cursor.getFloat(2),
                    cursor.getFloat(3),
                    newDate);
            monies.add(money);
            id++;
        }
        cursor.close();
        return  monies;
    }
    public boolean insertData(TextInputEditText description, TextInputEditText amount, Integer type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", description.getText().toString().trim());
        contentValues.put("amount", amount.getText().toString().trim());
        contentValues.put("type", type);

        long result = db.insert("money", null, contentValues);
        return result != -1;
    }

    public ArrayList<Transaction> getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM money", new String[]{});

        ArrayList<Transaction> transactions = new ArrayList<>();

        while (cursor.moveToNext()) {
            Transaction transaction = new Transaction(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4)
                    );
            transactions.add(transaction);
        }

        cursor.close();

        return transactions;
    }
}
