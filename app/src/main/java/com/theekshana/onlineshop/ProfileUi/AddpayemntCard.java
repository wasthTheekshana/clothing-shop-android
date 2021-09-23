package com.theekshana.onlineshop.ProfileUi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theekshana.onlineshop.Database.SessionManager;
import com.theekshana.onlineshop.Login.loginuser;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.utils.appConfig;

import java.util.HashMap;

public class AddpayemntCard extends AppCompatActivity {

    TextInputLayout namecard, numberCard,sencNumbercard,date,month;
    Button save_btn;
    ImageButton backBtn;
    private String UserName;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpayemnt_card);
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        namecard = findViewById(R.id.mycardName);
        numberCard = findViewById(R.id.mycardnum);
        backBtn = findViewById(R.id.backBtn);
        sencNumbercard = findViewById(R.id.mycarcvv);
        date = findViewById(R.id.mycarddate);
        month = findViewById(R.id.mycardmonth);

        save_btn =findViewById(R.id.cardSaveBtn);


        //get login User name
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userName = sessionManager.getUserDetailfromSession();
         UserName = userName.get(SessionManager.KEY_EMAIL);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveUserCard();
                UserCardSave();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveUserCard();
                onBackPressed();
            }
        });

    }

    private void UserCardSave() {
        String Namecard = namecard.getEditText().getText().toString();
        String Numbercard = numberCard.getEditText().getText().toString();
        String SecNumbercardnumber = sencNumbercard.getEditText().getText().toString();
        String dateCard = date.getEditText().getText().toString();
        String monthcard = month.getEditText().getText().toString();

        HashMap<String,Object> hashMap = new HashMap<>();
       // hashMap.put("userName",UserName);
        hashMap.put("cardName",Namecard);
        hashMap.put("cardNumber",Numbercard);
        hashMap.put("card_cv",SecNumbercardnumber);
        hashMap.put("date",dateCard);
        hashMap.put("month",monthcard);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref.child(firebaseAuth.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddpayemntCard.this, "Card Detail saved succefully", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddpayemntCard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUser() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null){
            Intent i = new Intent(AddpayemntCard.this, loginuser.class);
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

                    UserName = ""+ds.child("firstName").getValue();
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

    private void saveUserCard() {

        String Namecard = namecard.getEditText().getText().toString();
        String Numbercard = numberCard.getEditText().getText().toString();
        String SecNumbercardnumber = sencNumbercard.getEditText().getText().toString();
        String dateCard = date.getEditText().getText().toString();
        String monthcard = month.getEditText().getText().toString();

        String cardUrl = appConfig.saveUserCardUrl+UserName+"&=cardName"+Namecard+"&cardNumber="+Numbercard+"&cardCvv="+SecNumbercardnumber+"&cardDate="+dateCard+"&cardMonth="+monthcard;
        StringRequest request = new StringRequest(Request.Method.POST, cardUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddpayemntCard.this, "Card Save Succecfull", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddpayemntCard.this, "Card Error"+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}