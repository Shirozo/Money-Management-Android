package com.shirozo.simplemoneymanagement;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.shirozo.simplemoneymanagement.database.DatabaseHelper;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomModal {

    private final Context context;

    private Dialog dialog;
    private int type = -1;
    public CustomModal(Context context) {
        this.context = context;
    }

    public void show() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.transaction_modal);
        dialog.setCancelable(false);

        AtomicBoolean valid = new AtomicBoolean(true);

        RadioGroup radioGroup = dialog.findViewById(R.id.radio_group);

        TextView type_error = dialog.findViewById(R.id.type_required);
        TextView description_error = dialog.findViewById(R.id.description_required);
        TextView amount_error = dialog.findViewById(R.id.amount_required);

        TextInputEditText amount = dialog.findViewById(R.id.amount);
        TextInputEditText description = dialog.findViewById(R.id.description);


        RadioButton income = dialog.findViewById(R.id.radio_income);
        income.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                type = 0;
            }
        });


        RadioButton expense = dialog.findViewById(R.id.radio_expense);
        expense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                type = 1;
            }
        });


        Button add = dialog.findViewById(R.id.add_button);
        add.setOnClickListener(v -> {
            if (type == -1) {
                valid.set(false);
                type_error.setVisibility(View.VISIBLE);
            } else {
                type_error.setVisibility(View.GONE);
                valid.set(true);
            }

            if (description.getText().toString().trim().isEmpty()) {
                valid.set(false);
                description_error.setVisibility(View.VISIBLE);
            } else {
                valid.set(true);
                description_error.setVisibility(View.GONE);
            }

            if (amount.getText().toString().trim().isEmpty() || Integer.parseInt(amount.getText().toString().trim()) <= 0) {
                valid.set(false);
                amount_error.setVisibility(View.VISIBLE);
            } else {
                valid.set(true);
                amount_error.setVisibility(View.GONE);
            }

            if (valid.get()) {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                boolean result = databaseHelper.insertData(description, amount, type);
                if (result) {
                    description.setText("");
                    amount.setText("");
                    radioGroup.clearCheck();
                    Toast.makeText(context, "Data added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error Adding Data!", Toast.LENGTH_SHORT).show();
                }
            }

        });


        Button cancel = dialog.findViewById(R.id.cancel_dialog);
        cancel.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
    }
}
