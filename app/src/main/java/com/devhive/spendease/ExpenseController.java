package com.devhive.spendease;

import java.util.ArrayList;
import java.util.Date;

public class ExpenseController {

    private final ArrayList<ExpenseModel> expenseList = new ArrayList<>();
    private final ArrayList<String> categoryList = new ArrayList<>();
    public ExpenseController() {

    }

    public ArrayList<ExpenseModel> getExpenseList() {
        return this.expenseList;
    }

    public ArrayList<String> getCategoryList() {
        return this.categoryList;
    }

    public void addExpense(String name, String Category, double amount, Date date) {
        expenseList.add(new ExpenseModel(name, Category, amount, date));
    }

    public void addCategory(String category) {
        categoryList.add(category);
    }
}
