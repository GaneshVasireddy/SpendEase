package com.devhive.spendease;

import java.util.Date;

public interface OnAddExpenseDialogListener {
    public void onExpenseAdded(String name, String category, double amount, Date date);
    public void onCategoryAdded(String name);
}
