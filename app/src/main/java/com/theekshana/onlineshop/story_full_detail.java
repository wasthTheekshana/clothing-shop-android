package com.theekshana.onlineshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theekshana.onlineshop.Database.DBHelper;
import com.theekshana.onlineshop.LocationCustomer.HomeDashboard;
import com.theekshana.onlineshop.Model.CartModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class story_full_detail extends AppCompatActivity {

    private String image, productName, productrpice,productID,productSize;
    TextView prName, prprice,qtyview,total,siZeText,ClickDetails,titleName;
    ImageView storyImage;
    ImageButton backbtn,increase,discreas,bookmark;
    Button addtobag;
    private String uid,fcmToken,name,mobile,myaddress;
    DBHelper dbHelper;
    private FirebaseAuth firebaseAuth;
    private double cost = 0;
    private double finalcost = 0;
    private int quantity = 0;
    public List<CartModel> mainProductsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_full_detail);

        //hooks
        storyImage = findViewById(R.id.fullstoryimage);
        titleName = findViewById(R.id.titleName);
        backbtn = findViewById(R.id.backButton);

        bookmark = findViewById(R.id.bookmark);


        ClickDetails = findViewById(R.id.ClickDetails);
        firebaseAuth = FirebaseAuth.getInstance();
        loadMyinfo();
        mainProductsList = new ArrayList<>();
        dbHelper = new DBHelper(this);

        Bundle bundle = getIntent().getExtras();
        image = bundle.getString("image");
        productID = getIntent().getExtras().getString("pid");
        productName = getIntent().getExtras().getString("prName");
        productrpice = getIntent().getExtras().getString("prPrice");
        productSize = getIntent().getExtras().getString("size");
        ClickDetails.setText(productName);
        titleName.setText(productName);
        Glide.with(this).load(image).into(storyImage);


        //back to homedashboard
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backintent = new Intent(story_full_detail.this, HomeDashboard.class);
                startActivity(backintent);
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveWislist();
            }
        });


        //Add to bag

        ClickDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showBottomDetails();
            }
        });


    }

    private void showBottomDetails() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(story_full_detail.this);
        bottomSheetDialog.setContentView(R.layout.product_details_box);
        prName = bottomSheetDialog.findViewById(R.id.productnames);
        prprice = bottomSheetDialog.findViewById(R.id.prdocyPrices);
        siZeText = bottomSheetDialog.findViewById(R.id.siZeText);
        increase = bottomSheetDialog.findViewById(R.id.increaseBtn);
        discreas = bottomSheetDialog.findViewById(R.id.discrasebtn);
        qtyview = bottomSheetDialog.findViewById(R.id.qtyView);
        total = bottomSheetDialog.findViewById(R.id.productTotal);
        addtobag = bottomSheetDialog.findViewById(R.id.addtobag);
        prName.setText(productName);
        prprice.setText("RS "+""+productrpice);
        siZeText.setText(""+productSize);
        bottomSheetDialog.show();
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalcost = finalcost + cost;
                quantity++;
                total.setText("Rs"+finalcost);
                qtyview.setText(""+quantity);
            }
        });

        discreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity >1){
                    finalcost = finalcost - cost;
                    quantity--;
                    total.setText("Rs"+finalcost);
                    qtyview.setText(""+quantity);
                }
            }
        });


        addtobag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToshoppingCartpage();
            }
        });


        cost = Double.parseDouble(productrpice.replaceAll("Rs",""));
        finalcost =Double.parseDouble(productrpice.replaceAll("Rs",""));
        total.setText("Rs"+productrpice);
        quantity =1;


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
    private void SaveWislist() {
        String timestamp = ""+System.currentTimeMillis();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("ProductId",productID);
        hashMap.put("productTitle",productName);
        hashMap.put("originalPrice",productrpice);
        hashMap.put("ProductImage",image);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer").child(uid).child("Wishlist");
        reference.child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                for (int i =0; i<mainProductsList.size(); i++){
                Toast.makeText(story_full_detail.this, "Your wihlist update", Toast.LENGTH_SHORT).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(story_full_detail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private int itemId;
    private void addToshoppingCartpage() {
        String fullTotal = total.getText().toString().trim();
        String fullqty = qtyview.getText().toString().trim();
       Boolean chechdb = dbHelper.insertuserdata(productID,productName,fullTotal,fullqty,image);
       if (chechdb==true){
           Toast.makeText(this, "Add To Bag", Toast.LENGTH_SHORT).show();
       }

    }
}