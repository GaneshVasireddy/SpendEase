package com.devhive.spendease;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class AddExpenseDialog extends AlertDialog {

    protected AddExpenseDialog(@NonNull Context context, ExpenseController expenseController,
                               OnAddExpenseDialogListener listener,
                               boolean isCategoryAddition) {
        super(context);

        if (isCategoryAddition) {
            View dialogView = getLayoutInflater().inflate(R.layout.add_category_dialog, null);
            setView(dialogView);

            EditText categoryName = dialogView.findViewById(R.id.editTextCategoryName);

            show();

            dialogView.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            dialogView.findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String category = categoryName.getText().toString();
                    if (!category.isEmpty()) {
                        if (listener != null) {
                            listener.onCategoryAdded(category);
                        }
                        dismiss();
                    } else {
                        categoryName.setError("Category cannot be empty");
                    }
                }
            });
        } else {
            View dialogView = getLayoutInflater().inflate(R.layout.add_expense_dialog, null);
            setView(dialogView);

            EditText editTextExpenseName = dialogView.findViewById(R.id.editTextExpenseName);
            EditText editTextExpenseAmount = dialogView.findViewById(R.id.editTextExpenseAmount);
            EditText editTextExpenseDate = dialogView.findViewById(R.id.editTextExpenseDate);

//            TextInputLayout categorySpinner = dialogView.findViewById(R.id.spinnerCategory);
            AutoCompleteTextView categoryDropdown = dialogView.findViewById(R.id.category_dropdown);
            ArrayList<String> categories = expenseController.getCategoryList();
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, categories);
            categoryDropdown.setAdapter(categoryAdapter);

            editTextExpenseDate.setOnClickListener(vi -> {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editTextExpenseDate.setText(selectedDate);
                        editTextExpenseDate.setError(null);
                    }
                }, year, month, day);

                datePickerDialog.show();
            });

            show();

            dialogView.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            dialogView.findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String expenseName = editTextExpenseName.getText().toString();
                    String expenseAmount = editTextExpenseAmount.getText().toString();
                    String expenseDate = editTextExpenseDate.getText().toString();
                    String category = categoryDropdown.getText().toString();
                    if (expenseName.isEmpty()) {
                        editTextExpenseName.setError("Expense cannot be empty");
                    }
                    if (expenseAmount.isEmpty()) {
                        editTextExpenseAmount.setError("Amount cannot be empty");
                    }
                    if (expenseDate.isEmpty()) {
                        editTextExpenseDate.setError("Date cannot be empty");
                    }
                    if (category.isEmpty()) {
                        categoryDropdown.setError("Category cannot be empty");
                    }
                    if (!expenseName.isEmpty() && !expenseAmount.isEmpty() && !expenseDate.isEmpty() && !category.isEmpty()) {
                        if (listener != null) {
                            listener.onCategoryAdded(category);
                            listener.onExpenseAdded(editTextExpenseName.getText().toString(),
                                    categoryDropdown.getText().toString(),
                                    Double.parseDouble(editTextExpenseAmount.getText().toString()),
                                    Utils.convertToDate(editTextExpenseDate.getText().toString())
                            );
                        }
                        dismiss();
                    }
                }
            });
        }
    }
}
