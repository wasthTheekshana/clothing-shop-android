package com.theekshana.onlineshop.LocationCustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.theekshana.onlineshop.Database.DBHelper;
import com.theekshana.onlineshop.Database.SessionManager;
import com.theekshana.onlineshop.Login.loginuser;
import com.theekshana.onlineshop.Payments.Checkout;
import com.theekshana.onlineshop.R;

public class HomeDashboard extends AppCompatActivity {

   public ChipNavigationBar chipNavigationBar;
    private FirebaseAuth firebaseAuth;
    private String uid,name,email;
    TextView namedss;
    DBHelper dbHelper;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_dashboard);
        dbHelper = new DBHelper(this);
         count = new DBHelper(this).getdata().getCount();
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_dashboard, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new retail_dashboard()).commit();
        bottomMenu();

    }


    private void checkUser() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null){
            Intent i = new Intent(getApplicationContext(), loginuser.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
        }else {
            loadMydata();
        }

    }

    private void loadMydata() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                   uid = "" + ds.child("uid").getValue();
                     name = ""+ds.child("firstName").getValue();
                    String lname = ""+ds.child("lastName").getValue();
                     email = ""+ds.child("email").getValue();
                    String pw = ""+ds.child("password").getValue();
                    String mobile = ""+ds.child("mobile").getValue();
                    String fcmToken = ""+ds.child("fcmToken").getValue();
                    String createDate = ""+ds.child("createDate").getValue();
                    String cardStatus = ""+ds.child("cardStatus").getValue();
                    String isActive = ""+ds.child("isActive").getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void bottomMenu() {
        chipNavigationBar.showBadge(R.id.bottom_nav_cart,count);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.bottom_nav_dashboard:
                        fragment = new retail_dashboard();
                        break;
                    case R.id.bottom_nav_manage:
                        fragment = new retail_category();
                        break;
                    case R.id.bottom_nav_wishlist:
                        fragment = new retail_wishlist();
                        break;
                    case R.id.bottom_nav_profile:
                        fragment = new retail_profile();
                        break;
                        case R.id.bottom_nav_cart:
                        fragment = new retail_cart();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }
}