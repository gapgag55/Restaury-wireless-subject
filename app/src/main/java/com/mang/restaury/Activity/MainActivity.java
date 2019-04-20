package com.mang.restaury.Activity;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.mang.restaury.Fragments.AuthenticationFragment;
import com.mang.restaury.Fragments.CartFragment;
import com.mang.restaury.Fragments.FavoriteFragment;
import com.mang.restaury.Fragments.ProfileFragment;
import com.mang.restaury.Fragments.SearchFragment;
import com.mang.restaury.R;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Fragment selectedFragment;
    private String fragmentTag;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            final AuthenticationFragment auth = AuthenticationFragment.getInstance();

            switch (item.getItemId()) {
                case R.id.search_page:
                    selectedFragment = new SearchFragment();
                    fragmentTag = "search_fragment";
                    break;

                case R.id.favorite_page:
                    selectedFragment = new FavoriteFragment();
                    fragmentTag = "favorite_fragment";
                    break;

                case R.id.cart_page:
                    if (auth.getCurrentUser() == null) {
                        auth.show(getSupportFragmentManager(), "Authentication");

                        return true;
                    }
                    selectedFragment = new CartFragment();
                    fragmentTag = "cart_fragment";
                    break;

                case R.id.profile_page:
                    if (auth.getCurrentUser() == null) {
                        auth.show(getSupportFragmentManager(), "Authentication");

                        return true;
                    }

                    selectedFragment = new ProfileFragment();
                    fragmentTag = "profile_fragment";
                    break;

                default:
                    selectedFragment = new SearchFragment();
                    fragmentTag = "search_fragment";
                    break;
            }

            renderFragment(selectedFragment, fragmentTag);
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Realm.init(this);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        selectedFragment = new SearchFragment();
        fragmentTag = "search_fragment";
        renderFragment(selectedFragment, fragmentTag);
    }

    private void renderFragment(Fragment selectedFragment, String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.wrapper, selectedFragment, fragmentTag)
                .commit();
    }

}
