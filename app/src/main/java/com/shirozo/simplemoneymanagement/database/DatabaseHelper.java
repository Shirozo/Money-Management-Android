package com.shirozo.simplemoneymanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.shirozo.simplemoneymanagement.classes.Money;
import com.shirozo.simplemoneymanagement.classes.Transaction;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

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


    public ArrayList<Money> getMonthData(String date) {

        String[] parts = date.split("-");
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT strftime('%d-%m-%Y', created_at) AS month_day, " +
                "SUM(CASE WHEN type = 0 THEN amount ELSE 0 END) AS total_income, " +
                "SUM(CASE WHEN type = 1 THEN amount ELSE 0 END) AS total_expenses, " +
                "SUM(CASE WHEN type = 0 THEN amount ELSE 0 END) - SUM(CASE WHEN type = 1 THEN amount ELSE 0 END) AS save " +
                "FROM money " +
                "WHERE strftime('%Y', created_at) = ? " +
                "GROUP BY month_day " +
                "ORDER BY month_day ASC",
                new String[]{parts[0]});

        ArrayList<Money> monies = new ArrayList<>();
        int id = 1;
        while (cursor.moveToNext()) {
            Money money = new Money(id,
                    cursor.getFloat(1),
                    cursor.getFloat(2),
                    cursor.getFloat(3),
                    cursor.getString(0));
            monies.add(money);
        }
        cursor.close();
        return monies;
    }

    public ArrayList<Money> getPerMonth() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT strftime('%Y-%m', created_at) AS month_year," +
                "SUM(CASE WHEN type = 0 THEN amount ELSE 0 END) AS total_income," +
                "SUM(CASE WHEN type = 1 THEN amount ELSE 0 END) AS total_expenses," +
                "SUM(CASE WHEN type = 0 THEN amount ELSE 0 END) - SUM(CASE WHEN type = 1 THEN amount ELSE 0 END) AS saved " +
                "FROM money GROUP BY month_year " +
                "ORDER BY month_year DESC", new String[]{});

        ArrayList<Money> monies = new ArrayList<>();

        int id = 1;
        while (cursor.moveToNext()) {
            Money money = new Money(id,
                    cursor.getFloat(1),
                    cursor.getFloat(2),
                    cursor.getFloat(3),
                    cursor.getString(0));
            monies.add(money);
            id++;
        }
        cursor.close();
        return  monies;
    }
    public boolean insertData(TextInputEditText description, TextInputEditText amount, Integer type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", Objects.requireNonNull(description.getText()).toString().trim());
        contentValues.put("amount", Objects.requireNonNull(amount.getText()).toString().trim());
        contentValues.put("type", type);

        long result = db.insert("money", null, contentValues);
        return result != -1;
    }

    public ArrayList<Transaction> getPerDay(String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] parts = date.split("-");
        Cursor cursor = db.rawQuery("SELECT id," +
                        "description, " +
                        "amount, " +
                        "type, " +
                        "strftime('%Y-%m-%d %H:%M:%S', created_at) AS time " +
                        "FROM money " +
                        "WHERE strftime('%Y', created_at) = ? AND " +
                        "strftime('%m', created_at) = ? AND " +
                        "strftime('%d', created_at) = ?",
                new String[]{parts[2], parts[1], parts[0]});

        ArrayList<Transaction> transactions = new ArrayList<>();

        while (cursor.moveToNext()) {
            Transaction transaction = new Transaction(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4));
            transactions.add(transaction);
        }

        cursor.close();

        return transactions;
    }

    public String[] getPerDaySummary(String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] parts = date.split("-");
        Log.e("date", date);
        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "SUM(CASE WHEN type = 0 THEN amount ELSE 0 END) AS total_income, " +
                        "SUM(CASE WHEN type = 1 THEN amount ELSE 0 END) AS total_expenses, " +
                        "SUM(CASE WHEN type = 0 THEN amount ELSE 0 END) - SUM(CASE WHEN type = 1 THEN amount ELSE 0 END) AS saved " +
                        "FROM money " +
                        "WHERE strftime('%Y', created_at) = ? AND strftime('%m', created_at) = ? AND strftime('%d', created_at) = ?",
                new String[]{parts[2], parts[1], parts[0]}
        );
        cursor.moveToFirst();
        return new String[]{
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2)
        };
    }

    public String[] getPerMonthSummary(String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] parts = date.split("-");
        Log.e("date", date);
        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "SUM(CASE WHEN type = 0 THEN amount ELSE 0 END) AS total_income, " +
                        "SUM(CASE WHEN type = 1 THEN amount ELSE 0 END) AS total_expenses, " +
                        "SUM(CASE WHEN type = 0 THEN amount ELSE 0 END) - SUM(CASE WHEN type = 1 THEN amount ELSE 0 END) AS saved " +
                        "FROM money " +
                        "WHERE strftime('%Y', created_at) = ? AND strftime('%m', created_at) = ?",
                new String[]{parts[1], parts[0]}
        );
        cursor.moveToFirst();
        return new String[]{
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2)
        };
    }
}
