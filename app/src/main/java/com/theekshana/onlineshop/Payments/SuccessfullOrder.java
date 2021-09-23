package com.theekshana.onlineshop.Payments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.theekshana.onlineshop.LocationCustomer.HomeDashboard;
import com.theekshana.onlineshop.Login.MainActivity;
import com.theekshana.onlineshop.Orders.HomeDelivery;
import com.theekshana.onlineshop.Orders.OrderDetails;
import com.theekshana.onlineshop.R;

public class SuccessfullOrder extends AppCompatActivity {

    Button scan_btn;
    TextView pay_btn;
    private String OderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfull_order);
        scan_btn = findViewById(R.id.scan_btn);
        pay_btn = findViewById(R.id.pay_btn);
        OderId = getIntent().getStringExtra("OderId");

        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SuccessfullOrder.this, HomeDelivery.class);
                intent1.putExtra("OderId",OderId);
                startActivity(intent1);
            }
        });

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SuccessfullOrder.this, HomeDashboard.class);
                startActivity(intent1);
            }
        });
    }
}