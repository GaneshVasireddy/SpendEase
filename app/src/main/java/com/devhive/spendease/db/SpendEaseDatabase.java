package com.devhive.spendease.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ExpenseCategory.class, Expense.class}, version = 1, exportSchema = false)
public abstract class SpendEaseDatabase extends RoomDatabase {

    private static volatile SpendEaseDatabase INSTANCE;

    public abstract ExpenseCategoryDao expenseCategoryDao();
    public abstract ExpenseDao expenseDao();

    public static SpendEaseDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SpendEaseDatabase.class) {
                if (INSTANCE ==null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SpendEaseDatabase.class,
                            "spend_ease_db").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
