package com.theekshana.onlineshop.Orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theekshana.onlineshop.LocationCustomer.HomeDashboard;
import com.theekshana.onlineshop.R;

import java.util.Calendar;

public class HomeDelivery extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private String OderId;
    Button scan_btn;
    TextView inPrograssRound,confrimRound,packRound,finishRound,timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_delivery);
        inPrograssRound = findViewById(R.id.inPrograssRound);
        confrimRound = findViewById(R.id.confrimRound);
        packRound = findViewById(R.id.packRound);
        finishRound = findViewById(R.id.finishRound);
        timer = findViewById(R.id.timer);
        scan_btn = findViewById(R.id.scan_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        OderId = getIntent().getStringExtra("OderId");
        loadOderDetails();


        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeDelivery.this, HomeDashboard.class);
                startActivity(intent);
            }
        });

    }


    private void loadOderDetails() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref.child(firebaseAuth.getUid()).child("Orders").child(OderId).addValueEventListener(new ValueEventListener() {
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
                    inPrograssRound.setBackgroundResource(R.drawable.round_shape);
                }else if (orderStatus.equals("Finished")){
                    finishRound.setBackgroundResource(R.drawable.round_shape);
                    timer.setText("IN Today");
                }else if (orderStatus.equals("Cancelled")){
                    finishRound.setBackgroundResource(R.drawable.round_shape);
                }else if (orderStatus.equals("Confrim")){
                    confrimRound.setBackgroundResource(R.drawable.round_shape);
                }else if (orderStatus.equals("Packaging")){
                    packRound.setBackgroundResource(R.drawable.round_shape);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}