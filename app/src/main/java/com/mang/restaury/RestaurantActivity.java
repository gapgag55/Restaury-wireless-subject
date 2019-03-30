package com.mang.restaury;

import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mang.restaury.Adapter.ResturantTabAdapter;
import com.mang.restaury.Fragments.AboutFragment;
import com.mang.restaury.Fragments.MenusFragment;
import com.mang.restaury.Fragments.ReviewsFragment;
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
    private int res_id;
    private String res_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        String restaurant_name = getIntent().getExtras().getString("restaurant_name");
        latitute = getIntent().getExtras().getDouble("latitute");
        longitute = getIntent().getExtras().getDouble("longitute");
        picture = getIntent().getExtras().getString("picture");
        about = getIntent().getExtras().getString("about");
        res_id = getIntent().getExtras().getInt("res_id");
        res_name = getIntent().getExtras().getString("res_name");


        // set image of the restaurant
        mResPic = (ImageView) findViewById(R.id.restaurant_image) ;
        Picasso.get().load(picture).into(mResPic);

        // set about of the restaurant



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
        adapter.addFragment(new AboutFragment(latitute,longitute,about), "About");
        adapter.addFragment(new MenusFragment(res_id,res_name), "Menus");
        adapter.addFragment(new ReviewsFragment(), "Reviews");
        viewPager.setAdapter(adapter);
    }
}
