package com.theekshana.onlineshop.Orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theekshana.onlineshop.Adapter.AdapterOderedItem;
import com.theekshana.onlineshop.Model.ModelOderedItem;
import com.theekshana.onlineshop.R;

import java.util.ArrayList;
import java.util.Calendar;

public class OrderDetails extends AppCompatActivity {

    private String oderBy, OderId;
    ImageButton backBtn, editBtn, mapBtn;
    TextView orderIdTv, dateTv, orderStatusTv, orderEmailTv, phoneTv, totalItemsTv, amountTv, addressTv;
    RecyclerView itemRv;
    String lat, lon,tokens;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelOderedItem> orderedItemArrayList;
    private AdapterOderedItem adapterOderedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        backBtn = findViewById(R.id.backBtn);
        orderIdTv = findViewById(R.id.orderIdTv);
        dateTv = findViewById(R.id.dateTv);
        orderStatusTv = findViewById(R.id.orderStatusTv);
        orderEmailTv = findViewById(R.id.orderEmailTv);
        mapBtn = findViewById(R.id.mapBtn);
        phoneTv = findViewById(R.id.phoneTv);
        totalItemsTv = findViewById(R.id.totalItemsTv);
        amountTv = findViewById(R.id.amountTv);
        addressTv = findViewById(R.id.addressTv);
        itemRv = findViewById(R.id.itemRv);

        firebaseAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        OderId = intent.getStringExtra("OderId");
        oderBy = intent.getStringExtra("OderBy");

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(OrderDetails.this,HomeDelivery.class);
                intent1.putExtra("OderId",OderId);
                startActivity(intent1);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadBuyerInfo();
        loadOderDetails();
        loadOrderItems();
    }

    private void loadOderDetails() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref.child(oderBy).child("Orders").child(OderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String oderBy = ""+snapshot.child("oderBy").getValue();
                String orderId = ""+snapshot.child("orderId").getValue();
                String orderCost = ""+snapshot.child("orderCost").getValue();
                String oderAddress = ""+snapshot.child("oderAddress").getValue();
                String deliveryStatus = ""+snapshot.child("deliveryStatus").getValue();
                String orderStatus = ""+snapshot.child("orderStatus").getValue();
                String orderTime = ""+snapshot.child("orderTime").getValue();

//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(Long.parseLong(orderTime));
//                String formatTime = DateFormat.format("dd/MM/yyyy",calendar).toString();

                if (orderStatus.equals("In Progress")){
                    orderStatusTv.setTextColor(getResources().getColor(R.color.blue));
                }else if (orderStatus.equals("Finished")){
                    orderStatusTv.setTextColor(getResources().getColor(R.color.teal_200));
                }else if (orderStatus.equals("Cancelled")){
                    orderStatusTv.setTextColor(getResources().getColor(R.color.red));
                }else if (orderStatus.equals("Confrim")){
                    orderStatusTv.setTextColor(getResources().getColor(R.color.hilightGreen));
                }else if (orderStatus.equals("Packaging")){
                    orderStatusTv.setTextColor(getResources().getColor(R.color.back3));
                }

                if (deliveryStatus.equals("Home Delivery")){
                    mapBtn.setVisibility(View.VISIBLE);
                }else {
                    mapBtn.setVisibility(View.GONE);

                }

                orderIdTv.setText(orderId);
                orderStatusTv.setText(orderStatus);
                amountTv.setText(orderCost);
                addressTv.setText(oderAddress);
                dateTv.setText(orderTime);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void loadBuyerInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref.child(oderBy)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        lat = "" + snapshot.child("latitude").getValue();
                        lon = "" + snapshot.child("longtitude").getValue();
                        tokens = "" + snapshot.child("fcmToken").getValue();
                        String email = "" + snapshot.child("email").getValue();
                        String phone = "" + snapshot.child("mobile").getValue();

                        orderEmailTv.setText(email);
                        phoneTv.setText(phone);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void loadOrderItems(){
        orderedItemArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref.child(oderBy).child("Orders").child(OderId).child("OrderItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderedItemArrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelOderedItem modelOderedItem = ds.getValue(ModelOderedItem.class);
                    orderedItemArrayList.add(modelOderedItem);
                }
                itemRv.setLayoutManager(new LinearLayoutManager(OrderDetails.this));
                adapterOderedItem = new AdapterOderedItem(OrderDetails.this,orderedItemArrayList);
                itemRv.setAdapter(adapterOderedItem);

                totalItemsTv.setText(""+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}