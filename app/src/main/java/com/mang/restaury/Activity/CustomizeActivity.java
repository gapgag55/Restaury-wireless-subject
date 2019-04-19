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
import com.mang.restaury.Adapter.VariationAdapter;
import com.mang.restaury.Model.Cart;
import com.mang.restaury.Model.CartItem;
import com.mang.restaury.Model.Customize;
import com.mang.restaury.Model.Favorite;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;

public class CustomizeActivity extends AppCompatActivity {

    private TextView menu;
    private TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        final Realm realm = Realm.getDefaultInstance();

        // From MenuAdapter.java
        final String menuID = getIntent().getExtras().getString("menuID");
        final String menuName = getIntent().getExtras().getString("menuName");
        final String menuPrice = getIntent().getExtras().getString("menuPrice");

        ((TextView) findViewById(R.id.menu_name)).setText(menuName);
        ((TextView) findViewById(R.id.total_price)).setText("฿" + menuPrice);


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


        final CustomizeActivity context = this;

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


                final VariationAdapter variationAdapter = new VariationAdapter(context, variations);
                variationList.setAdapter(variationAdapter);
                setListViewHeightBasedOnChildren(variationList);


                // place_order
                Button placeOrderButton = (Button) findViewById(R.id.place_order);
                placeOrderButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText instruction = (EditText) findViewById(R.id.instruction);
                        int totalNumber = Integer.parseInt(itemAmount.getText().toString());

                        // variation
                        Customize variation = variationAdapter.selectedVariation;
                        int variationPrice = variation.getPrice();
                        String variationSize = variation.getSizeId();

                        // total price
                        int basePrice = (int) Double.parseDouble(menuPrice);
                        int totalPrice = (basePrice + variationPrice) * totalNumber;

                        // add to cart
                        CartItem cartItem = new CartItem(menuID, menuName, variationSize, instruction.getText().toString(), totalPrice, totalNumber);

                        realm.beginTransaction();

                        final CartItem managedCartItem = realm.copyToRealm(cartItem);
                        Cart cart = realm.createObject(Cart.class);
                        cart.getCartItems().add(managedCartItem);

                        realm.commitTransaction();

                        closeFragment();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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


    @Override
    public void onBackPressed() {
        closeFragment();
    }

    private void closeFragment() {
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_out_down);
    }
}
