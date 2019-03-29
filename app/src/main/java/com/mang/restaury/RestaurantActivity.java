package com.mang.restaury;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mang.restaury.Adapter.ResturantTabAdapter;
import com.mang.restaury.Fragments.AboutFragment;
import com.mang.restaury.Fragments.MenusFragment;
import com.mang.restaury.Fragments.ReviewsFragment;

public class RestaurantActivity extends AppCompatActivity {

    private TextView toolbar_title;

    private static final String TAG = "MainActivity";

    private ResturantTabAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    private Float latitute;

    private Float longitute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        String restaurant_name = getIntent().getExtras().getString("restaurant_name");
        latitute = getIntent().getExtras().getFloat("lat");
        longitute = getIntent().getExtras().getFloat("long");


        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(restaurant_name);

        mSectionsPageAdapter = new ResturantTabAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ResturantTabAdapter adapter = new ResturantTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new AboutFragment(latitute,longitute), "About");
        adapter.addFragment(new MenusFragment(), "Menus");
        adapter.addFragment(new ReviewsFragment(), "Reviews");
        viewPager.setAdapter(adapter);
    }
}
