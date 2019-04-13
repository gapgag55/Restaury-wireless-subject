package com.mang.restaury.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.mang.restaury.Fragments.CartFragment;
import com.mang.restaury.Fragments.FavoriteFragment;
import com.mang.restaury.Fragments.ProfileFragment;
import com.mang.restaury.Fragments.SearchFragment;
import com.mang.restaury.Fragments.searchResultFragment;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;
import com.mang.restaury.Utility.RangeSeekBar;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    Toolbar t1;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    ImageButton filter;
    LinearLayout gv,drawer_left,drawer_right;
    Context con = this;

    Button thai_food_button,intalian_food_button,japanese_food_button,indian_food_button;
    int thai_food,intalian_food,japanese_food,indian_food;

    ImageButton one_star_button,two_star_button,three_star_button,four_star_button,five_star_button;
    int one_star,two_star,three_star,four_star,five_star;

    private ArrayList<Restaurant> restaurants;


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
        setContentView(R.layout.activity_search);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        renderFragment(new searchResultFragment());

        TextView searchTitle = (TextView) findViewById(R.id.search_title);
        searchTitle.setText("Search: " + getIntent().getExtras().getString("keyword"));

        // right drawer
        t1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(t1);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_search);
        drawer_right = (LinearLayout) findViewById(R.id.drawer_right);

        double width = getResources().getDisplayMetrics().widthPixels*0.8;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) drawer_right.getLayoutParams();
        params.width = (int)width;
        drawer_right.setLayoutParams(params);

        filter = (ImageButton) findViewById(R.id.right_filler);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDrawerLayout.isDrawerOpen(drawer_right)){
                    mDrawerLayout.closeDrawer(drawer_right);
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                }else if(!mDrawerLayout.isDrawerOpen(drawer_right)){
                    mDrawerLayout.openDrawer(drawer_right);
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                }
            }
        });

        mDrawerLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int drawerWidth = (int)(getResources().getDisplayMetrics().widthPixels*0.8);
                if (x < drawerWidth) {
                    // inside scrim
                    mDrawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });

        // price range
        final RangeSeekBar rangeSeekbar = (RangeSeekBar) findViewById(R.id.rangeSeekbar1);
        final TextView tvMin = (TextView) findViewById(R.id.textMin1);
        final TextView tvMax = (TextView) findViewById(R.id.textMax1);
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText("฿ "+String.valueOf(minValue));
                tvMax.setText("฿ "+String.valueOf(maxValue));
            }
        });
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });


//        Food type
        thai_food = 0;
        thai_food_button = (Button)findViewById(R.id.thai_food);
        thai_food_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thai_food == 0) {
                    thai_food_button.setBackgroundResource(R.drawable.active_button);
                    thai_food_button.setTextColor(Color.WHITE);
                    thai_food = 1;
                }else{
                    thai_food_button.setBackgroundResource(R.drawable.round_button);
                    thai_food_button.setTextColor(Color.BLACK);
                    thai_food = 0;
                }
            }
        });

        intalian_food = 0;
        intalian_food_button = (Button)findViewById(R.id.intalian_food);
        intalian_food_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intalian_food == 0) {
                    intalian_food_button.setBackgroundResource(R.drawable.active_button);
                    intalian_food_button.setTextColor(Color.WHITE);
                    intalian_food = 1;
                }else{
                    intalian_food_button.setBackgroundResource(R.drawable.round_button);
                    intalian_food_button.setTextColor(Color.BLACK);
                    intalian_food = 0;
                }
            }
        });

        japanese_food = 0;
        japanese_food_button = (Button)findViewById(R.id.japanese_food);
        japanese_food_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(japanese_food == 0) {
                    japanese_food_button.setBackgroundResource(R.drawable.active_button);
                    japanese_food_button.setTextColor(Color.WHITE);
                    japanese_food = 1;
                }else{
                    japanese_food_button.setBackgroundResource(R.drawable.round_button);
                    japanese_food_button.setTextColor(Color.BLACK);
                    japanese_food = 0;
                }
            }
        });

        indian_food = 0;
        indian_food_button = (Button)findViewById(R.id.indian_food);
        indian_food_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indian_food == 0) {
                    indian_food_button.setBackgroundResource(R.drawable.active_button);
                    indian_food_button.setTextColor(Color.WHITE);
                    indian_food = 1;
                }else{
                    indian_food_button.setBackgroundResource(R.drawable.round_button);
                    indian_food_button.setTextColor(Color.BLACK);
                    indian_food = 0;
                }
            }
        });


//        Rating
        one_star = 0;
        one_star_button = findViewById(R.id.one_star);
        one_star_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(one_star == 0) {
                    one_star_button.setImageResource(R.drawable.active_one_star);
                    one_star = 1;
                }else {
                    one_star_button.setImageResource(R.drawable.one_star);
                    one_star = 0;
                }
            }
        });

        two_star = 0;
        two_star_button = findViewById(R.id.two_star);
        two_star_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(two_star == 0) {
                    two_star_button.setImageResource(R.drawable.active_two_star);
                    two_star = 1;
                }else {
                    two_star_button.setImageResource(R.drawable.two_star);
                    two_star = 0;
                }
            }
        });

        three_star = 0;
        three_star_button = findViewById(R.id.three_star);
        three_star_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(three_star == 0) {
                    three_star_button.setImageResource(R.drawable.active_three_star);
                    three_star = 1;
                }else {
                    three_star_button.setImageResource(R.drawable.three_star);
                    three_star = 0;
                }
            }
        });

        four_star = 0;
        four_star_button = findViewById(R.id.four_star);
        four_star_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(four_star == 0) {
                    four_star_button.setImageResource(R.drawable.active_four_star);
                    four_star = 1;
                }else {
                    four_star_button.setImageResource(R.drawable.four_star);
                    four_star = 0;
                }
            }
        });

        five_star = 0;
        five_star_button = findViewById(R.id.five_star);
        five_star_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(five_star == 0) {
                    five_star_button.setImageResource(R.drawable.active_five_star);
                    five_star = 1;
                }else {
                    five_star_button.setImageResource(R.drawable.one_star);
                    five_star = 0;
                }
            }
        });
    }

    private void renderFragment(Fragment selectedFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.wrapper, selectedFragment)
                .commit();
    }
}
