package com.devhive.spendease;

import android.content.Context;

import com.devhive.spendease.db.Expense;
import com.devhive.spendease.db.ExpenseCategory;
import com.devhive.spendease.db.ExpenseCategoryDao;
import com.devhive.spendease.db.ExpenseDao;
import com.devhive.spendease.db.SpendEaseDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import java.util.concurrent.Future;

public class ExpenseRepository {

    private final ExpenseDao expenseDao;
    private final ExpenseCategoryDao expenseCategoryDao;
    private final ExecutorService executorService;

    public ExpenseRepository(Context context) {
        SpendEaseDatabase db = SpendEaseDatabase.getInstance(context);
        expenseDao = db.expenseDao();
        expenseCategoryDao = db.expenseCategoryDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insertExpense(Expense expense) {
        executorService.execute(() -> expenseDao.insert(expense));
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return expenseDao.getAllExpenses();
    }

    public long insertCategory(ExpenseCategory category) {
        Future<Long> future = executorService.submit(() -> expenseCategoryDao.insert(category));

        try {
            return future.get(); // Wait for the result and return the ID
        } catch (Exception e) {
            return -1; // Return -1 if there's an error
        }
    }

    public LiveData<List<ExpenseCategory>> getAllCategories() {
        return expenseCategoryDao.getAllCategories();
    }

    public LiveData<String> getCategoryNameById(long categoryId) {
        return expenseCategoryDao.getCategoryNameById(categoryId);
    }

    public LiveData<Integer> getCategoryIdByName(String categoryName) {
        return expenseCategoryDao.getCategoryIdByName(categoryName);
    }
}