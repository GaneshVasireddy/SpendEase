package com.devhive.spendease;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnAddExpenseDialogListener, NavigationView.OnNavigationItemSelectedListener {

    ExpenseController expenseController;
    ExpenseAdapter adapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.expense_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        expenseController = new ExpenseController();
        ArrayList<ExpenseModel> expenseList = expenseController.getExpenseList();

        adapter = new ExpenseAdapter(expenseList);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.add_expense_btn).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        findViewById(R.id.hamburger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isOpen()) {
                    drawerLayout.close();
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_expense_btn) {
            AddExpenseDialog dialog = new AddExpenseDialog(this, expenseController, this, false);
        }
    }

    @Override
    public void onExpenseAdded(String name, String category, double amount, Date date) {
        expenseController.addExpense(name, category, amount, date);
        adapter.notifyItemInserted(adapter.getItemCount());
    }

    @Override
    public void onCategoryAdded(String category) {
        expenseController.addCategory(category);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_category_menu_item) {
            AddExpenseDialog dialog = new AddExpenseDialog(this, expenseController, this, true);
        }
        return false;
    }
}