package com.devhive.spendease;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExpenseViewHolder extends RecyclerView.ViewHolder {

    TextView expenseName;
    TextView categoryName;
    TextView expense;
    TextView expenseDate;

    public ExpenseViewHolder(@NonNull View itemView) {
        super(itemView);
        expenseName = itemView.findViewById(R.id.expense_name);
        categoryName = itemView.findViewById(R.id.category_name);
        expense = itemView.findViewById(R.id.expense);
        expenseDate = itemView.findViewById(R.id.expense_date);
    }

    public void bind(ExpenseModel expenseModel) {
        expenseName.setText(expenseModel.getName());
        categoryName.setText("Category: " + expenseModel.getCategory());
        expense.setText("Rs. " + expenseModel.getAmount() + " /-");
        expenseDate.setText("Date: " + Utils.convertDateToString(expenseModel.getDate()));
    }
}
