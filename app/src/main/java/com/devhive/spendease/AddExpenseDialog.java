package com.devhive.spendease;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.devhive.spendease.db.Expense;
import com.devhive.spendease.db.ExpenseCategory;

import java.util.Calendar;
import java.util.List;

public class AddExpenseDialog extends DialogFragment {
    ArrayAdapter<ExpenseCategory> categoryAdapter;
    List<ExpenseCategory> categoriesList;
    @Override
    public void onStart() {
        super.onStart();

        // Set the dialog window to match the parent width
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_expense_dialog, container, false);

        ViewModel viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        EditText editTextExpenseName = view.findViewById(R.id.editTextExpenseName);
        EditText editTextExpenseAmount = view.findViewById(R.id.editTextExpenseAmount);
        EditText editTextExpenseDate = view.findViewById(R.id.editTextExpenseDate);

        AutoCompleteTextView categoryDropdown = view.findViewById(R.id.category_dropdown);
        viewModel.getAllCategories().observe(this, new Observer<List<ExpenseCategory>>() {
            @Override
            public void onChanged(List<ExpenseCategory> expenseCategories) {
                categoriesList = expenseCategories;
                categoryAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, expenseCategories);
                categoryDropdown.setAdapter(categoryAdapter);
            }
        });
        editTextExpenseDate.setOnClickListener(vi -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    editTextExpenseDate.setText(selectedDate);
                    editTextExpenseDate.setError(null);
                }
            }, year, month, day);

            // Disable future dates
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

            datePickerDialog.show();
        });

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expenseName = editTextExpenseName.getText().toString();
                if (expenseName.isEmpty()) {
                    editTextExpenseName.setError("Expense cannot be empty");
                    return;
                }

                String expenseAmount = editTextExpenseAmount.getText().toString();
                if (expenseAmount.isEmpty()) {
                    editTextExpenseAmount.setError("Amount cannot be empty");
                    return;
                }

                String expenseDate = editTextExpenseDate.getText().toString();
                if (expenseDate.isEmpty()) {
                    editTextExpenseDate.setError("Date cannot be empty");
                    return;
                }

                String selectedCategory = categoryDropdown.getText().toString().trim();
                if (selectedCategory.isEmpty()) {
                    categoryDropdown.setError("Category cannot be empty");
                    return;
                }
                long categoryId = -1;
                // Loop through adapter items to check if userInput matches a known category
                for (int i = 0; i < categoriesList.size(); i++) {
                    ExpenseCategory category = (ExpenseCategory) categoriesList.get(i);
                    if (category.getName().equalsIgnoreCase(selectedCategory)) {
                        categoryId = category.getId();
                        break;
                    }
                }
                if (categoryId == -1) {
                    categoryId = viewModel.insertCategory(new ExpenseCategory(selectedCategory));
                }
                viewModel.insertExpense(new Expense(expenseName, Double.parseDouble(expenseAmount), Utils.convertToDate(expenseDate).getTime(), categoryId));
                dismiss();
            }
        });

        return view;
    }
}
