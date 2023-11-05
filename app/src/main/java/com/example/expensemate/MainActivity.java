package com.example.expensemate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

// START source: https://www.c-sharpcorner.com/article/bottom-navigation-bar-in-android/
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener{

    // DECLARING COMPONENTS
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_page);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.statistics:
                selectedFragment = new StatisticsFragment();
                break;
            case R.id.home_page:
                selectedFragment = new HomePageFragment();
                break;
            case R.id.history:
                selectedFragment = new HistoryFragment();
                break;
            case R.id.profile:
                selectedFragment = new UserProfileFragment();
                break;
        }

        if (selectedFragment != null) {
            loadFragment(selectedFragment);
            return true;
        }

        return false;
    }

    public void loadFragment(Fragment fragment) {
        // To attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }
}
// END source: https://www.c-sharpcorner.com/article/bottom-navigation-bar-in-android/