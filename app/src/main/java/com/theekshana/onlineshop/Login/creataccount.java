package com.theekshana.onlineshop.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.theekshana.onlineshop.Database.SessionManager;
import com.theekshana.onlineshop.LocationCustomer.HomeDashboard;
import com.theekshana.onlineshop.LocationCustomer.retail_dashboard;
import com.theekshana.onlineshop.Model.Customers;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.utils.appConfig;

import java.util.HashMap;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class creataccount extends AppCompatActivity {

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    BlurView blurView;
    ImageButton backbutton;

    TextInputLayout Fname, Lname, Uemail, Upassword, Utele;
    MaterialButton login;
    String FCMToken = null;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creataccount);
        blurView = findViewById(R.id.blurView);
        backbutton = findViewById(R.id.imageButton2);
        login = findViewById(R.id.loginBtn);

        //textFiled Hooks
        Fname = findViewById(R.id.firstName);
        Lname = findViewById(R.id.lastname);
        Uemail = findViewById(R.id.email);
        Upassword = findViewById(R.id.passworc);
        Utele = findViewById(R.id.contactNum);

        firebaseAuth = FirebaseAuth.getInstance();
        //getFCMToken
        initFCM();
        //go to Back Page(main scree)
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFragment();
            }
        });


        //login button code
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!validateFirstName() | !validateLastName() | !validateEmail() | !validatePassword() | !validatePhoneNum()) {
//                    return;
//                }
                //register user
               // registerFecthData();
                //callVerifyOTPScreen();

              registerUserInfirebase();

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

                        Toast.makeText(creataccount.this, FCMToken, Toast.LENGTH_SHORT).show();
                    }
                });
    }

   private String fNname,lNname ,uEmail ,uPw ,uTele;
    private void registerUserInfirebase() {

         fNname = Fname.getEditText().getText().toString();
         lNname = Lname.getEditText().getText().toString();
         uEmail = Uemail.getEditText().getText().toString();
        uPw = Upassword.getEditText().getText().toString();
        uTele = Utele.getEditText().getText().toString();

//        Customers customers = new Customers(fNname,lNname,uEmail,uPw,null,null,uTele,2,1,1,null,null);
            firebaseAuth.createUserWithEmailAndPassword(uEmail,uPw).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    String timestamp = ""+System.currentTimeMillis();
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("uid", firebaseAuth.getUid());
                    hashMap.put("firstName", fNname);
                    hashMap.put("lastName", lNname);
                    hashMap.put("email", uEmail);
                    hashMap.put("password", uPw);
                    hashMap.put("mobile", uTele);
                    hashMap.put("fcmToken", FCMToken);
                    hashMap.put("createDate", timestamp);
                    hashMap.put("province", "");
                    hashMap.put("distric", "");
                    hashMap.put("city", "");
                    hashMap.put("address", "");
                    hashMap.put("latitude", 0.00);
                    hashMap.put("longtitude", 0.00);
                    hashMap.put("profileImage", "");
                    hashMap.put("cardName","");
                    hashMap.put("cardNumber","");
                    hashMap.put("card_cv","");
                    hashMap.put("date","");
                    hashMap.put("month","");
                    hashMap.put("isActive", 1);

                    //saveto db
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
                    ref.child(firebaseAuth.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(creataccount.this, "Success", Toast.LENGTH_SHORT).show();
                           // registerFecthData();
                            // Goto homeDashBoard
                            Intent i = new Intent(creataccount.this, HomeDashboard.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(i);
                            finish();

//                            Intent i = new Intent(creataccount.this, OTPVerification.class);
//                            i.putExtra("phoneNo",uTele);
//                            startActivity(i);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(creataccount.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

    }

//    private void updateDocId(String docId) {
//        db.collection("Customer").document(docId).update("google_id",docId).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//           Log.d("TAG","Google Id Update");
//            }
//        });
//
//    }

    private void callVerifyOTPScreen() {

        String _getUserEnterdPhoneNumber = Utele.getEditText().getText().toString().trim();
        String _phoneNo = "+94"+_getUserEnterdPhoneNumber;
        Log.d("Inthecreateacount>>>",_phoneNo);

        Intent i = new Intent(creataccount.this, OTPVerification.class);
        i.putExtra("phoneNo",_phoneNo);
        startActivity(i);

    }

    // register api connection
//    private void registerFecthData() {
//        String fNname = Fname.getEditText().getText().toString();
//        String lNname = Lname.getEditText().getText().toString();
//        String uEmail = Uemail.getEditText().getText().toString();
//        String uPw = Upassword.getEditText().getText().toString();
//        String uTele = Utele.getEditText().getText().toString();
//
//        SessionManager sessionManager = new SessionManager(creataccount.this);
//        sessionManager.createuserSession(fNname,lNname,uEmail,uPw,null,FCMToken,uTele,"2","2","1","2");
//
//
//        String url = appConfig.RegisterAuthUrl + fNname + "&Lname=" + lNname + "&Email=" + uEmail + "&password=" + uPw + "&tele=" + uTele+"&token="+FCMToken;
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(creataccount.this, "Success Sever", Toast.LENGTH_SHORT).show();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(creataccount.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(request);
//    }

    private void backFragment() {

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new mainScreenFragment();
        fragmentTransaction.replace(R.id.contanier, fragment, "createaccount");
        fragmentTransaction.commit();
    }


    // fragment get Blur in this method
    private void blurmethod() {
        float radius = 20f;

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


    //Form Validation Start
    private boolean validateFirstName() {
        String val = Fname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Fname.setError("Filed can not be empty");
            return false;
        } else {
            Fname.setError(null);
            Fname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateLastName() {
        String val = Lname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Lname.setError("Filed can not be empty");
            return false;
        } else {
            Lname.setError(null);
            Lname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = Uemail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (val.isEmpty()) {
            Uemail.setError("Filed can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            Uemail.setError("Invalid Email");
            return false;
        } else {
            Uemail.setError(null);
            Uemail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhoneNum() {
        String val = Utele.getEditText().getText().toString().trim();
        String checkNum = "Aw{1,12}z";
        if (val.isEmpty()) {
            Utele.setError("Enter valid phone number");
            return false;
//        } else if (!val.matches(checkNum)) {
//            Utele.setError("No White spaces are allowed");
//            return false;


        } else {
            Utele.setError(null);
            Utele.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = Upassword.getEditText().getText().toString().trim();
        String checkPassword = "^" + "(?=.*[a-zA-Z])" + "(?=S+$)" + ".{4,}" + "$";
        if (val.isEmpty()) {
            Upassword.setError("Filed can not be empty");
            return false;
        }
//        } else if (!val.matches(checkPassword)) {
//            Upassword.setError("Password should contain 4 characters");
//            return false;
//        }

      else {
            Upassword.setError(null);
            Upassword.setErrorEnabled(false);
            return true;
        }
    }
    //Form Validation End

    //friebase connect


}