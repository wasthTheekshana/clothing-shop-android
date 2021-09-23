package com.theekshana.onlineshop.LocationCustomer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theekshana.onlineshop.Database.SessionManager;
import com.theekshana.onlineshop.Login.MainActivity;
import com.theekshana.onlineshop.Login.loginuser;
import com.theekshana.onlineshop.Notification.UserNotification;
import com.theekshana.onlineshop.Payments.DeliveryandPick;
import com.theekshana.onlineshop.ProfileUi.AddpayemntCard;
import com.theekshana.onlineshop.ProfileUi.EditAddressBook;
import com.theekshana.onlineshop.ProfileUi.MyProfile;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.Orders.order;

import java.util.HashMap;


public class retail_profile extends Fragment {

    LinearLayout myorderlayer, paymenTmethod, myaddress, signout,information,hlep;
    TextView profileName, profileemail;
    ImageButton notifi;

    private FirebaseAuth firebaseAuth;
    private String uid, name, email, mobile, fcmToken, lname, pw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retail_profile, container, false);
        myorderlayer = view.findViewById(R.id.myOrderlayer);
        paymenTmethod = view.findViewById(R.id.paymentCard);
        notifi = view.findViewById(R.id.notification_btn);
        myaddress = view.findViewById(R.id.address_my);
        profileName = view.findViewById(R.id.profile_name);
        profileemail = view.findViewById(R.id.profile_email);
        signout = view.findViewById(R.id.signOutLayout);
        information = view.findViewById(R.id.myInformation);
        hlep = view.findViewById(R.id.help);


        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();


        SessionManager sessionManager = new SessionManager(getContext());
        HashMap<String, String> userdetails = sessionManager.getUserDetailfromSession();
        String emailsd = userdetails.get(SessionManager.KEY_EMAIL);
        Toast.makeText(getContext(), "useremail" + "" + emailsd, Toast.LENGTH_SHORT).show();
        myorderlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), order.class);
                startActivity(intent);
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });

        hlep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DeliveryandPick.class);
                startActivity(intent);
            }
        });

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyProfile.class);
                startActivity(intent);
            }
        });
//notififcation open
        notifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserNotification.class);
                startActivity(intent);
            }
        });
        myaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditAddressBook.class);
                startActivity(intent);
            }
        });
        paymenTmethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddpayemntCard.class);
                startActivity(intent);
            }
        });
        return view;
    }


    private void checkUser() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            Intent i = new Intent(getContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);

        } else {
            loadMydata();
        }

    }

    private void loadMydata() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    uid = "" + ds.child("uid").getValue();
                    name = "" + ds.child("firstName").getValue();
                    lname = "" + ds.child("lastName").getValue();
                    email = "" + ds.child("email").getValue();
                    pw = "" + ds.child("password").getValue();
                    mobile = "" + ds.child("mobile").getValue();
                    fcmToken = "" + ds.child("fcmToken").getValue();
                    String createDate = "" + ds.child("createDate").getValue();
                    String cardStatus = "" + ds.child("cardStatus").getValue();
                    String isActive = "" + ds.child("isActive").getValue();

                    profileemail.setText(email);
                    profileName.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}