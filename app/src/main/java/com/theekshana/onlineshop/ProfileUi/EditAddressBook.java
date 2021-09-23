package com.theekshana.onlineshop.ProfileUi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
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

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class EditAddressBook extends AppCompatActivity implements LocationListener {


    TextInputLayout Addresname, addresLine1, addressLine2, city, province, distric, postalCode;
    SwitchCompat defualt;
    MaterialButton saveBtn;
    ImageButton backbtn, gpsbtn;
    private FirebaseAuth firebaseAuth;
    private String uid, name, email, mobile, fcmToken, lname, pw, loadprovince, loaddistric, loadCity, loadAddress, loadImage, loadCardstatus, createDate, isActive;
    private String TextAddress, TextCity, TextProvince, TextDistric, TextPostalCode;

    //permission Constants
    private static final int LOCATION_REQUEST_CODE = 100;
    //permission arrays
    private String[] locationPermission;
    private double latitude = 0.00;
    private double longitude = 0.00;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address_book);
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        Addresname = findViewById(R.id.firstName);
        addresLine1 = findViewById(R.id.addresline1);
        addressLine2 = findViewById(R.id.addresline2);
        city = findViewById(R.id.city);
        province = findViewById(R.id.contactNum);
        distric = findViewById(R.id.distric);
       // postalCode = findViewById(R.id.postalCodes);
        defualt = findViewById(R.id.switch1);
        saveBtn = findViewById(R.id.savebutton);
        backbtn = findViewById(R.id.backBtn);
        gpsbtn = findViewById(R.id.gpsBtn);

//        .int permission
        locationPermission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //deteced current location
        gpsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLocationPermission()) {
                    //already allowed
                    detectLocation();
                } else {
                    //not allowed ,request
                    requestLocationPermission();
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddress();
            }
        });
    }

    private void updateAddress() {

        TextAddress = addresLine1.getEditText().getText().toString();
        TextCity = city.getEditText().getText().toString();
        TextProvince = province.getEditText().getText().toString();
        TextDistric = distric.getEditText().getText().toString();
       // TextPostalCode = postalCode.getEditText().getText().toString();

        if (TextUtils.isEmpty(TextAddress)) {
            Toast.makeText(this, "Enter the Address.....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(TextCity)) {
            Toast.makeText(this, "Enter the City.....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(TextProvince)) {
            Toast.makeText(this, "Enter the Province.....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(TextDistric)) {
            Toast.makeText(this, "Enter the Districs.....", Toast.LENGTH_SHORT).show();
            return;
        }


//        if (latitude == 0.00 || longitude == 0.00) {
//            Toast.makeText(this, "Please click the gps button for the detected the Location", Toast.LENGTH_SHORT).show();
//            return;
//        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("province", TextProvince);
        hashMap.put("distric", TextDistric);
        hashMap.put("city", TextCity);
        hashMap.put("address", TextAddress);
        hashMap.put("latitude", latitude);
        hashMap.put("longtitude", longitude);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref.child(firebaseAuth.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditAddressBook.this, "Address Update Successfull", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditAddressBook.this, "Update not Complete", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkUser() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            Intent i = new Intent(EditAddressBook.this, loginuser.class);
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
                    createDate = "" + ds.child("createDate").getValue();
                    loadCardstatus = "" + ds.child("cardStatus").getValue();
                    isActive = "" + ds.child("isActive").getValue();

                    Addresname.getEditText().setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private boolean checkLocationPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, locationPermission, LOCATION_REQUEST_CODE);
    }


    private void detectLocation() {
        Toast.makeText(this, "Please wait......", Toast.LENGTH_SHORT).show();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }




    @Override
    public void onLocationChanged(@NonNull Location location) {
//locvation deteced
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        findAddress();
    }

    private void findAddress() {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude,longitude,1);
            String address = addresses.get(0).getAddressLine(0);//complete address
            String City = addresses.get(0).getLocality();//complete address
            String State = addresses.get(0).getAdminArea();//complete address
            String PostalCode = addresses.get(0).getPostalCode();//complete address
            String Province = addresses.get(0).getSubAdminArea();//complete address


            addresLine1.getEditText().setText(address);
            city.getEditText().setText(City);
            province.getEditText().setText(State);
          //  postalCode.getEditText().setText(PostalCode);
            distric.getEditText().setText(Province);

        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
//gps location disavled
        Toast.makeText(this, "Please turn on Location.....", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean loactionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (loactionAccepted) {
                        //permission allowed
                        detectLocation();
                    } else {
                        //permission denied
                        Toast.makeText(this, "Location permission is necessary", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

}