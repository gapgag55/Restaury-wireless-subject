package com.mang.restaury.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Adapter.IngredientAdapter;
import com.mang.restaury.Adapter.VariationAdapter;
import com.mang.restaury.Model.CartItem;
import com.mang.restaury.Model.Customize;
import com.mang.restaury.R;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;

public class CustomizeActivity extends AppCompatActivity {

    private TextView menu;
    private TextView res;

    private VariationAdapter variationAdapter;
    private IngredientAdapter ingredientAdapter;

    private String menuID;
    private String menuName;
    private double menuPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        final Realm realm = Realm.getDefaultInstance();

        // From MenuAdapter.java
        menuID = getIntent().getExtras().getString("menuID");
        menuName = getIntent().getExtras().getString("menuName");
        menuPrice = Double.parseDouble(getIntent().getExtras().getString("menuPrice"));

        System.out.println(menuPrice );

        ((TextView) findViewById(R.id.menu_name)).setText(menuName);
        ((TextView) findViewById(R.id.total_price)).setText("฿" + String.valueOf(menuPrice));


        // Set Close
        ImageButton closeButton = (ImageButton) findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });


        Button removeButton = (Button) findViewById(R.id.remove_button);
        Button addButton = (Button) findViewById(R.id.add_button);
        final TextView itemAmount = (TextView) findViewById(R.id.item_amount);


        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(itemAmount.getText().toString()) - 1;

                if (number > 0) {
                    itemAmount.setText(String.valueOf(number));
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(itemAmount.getText().toString()) + 1;
                itemAmount.setText(String.valueOf(number));
            }
        });



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();


        // Load Size
        final HashMap<String, String> sizeMap = new HashMap<>();
        final Query size = ref.child("Size");
        size.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String sizeID = ds.getKey();
                    String sizeName = ds.child("sizeName").getValue(String.class);

                    sizeMap.put(sizeID, sizeName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


        // Load Ingredient
        final HashMap<String, String> ingredientMap = new HashMap<>();
        final Query ingreident = ref.child("Ingredient");
        ingreident.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String ingreidentID = ds.getKey();
                    String ingredientName = ds.child("ingredientName").getValue(String.class);

                    ingredientMap.put(ingreidentID, ingredientName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });



        // Printout Menu Size
        final Query menuSize = ref.child("MenuSize").orderByChild("menuID").equalTo(menuID);
        menuSize.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Variation paint out
                ListView variationList = (ListView) findViewById(R.id.variation_list);
                variationList.setDivider(null);

                final ArrayList<Customize> variations = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String sizeId = ds.child("sizeID").getValue(String.class);
                    String sizeName = sizeMap.get(sizeId);
                    int sizePrice = ds.child("additionalPrice").getValue(int.class);

                    variations.add(new Customize(sizeId, sizeName, sizePrice));
                }


                variationAdapter = new VariationAdapter(getBaseContext(), CustomizeActivity.this, variations);
                variationList.setAdapter(variationAdapter);
                setListViewHeightBasedOnChildren(variationList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Load ingredient
        final Query ingredientSize = ref.child("MenuIngredient").orderByChild("menuID").equalTo(menuID);
        ingredientSize.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // Variation paint out
                ListView ingredientList = (ListView) findViewById(R.id.ingredient_list);
                ingredientList.setDivider(null);

                final ArrayList<Customize> ingredients = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String ingredientId = ds.child("ingredientID").getValue(String.class);
                    String ingredientName = ingredientMap.get(ingredientId);
                    int ingredientPrice = ds.child("additionalPrice").getValue(int.class);

                    ingredients.add(new Customize(ingredientId, ingredientName, ingredientPrice));
                }


                ingredientAdapter = new IngredientAdapter(getBaseContext(), CustomizeActivity.this, ingredients);
                ingredientList.setAdapter(ingredientAdapter);
                setListViewHeightBasedOnChildren(ingredientList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        // place_order
        Button placeOrderButton = (Button) findViewById(R.id.place_order);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText instruction = (EditText) findViewById(R.id.instruction);
                int totalNumber = Integer.parseInt(itemAmount.getText().toString());

                // variation
                Customize variation = variationAdapter.selectedVariation;
                Customize ingredient = ingredientAdapter.selectedIngredient;

                int variationPrice = 0;
                int ingredientPrice = 0;

                String variationId = "";
                String ingredientId = "";

                if (variation != null) {
                    variationPrice = variation.getPrice();
                    variationId = variation.getId();
                }

                if (ingredient != null) {
                    ingredientPrice = ingredient.getPrice();
                    ingredientId = ingredient.getId();
                }

                // total price
                double totalPrice = (menuPrice + variationPrice + ingredientPrice) * totalNumber;

                // add to cart
                CartItem cartItem = new CartItem(menuID, ingredientId, menuName, variationId, instruction.getText().toString(), totalPrice, totalNumber);

                realm.beginTransaction();
                CartItem realmCartItem = realm.copyToRealm(cartItem);
                realm.commitTransaction();

                closeFragment();
            }
        });



    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public void updatePrice() {

        Customize variation = variationAdapter.selectedVariation;
        Customize ingredient = ingredientAdapter.selectedIngredient;

        int variationPrice = 0;
        int ingredientPrice = 0;

        if (variation != null) variationPrice = variation.getPrice();
        if (ingredient != null) ingredientPrice = ingredient.getPrice();

        double price = menuPrice + variationPrice + ingredientPrice;

        ((TextView) findViewById(R.id.total_price)).setText("฿" + String.valueOf(price));
    }


    @Override
    public void onBackPressed() {
        closeFragment();
    }

    private void closeFragment() {
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_out_down);
    }
}
