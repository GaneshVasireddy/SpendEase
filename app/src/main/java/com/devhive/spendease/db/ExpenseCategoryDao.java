package com.devhive.spendease.db;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExpenseCategoryDao {

    @Insert
    long insert(ExpenseCategory category);

    @Query("SELECT * FROM expense_category ORDER BY name ASC")
    LiveData<List<ExpenseCategory>> getAllCategories();


    @Query("SELECT name FROM expense_category WHERE id = :id")
    LiveData<String> getCategoryNameById(long id);

    @Query("SELECT id FROM expense_category WHERE name = :name")
    @Nullable
    LiveData<Integer> getCategoryIdByName(String name);
}
