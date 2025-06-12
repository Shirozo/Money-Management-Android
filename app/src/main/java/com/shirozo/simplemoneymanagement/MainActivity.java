package com.shirozo.simplemoneymanagement;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shirozo.simplemoneymanagement.classes.Money;

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

        //! Testing data only
        ArrayList<Money> monthlies = new ArrayList<>();
        Money money = new Money(1, (float) 100, (float) 150, "December 2025");
        monthlies.add(money);

        ListView listView = findViewById(R.id.monthly_summary);
        ListViewAdapter adapter = new ListViewAdapter(this, monthlies);
        listView.setAdapter(adapter);
    }
}