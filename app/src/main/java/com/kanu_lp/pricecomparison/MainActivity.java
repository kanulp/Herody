package com.kanu_lp.pricecomparison;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kanu_lp.support.Home_Fragment;
import com.kanu_lp.support.Profile_Fragment;
import com.kanu_lp.support.Settings_Fragment;
import com.kanu_lp.support.R;

public class MainActivity extends AppCompatActivity {



    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentManager = getSupportFragmentManager();
        fragment = new com.kanu_lp.support.Home_Fragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container, fragment).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case com.kanu_lp.support.R.id.navigation_home:
                                fragment = new Home_Fragment();
                                break;
                            case R.id.navigation_dashboard:
                                fragment = new Profile_Fragment();
                                break;
                            case R.id.navigation_notifications:
                                fragment = new Settings_Fragment();
                                break;
                        }
                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.main_container, fragment).commit();
                        return true;
                    }
                });


    }

}
