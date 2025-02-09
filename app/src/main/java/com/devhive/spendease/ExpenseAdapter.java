package com.devhive.spendease;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devhive.spendease.db.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseViewHolder> {

    ArrayList<Expense> expenseList ;
    ViewModel viewModel;

    public ExpenseAdapter(ArrayList<Expense> expenseList, ViewModel viewModel) {
        this.expenseList = expenseList;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.expense_listing_item, viewGroup, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder viewHolder, int i) {
        viewHolder.bind(expenseList.get(i), viewModel);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public void updateData(List<Expense> newExpenses) {
        expenseList.clear();
        expenseList.addAll(newExpenses);
        notifyDataSetChanged();
    }
}
