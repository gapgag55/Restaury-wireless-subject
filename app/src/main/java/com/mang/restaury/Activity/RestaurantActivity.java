package com.mang.restaury.Activity;

import android.app.Person;
import android.content.res.Resources;
import android.graphics.Color;
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
import com.mang.restaury.Model.Favorite;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmResults;

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
    private String resName;
    private int resDeliverFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        // Intent from RestaurantAdapter.java

        final String restaurant_name = getIntent().getExtras().getString("restaurant_name");
        latitute = getIntent().getExtras().getDouble("latitute");
        longitute = getIntent().getExtras().getDouble("longitute");
        picture = getIntent().getExtras().getString("picture");
        about = getIntent().getExtras().getString("about");
        resID = getIntent().getExtras().getInt("res_id");
        resName = getIntent().getExtras().getString("res_name");
        resDeliverFee =  getIntent().getExtras().getInt("res_deliverFee");


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

        // onSave

        final Realm realm = Realm.getDefaultInstance();

        final ImageButton saveIcon = (ImageButton) findViewById(R.id.save_button);
        RealmResults<Restaurant> restaurants = realm.where(Restaurant.class).equalTo("restaurant_ID", resID).findAll();

        if (restaurants.size() > 0) {
            saveIcon.setBackgroundResource(R.drawable.ic_save_active);
        }

        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm.beginTransaction();

                RealmResults<Restaurant> restaurants = realm.where(Restaurant.class).equalTo("restaurant_ID", resID).findAll();

                if (restaurants.size() > 0) {

                    // remove
                    restaurants.deleteAllFromRealm();
                    saveIcon.setBackgroundResource(R.drawable.ic_save);

                } else {

                    // add
                    Restaurant restaurant = new Restaurant(resName, latitute, longitute,  resID, about, resDeliverFee, picture);

                    final Restaurant managedRestaurant = realm.copyToRealm(restaurant);
                    Favorite favorite = realm.createObject(Favorite.class);
                    favorite.getRestaurants().add(managedRestaurant);
                    saveIcon.setBackgroundResource(R.drawable.ic_save_active);

                }

                realm.commitTransaction();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ResturantTabAdapter adapter = new ResturantTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new AboutFragment(resID, resName, latitute, longitute, about), "About");
        adapter.addFragment(new MenusFragment(resID), "Menus");
        adapter.addFragment(new ReviewsFragment(resID), "Reviews");
        viewPager.setAdapter(adapter);
    }
}
