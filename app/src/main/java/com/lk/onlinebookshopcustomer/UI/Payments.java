package com.lk.onlinebookshopcustomer.UI;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.lk.onlinebookshopcustomer.Fragment.PaymentMethod;
import com.lk.onlinebookshopcustomer.R;

public class Payments extends NormalOptionMenu {

    DrawerLayout drawer;
    public ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.payment_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chech Out");

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);


        PaymentMethod payMethod = new PaymentMethod();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        manager.beginTransaction().add(R.id.payment_container, payMethod).commit();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.payment_container,
                    new PaymentMethod()).commit();

        }
    }
}