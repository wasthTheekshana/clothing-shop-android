package com.theekshana.onlineshop.Payments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theekshana.onlineshop.Adapter.CartAdapte;
import com.theekshana.onlineshop.Database.DBHelper;
import com.theekshana.onlineshop.Model.CartModel;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.story_full_detail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SacnandpayCheckout extends AppCompatActivity {
    RecyclerView cartRecycle;
    List<CartModel> mainProductsList;
    DBHelper dbHelper;
    Button checkout;
    ImageButton backbtn;

    public TextView total;
    public double allTotalPrice = 0.00;
    public String price,delivery;
    private double cost;
    private String pid;
    private String uid,fcmToken,name,mobile,myaddress;
    private CartAdapte cartAdapte;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sacnandpay_checkout);

        cartRecycle = findViewById(R.id.cartRecycleView);
        checkout = findViewById(R.id.checkoutBtn);
        backbtn = findViewById(R.id.backbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        loadMyinfo();

        mainProductsList = new ArrayList<>();
        dbHelper = new DBHelper(SacnandpayCheckout.this);
        total = findViewById(R.id.subTotal);

        Cursor cursor = new DBHelper(SacnandpayCheckout.this).getdata();
        while (cursor.moveToNext()) {
            CartModel cart = new CartModel(cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            price = cursor.getString(3);
            pid = cursor.getString(1);
            cost = Double.parseDouble(price.replaceAll("Rs", ""));
            allTotalPrice = allTotalPrice + cost;

            mainProductsList.add(cart);
        }

        cartAdapte = new CartAdapte(SacnandpayCheckout.this, mainProductsList);
        cartRecycle.setLayoutManager(new LinearLayoutManager(SacnandpayCheckout.this));
        cartRecycle.setAdapter(cartAdapte);

        total.setText(String.format("%.2f", allTotalPrice));

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkoutTotal = total.getText().toString();


                if (mainProductsList.size() == 0){
                    Toast.makeText(SacnandpayCheckout.this, "No item in cart", Toast.LENGTH_SHORT).show();
                    return;
                }
                String timestamp = ""+System.currentTimeMillis();
                String cost = total.getText().toString().trim().replace("Rs","");

                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("orderId",timestamp);
                hashMap.put("orderTime",timestamp);
                hashMap.put("orderCost",cost);
                hashMap.put("orderStatus","In Progress");
                hashMap.put("deliveryStatus","Scan and Pay");
                hashMap.put("oderBy",uid);
                hashMap.put("oderAddress","");

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer").child(uid).child("Orders");
                reference.child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        for (int i =0; i<mainProductsList.size(); i++){
                            String pid = mainProductsList.get(i).getId();
                            String name = mainProductsList.get(i).getName();
                            String price = mainProductsList.get(i).getPrice();
                            String qty = mainProductsList.get(i).getQuantity();
                            HashMap<String,String> hashMap1 = new HashMap<>();
                            hashMap1.put("pId",pid);
                            hashMap1.put("name",name);
                            hashMap1.put("orderCost",price);
                            hashMap1.put("qty",qty);
                            reference.child(timestamp).child("OrderItem").child(pid).setValue(hashMap1);

                        }

                        Toast.makeText(SacnandpayCheckout.this, "Order Placed Successfull"+" "+fcmToken, Toast.LENGTH_SHORT).show();
                        Intent scan = new Intent(SacnandpayCheckout.this,sacnSucessfullPayemnt.class);
                        scan.putExtra("orderID",timestamp);
                        startActivity(scan);
                        dbHelper.delete();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SacnandpayCheckout.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void loadMyinfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    uid = ""+ds.child("uid").getValue();
                    name = ""+ds.child("firstName").getValue();
                    String lname = ""+ds.child("lastName").getValue();
                    String email = ""+ds.child("email").getValue();
                    String pw = ""+ds.child("password").getValue();
                    mobile = ""+ds.child("mobile").getValue();
                    fcmToken = ""+ds.child("fcmToken").getValue();
                    myaddress = ""+ds.child("address").getValue();
                    String createDate = ""+ds.child("createDate").getValue();
                    String cardNumber = ""+ds.child("cardNumber").getValue();
                    String cardStatus = ""+ds.child("cardStatus").getValue();
                    String isActive = ""+ds.child("isActive").getValue();
                    //  Toast.makeText(Checkout.this, "UIds>>"+""+uid, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}