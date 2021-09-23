package com.theekshana.onlineshop.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theekshana.onlineshop.Model.Customers;
import com.theekshana.onlineshop.R;

import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firebaseAuth = FirebaseAuth.getInstance();


        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("name","name");
        hashMap.put("age","age");
        hashMap.put("address","add");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Test");
        ref.child("123445667788").setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity2.this, "Add datails", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(MainActivity2.this, "Add datails", Toast.LENGTH_SHORT).show();
            }
        });
    }
}