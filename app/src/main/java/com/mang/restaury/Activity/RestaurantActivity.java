package com.mang.restaury.Activity;

import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mang.restaury.Adapter.ResturantTabAdapter;
import com.mang.restaury.Fragments.AboutFragment;
import com.mang.restaury.Fragments.MenusFragment;
import com.mang.restaury.Fragments.ReviewsFragment;
import com.mang.restaury.R;
import com.squareup.picasso.Picasso;

public class RestaurantActivity extends AppCompatActivity {

    private TextView toolbar_title;

    private static final String TAG = "MainActivity";

    private ResturantTabAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private ImageView mResPic;

    private double latitute;
    private double longitute;
    private String picture;
    private String about;
    private int resID;
    private String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        String restaurant_name = getIntent().getExtras().getString("restaurant_name");
        latitute = getIntent().getExtras().getDouble("latitute");
        longitute = getIntent().getExtras().getDouble("longitute");
        picture = getIntent().getExtras().getString("picture");
        about = getIntent().getExtras().getString("about");
        resID = getIntent().getExtras().getInt("res_id");
        restaurantName = getIntent().getExtras().getString("res_name");


        // set image of the restaurant
        mResPic = (ImageView) findViewById(R.id.restaurant_image) ;
        Picasso.get().load(picture).into(mResPic);


        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(restaurant_name);

        mSectionsPageAdapter = new ResturantTabAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        // onBack
        ImageButton backIcon = (ImageButton) findViewById(R.id.back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ResturantTabAdapter adapter = new ResturantTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new AboutFragment(resID, restaurantName, latitute, longitute, about), "About");
        adapter.addFragment(new MenusFragment(resID), "Menus");
        adapter.addFragment(new ReviewsFragment(), "Reviews");
        viewPager.setAdapter(adapter);
    }
}
