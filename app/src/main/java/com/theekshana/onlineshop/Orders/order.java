package com.theekshana.onlineshop.Orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theekshana.onlineshop.Adapter.AdapterOrder;
import com.theekshana.onlineshop.Model.ModelOrderUser;
import com.theekshana.onlineshop.R;

import java.util.ArrayList;

public class order extends AppCompatActivity {
    RecyclerView oderRv;
    ArrayList<ModelOrderUser> orderList;
    AdapterOrder adapterOrder;
    EditText searchorderEt;
    ImageButton filterorderBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        oderRv = findViewById(R.id.orderRv);
        searchorderEt = findViewById(R.id.searchorderEt);
        filterorderBtn = findViewById(R.id.filterorderBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        loadOders();

        filterorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] options = {"All","In Progress","Confrim","Packaging","Finished","Cancelled","Drive and Pick","Home Delivery"};
                AlertDialog.Builder builder =  new AlertDialog.Builder(order.this);
                builder.setTitle("Fillter Order").setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            adapterOrder.getFilter().filter("");
                        }else {
                            String optionClicked = options[which];
                            adapterOrder.getFilter().filter(optionClicked);
                        }
                    }
                }).show();
            }
        });
    }

    private void loadOders() {
        orderList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer").child(firebaseAuth.getUid()).child("Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    ModelOrderUser modelOrderUser = ds.getValue(ModelOrderUser.class);
                    orderList.add(modelOrderUser);


                }
                oderRv.setLayoutManager(new LinearLayoutManager(order.this));
                adapterOrder = new AdapterOrder(order.this,orderList);
                oderRv.setAdapter(adapterOrder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}