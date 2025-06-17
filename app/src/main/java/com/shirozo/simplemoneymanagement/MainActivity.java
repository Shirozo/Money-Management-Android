package com.shirozo.simplemoneymanagement;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shirozo.simplemoneymanagement.adapter.ListViewAdapter;
import com.shirozo.simplemoneymanagement.classes.Money;
import com.shirozo.simplemoneymanagement.database.DatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LoadData();

        FloatingActionButton button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(v -> {
            CustomModal modal = new CustomModal(this);
            modal.setOnAddedListener(this::LoadData);
            modal.show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadData();
    }

    public void LoadData() {
        int phase = 1;

        DatabaseHelper db = new DatabaseHelper(this);

        String[] cardData = db.getSummary();

        TextView income = findViewById(R.id.total_income);
        TextView expense = findViewById(R.id.total_expenses);
        TextView saved = findViewById(R.id.total_saved);

        income.setText("₱ " + cardData[0]);
        expense.setText("₱ " + cardData[1]);
        saved.setText("₱ " + cardData[2]);

        ArrayList<Money> monthlies = db.getPerMonth();

        ListView listView = findViewById(R.id.monthly_summary);
        listView.setDivider(null);
        listView.setDividerHeight(10);
        ListViewAdapter adapter = new ListViewAdapter(this, monthlies, phase);
        listView.setAdapter(adapter);
    }
}