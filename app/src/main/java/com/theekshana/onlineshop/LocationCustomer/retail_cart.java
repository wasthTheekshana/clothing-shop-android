package com.theekshana.onlineshop.LocationCustomer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.theekshana.onlineshop.Adapter.CartAdapte;
import com.theekshana.onlineshop.Database.DBHelper;
import com.theekshana.onlineshop.Model.CartModel;
import com.theekshana.onlineshop.Payments.Checkout;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.story_full_detail;

import java.util.ArrayList;
import java.util.List;


public class retail_cart extends Fragment {

    RecyclerView cartRecycle;
    RelativeLayout animCart;
    List<CartModel> mainProductsList;
    DBHelper dbHelper;
    Button checkout;
    ImageButton backbtn;
    Spinner deliveryOption;
    public TextView total;
    public double allTotalPrice = 0.00;
    public String price,delivery;
    private double cost;
    private String pid;
    private CartAdapte cartAdapte;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retail_cart, container, false);
        cartRecycle = view.findViewById(R.id.cartRecycleView);
        checkout = view.findViewById(R.id.checkoutBtn);
        backbtn = view.findViewById(R.id.backbtn);
        animCart = view.findViewById(R.id.animCart);
        deliveryOption = view.findViewById(R.id.spinner);

        mainProductsList = new ArrayList<>();
        dbHelper = new DBHelper(getContext());
        total = view.findViewById(R.id.subTotal);

        String[] deliverOption = {"Home Delivery", "Drive and Pick",};
        ArrayAdapter<String> option_adapt = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, deliverOption);
        option_adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deliveryOption.setAdapter(option_adapt);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        }).attachToRecyclerView(cartRecycle);



        //get all data
        Cursor cursor = new DBHelper(getContext()).getdata();
        while (cursor.moveToNext()) {
            CartModel cart = new CartModel(cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            price = cursor.getString(3);
            pid = cursor.getString(1);
            cost = Double.parseDouble(price.replaceAll("Rs", ""));
            allTotalPrice = allTotalPrice + cost;

            mainProductsList.add(cart);
        }

       cartAdapte = new CartAdapte(getContext(), mainProductsList);
        cartRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecycle.setAdapter(cartAdapte);

        if (mainProductsList.size() == 0){
            animCart.setVisibility(View.VISIBLE);
            cartRecycle.setVisibility(View.GONE);
        }else {
            animCart.setVisibility(View.GONE);
            cartRecycle.setVisibility(View.VISIBLE);
        }

        total.setText(String.format("%.2f", allTotalPrice));

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkoutTotal = total.getText().toString();
                delivery = deliveryOption.getSelectedItem().toString();

                if (mainProductsList.size() == 0){
                    Toast.makeText(getContext(), "No item in cart", Toast.LENGTH_SHORT).show();
                    return;
                }
                openCheckoutpage(checkoutTotal,delivery);
            }
        });

//        backbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent checkoutIntent = new Intent(getContext(), story_full_detail.class);
//                startActivity(checkoutIntent);
//            }
//        });


        return view;
    }



    private void openCheckoutpage(String checkoutTotal,String delivery) {

        Intent checkoutIntent = new Intent(getContext(), Checkout.class);
        checkoutIntent.putExtra("checkoutTotal",checkoutTotal);
        checkoutIntent.putExtra("delivery",delivery);
        startActivity(checkoutIntent);
    }
}