package com.devhive.spendease;

import com.devhive.spendease.db.Expense;
import com.devhive.spendease.db.ExpenseCategory;

import java.util.List;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ViewModel extends AndroidViewModel {

    private final ExpenseRepository expenseRepository;
    private final LiveData<List<Expense>> allExpenses;
    private final LiveData<List<ExpenseCategory>> allCategories;

    public ViewModel(Application application) {
        super(application);
        expenseRepository = new ExpenseRepository(application);
        allExpenses = expenseRepository.getAllExpenses();
        allCategories = expenseRepository.getAllCategories();
    }

    public void insertExpense(Expense expense) {
        expenseRepository.insertExpense(expense);
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }

    public long insertCategory(ExpenseCategory category) {
        return expenseRepository.insertCategory(category);
    }

    public LiveData<List<ExpenseCategory>> getAllCategories() {
        return allCategories;
    }

    public LiveData<String> getCategoryNameById(long id) {
        return expenseRepository.getCategoryNameById(id);
    }
}
