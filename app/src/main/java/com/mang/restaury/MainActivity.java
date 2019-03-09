package com.mang.restaury;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mang.restaury.Fragments.CartFragment;
import com.mang.restaury.Fragments.FavoriteFragment;
import com.mang.restaury.Fragments.ProfileFragment;
import com.mang.restaury.Fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment selectedFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.search_page:
                    selectedFragment = new SearchFragment();
                    break;

                case R.id.favorite_page:
                    selectedFragment = new FavoriteFragment();
                    break;

                case R.id.cart_page:
                    selectedFragment = new CartFragment();
                    break;

                case R.id.profile_page:
                    selectedFragment = new ProfileFragment();
                    break;
                default:
                    selectedFragment = new SearchFragment();
                    break;
            }

            renderFragment(selectedFragment);
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        selectedFragment = new SearchFragment();
        renderFragment(selectedFragment);
    }

    private void renderFragment(Fragment selectedFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.wrapper, selectedFragment)
                .commit();
    }

}
