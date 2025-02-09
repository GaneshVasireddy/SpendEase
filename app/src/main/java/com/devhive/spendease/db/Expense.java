package com.devhive.spendease.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity( tableName = "expenses")
public class Expense {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private double amount;
    private long date;
    private long categoryId;

    public Expense(String title, double amount, long date, long categoryId) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
