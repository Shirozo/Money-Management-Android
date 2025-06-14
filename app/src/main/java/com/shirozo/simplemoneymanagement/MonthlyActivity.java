package com.shirozo.simplemoneymanagement;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shirozo.simplemoneymanagement.adapter.ListViewAdapter;
import com.shirozo.simplemoneymanagement.classes.Money;

import java.util.ArrayList;

public class MonthlyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_montly);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<Money> monthlies = new ArrayList<>();
        Money money = new Money(1, (float) 100, (float) 150, "December 21");
        monthlies.add(money);

        int phase = 2;

        ListView listView = findViewById(R.id.daily_summary);
        ListViewAdapter adapter = new ListViewAdapter(this, monthlies, phase);
        listView.setAdapter(adapter);
    }
}