package com.example.expensemate;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// START source: https://www.c-sharpcorner.com/article/bottom-navigation-bar-in-android/ and https://www.geeksforgeeks.org/how-to-save-fragment-states-with-bottomnavigationview-in-android/
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    // DECLARING COMPONENTS
    BottomNavigationView bottomNavigationView;

    // DECLARING VARIABLES
    FragmentManager fragmentManager = getSupportFragmentManager();
    StatisticsFragment statisticsFragment;
    HistoryFragment historyFragment;
    UserProfileFragment userProfileFragment;
    HomePageFragment homePageFragment = new HomePageFragment();
    Fragment active = homePageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager.beginTransaction().add(R.id.frameLayout, homePageFragment).commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_page);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.statistics:
                if (statisticsFragment == null) {
                    statisticsFragment = new StatisticsFragment();
                    fragmentManager.beginTransaction().add(R.id.frameLayout, statisticsFragment).hide(statisticsFragment).commit();
                }
                fragmentManager.beginTransaction().hide(active).show(statisticsFragment).commit();
                active = statisticsFragment;
                return true;
            case R.id.home_page:
                if (homePageFragment == null) {
                    homePageFragment = new HomePageFragment();
                    fragmentManager.beginTransaction().add(R.id.frameLayout, homePageFragment).hide(homePageFragment).commit();
                }
                fragmentManager.beginTransaction().hide(active).show(homePageFragment).commit();
                active = homePageFragment;
                return true;
            case R.id.history:
                if (historyFragment == null) {
                    historyFragment = new HistoryFragment();
                    fragmentManager.beginTransaction().add(R.id.frameLayout, historyFragment).hide(historyFragment).commit();
                }
                fragmentManager.beginTransaction().hide(active).show(historyFragment).commit();
                active = historyFragment;
                return true;
            case R.id.profile:
                if (userProfileFragment == null) {
                    userProfileFragment = new UserProfileFragment();
                    fragmentManager.beginTransaction().add(R.id.frameLayout, userProfileFragment).hide(userProfileFragment).commit();
                }
                fragmentManager.beginTransaction().hide(active).show(userProfileFragment).commit();
                active = userProfileFragment;
                return true;
        }

        return false;
    }


}
// END source: https://www.c-sharpcorner.com/article/bottom-navigation-bar-in-android/ and https://www.geeksforgeeks.org/how-to-save-fragment-states-with-bottomnavigationview-in-android/