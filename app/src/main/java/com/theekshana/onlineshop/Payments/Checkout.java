package com.theekshana.onlineshop.Payments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.theekshana.onlineshop.Adapter.CartAdapte;
import com.theekshana.onlineshop.Database.DBHelper;
import com.theekshana.onlineshop.Database.SessionManager;
import com.theekshana.onlineshop.Map.MapsActivity;
import com.theekshana.onlineshop.Model.CartModel;
import com.theekshana.onlineshop.Model.Constants;
import com.theekshana.onlineshop.Notification.FCMClient;
import com.theekshana.onlineshop.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Checkout extends AppCompatActivity {

    private String orderPrice,deliveryoption;
    TextView cartTotal,backbtn,cusName,cusaddress;
    RecyclerView checkoutRecycle;
    Button checkOutbtn;

   public List<CartModel> mainProductsList;
    DBHelper dbHelper;


    private FirebaseAuth firebaseAuth;
    private String uid,fcmToken,name,mobile;
    private String sessionemail;
    FCMClient myFcmClient;
    private String myaddress;
    private String messemail,subjct, Semail, Spasswrd;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cartTotal = findViewById(R.id.checkoutTotal);
        checkoutRecycle = findViewById(R.id.checkputrecycleView);
        checkOutbtn = findViewById(R.id.placeOrderbtn);
        cusName = findViewById(R.id.cusName);
        cusaddress = findViewById(R.id.cusaddress);
        backbtn = findViewById(R.id.backbtn);
        mainProductsList = new ArrayList<>();
        dbHelper = new DBHelper(this);


        myFcmClient = new FCMClient();
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        HashMap<String,String> userdetails = sessionManager.getUserDetailfromSession();

        sessionemail = userdetails.get(SessionManager.KEY_GOOGLEID);
        firebaseAuth = FirebaseAuth.getInstance();
        loadMyinfo();
        //prograss bar


        orderPrice = getIntent().getExtras().getString("checkoutTotal");
        deliveryoption = getIntent().getExtras().getString("delivery");

        cartTotal.setText("Rs"+orderPrice);

        //get all data
        Cursor cursor = new DBHelper(this).getdata();
        while (cursor.moveToNext()) {

            CartModel cart = new CartModel(cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            mainProductsList.add(cart);
        }

        CartAdapte cartAdapte = new CartAdapte(this, mainProductsList);
        checkoutRecycle.setLayoutManager(new LinearLayoutManager(this));
        checkoutRecycle.setAdapter(cartAdapte);

        checkOutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    submitOrder();

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   onBackPressed();

            }
        });



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
                email = ""+ds.child("email").getValue();
               String pw = ""+ds.child("password").getValue();
                mobile = ""+ds.child("mobile").getValue();
                fcmToken = ""+ds.child("fcmToken").getValue();
                myaddress = ""+ds.child("address").getValue();
               String createDate = ""+ds.child("createDate").getValue();
               String cardNumber = ""+ds.child("cardNumber").getValue();
               String cardStatus = ""+ds.child("cardStatus").getValue();
               String isActive = ""+ds.child("isActive").getValue();
                    if (myaddress.isEmpty() || cardNumber.isEmpty() ) {
                        Toast.makeText(Checkout.this, "Please Fill Your Details", Toast.LENGTH_SHORT).show();
                        checkOutbtn.setVisibility(View.GONE);
                        cusaddress.setText(myaddress);
                        cusName.setText(name);
                    }else {
                        cusaddress.setText(myaddress);
                        cusName.setText(name);
                        checkOutbtn.setEnabled(true);
                    }
                  //  Toast.makeText(Checkout.this, "UIds>>"+""+uid, Toast.LENGTH_SHORT).show();
           }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void submitOrder() {


//        show prograss bar
        Toast.makeText(Checkout.this, "Please Add your address" +" "+ myaddress, Toast.LENGTH_LONG).show();
    String timestamp = ""+System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        String formatTime = DateFormat.format("dd/MM/yyyy",calendar).toString();
    String cost = cartTotal.getText().toString().trim().replace("Rs","");

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("orderId",timestamp);
        hashMap.put("orderTime",formatTime);
        hashMap.put("orderCost",cost);
        hashMap.put("orderStatus","In Progress");
        hashMap.put("deliveryStatus",deliveryoption);
        hashMap.put("oderBy",uid);
        hashMap.put("oderAddress",myaddress);

        messemail = "HI "+name+"Your Order Is Placed. Your Oder Number ORDID :"+timestamp+"Your Order Cost Rs"+cost+"Thank You Join Us .";
        subjct = "Order Details";
        Semail = "harischandrawasathkal@gmail.com";
        Spasswrd = "hari@wasa23#SE65";
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer").child(uid).child("Orders");
        reference.child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                for (int i =0; i<mainProductsList.size(); i++){
                    String pid = mainProductsList.get(i).getId();
                    String name = mainProductsList.get(i).getName();
                    String price = mainProductsList.get(i).getPrice();
                    String qty = mainProductsList.get(i).getQuantity();
                    HashMap<String,String> hashMap1 = new HashMap<>();
                    hashMap1.put("pId",pid);
                    hashMap1.put("name",name);
                    hashMap1.put("orderCost",price);
                    hashMap1.put("qty",qty);
                    reference.child(timestamp).child("OrderItem").child(pid).setValue(hashMap1);

                }

                if (deliveryoption.equals("Drive and Pick")){
                    String msgTitle = "Information your Order";
                    String msgBody = "Congragulation your order has been placed Order id is REFID :"+timestamp;
                    Intent del = new Intent(Checkout.this, DeliveryandPick.class);
                    del.putExtra("OderId",timestamp);
                    startActivity(del);
                    finish();

                }else{

                    Toast.makeText(Checkout.this, "Home Delivery Order Placed Successfull", Toast.LENGTH_SHORT).show();
                    Intent del1 = new Intent(Checkout.this, SuccessfullOrder.class);
                    del1.putExtra("OderId",timestamp);
                    startActivity(del1);
                    finish();
                }
                Toast.makeText(Checkout.this, "Order Placed Successfull"+" "+fcmToken, Toast.LENGTH_SHORT).show();

                //check home dilivery or drive and pick
                myFcmClient.execute(fcmToken,"msgTitle","Order id is REFID :"+timestamp);
                dbHelper.delete();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Checkout.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Semail, Spasswrd);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Semail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));

            message.setSubject(subjct);
            message.setText(messemail);

            new SendMail().execute(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    private class SendMail extends AsyncTask<Message, String, String> {

       // private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(Checkout.this, "Please Wait", "Sending Title", true, false);

        }

        @Override
        protected String doInBackground(Message... messages) {

            try {
                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {


                e.printStackTrace();
                return "Error";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

           // progressDialog.dismiss();
            ;
            if (s.equals("Success")) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(Checkout.this);
//                builder.setCancelable(false);
//                builder.setTitle(Html.fromHtml("<font color='#509324'>Success</font>"));
//                builder.setMessage("Mail send Successfully");
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//
//                    }
//                });
//                builder.show();
            } else {
                Toast.makeText(Checkout.this, "Somthing Wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

}