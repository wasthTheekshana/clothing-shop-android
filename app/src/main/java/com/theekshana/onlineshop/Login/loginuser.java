package com.theekshana.onlineshop.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.theekshana.onlineshop.Database.SessionManager;
import com.theekshana.onlineshop.LocationCustomer.HomeDashboard;
import com.theekshana.onlineshop.Model.Customers;
import com.theekshana.onlineshop.Notification.MyFirebaseInstanceIDService;
import com.theekshana.onlineshop.Payments.SuccessfullOrder;
import com.theekshana.onlineshop.ProfileUi.EditAddressBook;
import com.theekshana.onlineshop.ProfileUi.ForgotPassword;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.utils.appConfig;

import java.util.HashMap;
import java.util.List;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class loginuser extends AppCompatActivity {
    BlurView blurView;
    ImageButton backbutton;
    MaterialButton log;
    TextInputLayout email, password;
    List<Customers> customersList;
    private FirebaseAuth firebaseAuth;
    private String uid;
    private String named;
    private String emails;
    TextView textView5;
    public String getFCMToken() {
        return FCMToken;
    }

    public void setFCMToken(String FCMToken) {
        this.FCMToken = FCMToken;
    }

    String FCMToken = null;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginuser);

        //value init
        blurView = findViewById(R.id.blurView);
        backbutton = findViewById(R.id.imageButton2);
        log = findViewById(R.id.loginBtn);
        email = findViewById(R.id.emailfiled);
        password = findViewById(R.id.passwrdfiled);
        textView5 = findViewById(R.id.textView5);
        initFCM();
        MyFirebaseInstanceIDService service = new MyFirebaseInstanceIDService(loginuser.this);
        service.onTokenRefresh();
        firebaseAuth = FirebaseAuth.getInstance();
   // checkUser();
        //loging Processs
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CusEmail = email.getEditText().getText().toString();
                String CusPassword = password.getEditText().getText().toString();
                // Toast.makeText(loginuser.this, CusEmail+ "" + CusPassword, Toast.LENGTH_SHORT).show();
                Log.d("LENGTH_SHORT", CusEmail);
                // LoginFetchData(CusEmail, CusPassword);
                LoginFirebaseUser(CusEmail, CusPassword);
            }
        });

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginuser.this, ForgotPassword.class);



                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
                finish();
            }
        });
        blurmethod();

    }

    private void initFCM() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        FCMToken = task.getResult();

                        Toast.makeText(loginuser.this, FCMToken, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void checkUser() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            Intent i = new Intent(loginuser.this, creataccount.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
        } else {
            loadMydata();
        }

    }
    private void LoginFirebaseUser(String email, String password) {
//
//        Query query = FirebaseDatabase.getInstance().getReference("Customer").child("uid").equalTo(email);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    String systemPassword = snapshot.child("uid").child("password").getValue(String.class);
//                    if (systemPassword.equals(password)){
//
//                        String fullname = snapshot.child("uid").child("firstName").getValue(String.class);
//                        String email = snapshot.child("uid").child("email").getValue(String.class);
//                        String userid = snapshot.child("uid").child("uid").getValue(String.class);
//
//                    }else {
//                        Toast.makeText(loginuser.this, "Password not match !", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(loginuser.this, "User not exists !", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(loginuser.this, "Success"+authResult.getUser().getEmail(), Toast.LENGTH_SHORT).show();
                loadMydata();
                updateFCMTOken();
                Intent i = new Intent(loginuser.this, HomeDashboard.class);

//                SessionManager sessionManager = new SessionManager(loginuser.this);
//                sessionManager.LoginSession(email);
                startActivity(i);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void updateFCMTOken() {
        HashMap<String,Object> customersHashMap = new HashMap();
        customersHashMap.put("fcmToken",FCMToken);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer").child(firebaseAuth.getUid());
        reference.updateChildren(customersHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(loginuser.this, "Update FCMToKEN", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(loginuser.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loadMydata() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    uid = "" + ds.child("uid").getValue();
                    named = ""+ds.child("firstName").getValue();
                    String lname = ""+ds.child("lastName").getValue();
                    emails = ""+ds.child("email").getValue();
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
                Toast.makeText(loginuser.this, "load data Canceld", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void LoginFetchData(String email, String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String Url = appConfig.LoginAuthUrl + email + "&password=" + password;
        StringRequest request = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String reponce = response.trim();

                if (reponce.equals("success")) {
                    Toast.makeText(loginuser.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(loginuser.this, HomeDashboard.class);



                    i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(loginuser.this, "Erro", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(request);
    }

    private void blurmethod() {
        float radius = 23f;

        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);

        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true);
    }
}

