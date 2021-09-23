package com.theekshana.onlineshop.Payments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.theekshana.onlineshop.Database.DBHelper;
import com.theekshana.onlineshop.LocationCustomer.Capture;
import com.theekshana.onlineshop.R;

public class ScanandPay extends AppCompatActivity {

     Button button2;
    TextView pay_btn;
    private FirebaseAuth firebaseAuth;
    DBHelper dbHelper;
    private String productTitle;
    private String originalPrice;
    private String productId;
    public String contents;
    private String ProductImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanand_pay);
        button2 = findViewById(R.id.scan_btn);
        pay_btn = findViewById(R.id.pay_btn);

        firebaseAuth = FirebaseAuth.getInstance();
        dbHelper = new DBHelper(this);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ScanandPay.this);
                //set prompt text
                intentIntegrator.setBeepEnabled(true);
                //locked ori
                intentIntegrator.setPrompt("Scan a barcode");
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });

        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent pay = new Intent(ScanandPay.this,SacnandpayCheckout.class);
              startActivity(pay);
            }
        });
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product");
//                reference.orderByChild("productId").equalTo(contents).addValueEventListener(new ValueEventListener() {
//
//
//                   @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot ds : snapshot.getChildren()) {
//                            productTitle = "" + ds.child("productTitle").getValue();
//                            String productsubCategory = "" + ds.child("productsubCategory").getValue();
//                            String productMaincategory = "" + ds.child("productMaincategory").getValue();
//                            originalPrice = "" + ds.child("originalPrice").getValue();
//                            String discountPrice = "" + ds.child("discountPrice").getValue();
//                            String ProductImage = "" + ds.child("ProductImage").getValue();
//                            productId = "" + ds.child("productId").getValue();
//
//
//                        }
//                        Boolean chechdb = dbHelper.insertuserdata(productId,productTitle,originalPrice,"1");
//                        if (chechdb==true){
//                            Toast.makeText(ScanandPay.this, productId+""+productTitle+""+originalPrice+" "+contents, Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//                Toast.makeText(ScanandPay.this, "number"+" "+ contents, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        if (intentResult.getContents() != null){
        contents = intentResult.getContents();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product");
            reference.orderByChild("productId").equalTo(contents).addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        productTitle = "" + ds.child("productTitle").getValue();
                        String productsubCategory = "" + ds.child("productsubCategory").getValue();
                        String productMaincategory = "" + ds.child("productMaincategory").getValue();
                        originalPrice = "" + ds.child("originalPrice").getValue();
                        String discountPrice = "" + ds.child("discountPrice").getValue();
                         ProductImage = "" + ds.child("ProductImage").getValue();
                        productId = "" + ds.child("productId").getValue();


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Toast.makeText(ScanandPay.this, "number"+" "+ contents, Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(ScanandPay.this);
            builder.setTitle("Product Id :"+ contents);
            builder.setMessage("Your Product Add to the Bag");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Boolean chechdb = dbHelper.insertuserdata(productId,productTitle,originalPrice,"1",ProductImage);
                    if (chechdb==true){
                        Toast.makeText(ScanandPay.this, productId+""+productTitle+""+originalPrice+" "+contents, Toast.LENGTH_LONG).show();
                    }

                }
            });
            builder.show();

        }else {
            Toast.makeText(ScanandPay.this, "scan Again", Toast.LENGTH_SHORT).show();
        }
    }
}