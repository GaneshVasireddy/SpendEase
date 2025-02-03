package com.devhive.spendease;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseViewHolder> {

    ArrayList<ExpenseModel> expenseList ;

    public ExpenseAdapter(ArrayList<ExpenseModel> expenseList) {
        this.expenseList = expenseList;
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
        viewHolder.bind(expenseList.get(i));
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }
}
