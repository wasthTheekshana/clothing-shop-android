package com.theekshana.onlineshop.ProfileUi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theekshana.onlineshop.Login.loginuser;
import com.theekshana.onlineshop.R;

import java.util.HashMap;

public class MyProfile extends AppCompatActivity {

    TextView Headline;
    TextInputLayout Personname,PersonlastName,Personemail,PersonMobile;
    MaterialButton saveBtn;
  //  ImageView profileImage;
    private FirebaseAuth firebaseAuth;
    StorageReference storageRef;

    private int CODE_DRIVER =1001;
    private Uri image_uri;
    private String uid, name, email, mobile, fcmToken, lname, pw,loadprovince,loaddistric,loadCity,loadAddress,loadImage,loadCardstatus,createDate,isActive;
    private String setUName,setULast,setUEmail,setUmobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        storageRef= FirebaseStorage.getInstance().getReference();
        checkUser();
        Headline = findViewById(R.id.healPersonNanme);
       // profileImage = findViewById(R.id.profileimage);
        Personname = findViewById(R.id.firstName);
        PersonlastName = findViewById(R.id.lasrNameProfile);
        Personemail = findViewById(R.id.emailPerson);
        PersonMobile = findViewById(R.id.mobilePerson);
        saveBtn = findViewById(R.id.savebutton);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputeData();
            }
        });

//        profileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent filec=new Intent();
//                filec.setAction(Intent.ACTION_GET_CONTENT);
//                filec.setType("image/*");
//                startActivityForResult(Intent.createChooser(filec,"Select Driver Photo..."),CODE_DRIVER);
//
//            }
//        });

    }

    private void inputeData() {
        setUName = Personname.getEditText().getText().toString();
        setULast = PersonlastName.getEditText().getText().toString();
        setUEmail = Personemail.getEditText().getText().toString();
        setUmobile = PersonMobile.getEditText().getText().toString();
        updateProfile();
    }

    private void updateProfile() {
        String timestamp = ""+System.currentTimeMillis();
        if (image_uri == null) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("firstName", setUName);
            hashMap.put("lastName", setULast);
            hashMap.put("email", setUEmail);
            hashMap.put("mobile", setUmobile);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
            ref.child(firebaseAuth.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MyProfile.this, "Your Profile Has Update", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MyProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            String CustomerImageurl = "CustomerImage/"+""+firebaseAuth.getUid();
            storageRef = FirebaseStorage.getInstance().getReference(CustomerImageurl);
            storageRef.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    Uri downloadImageUri = uriTask.getResult();
                    if (uriTask.isSuccessful()){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("firstName", setUName);
                        hashMap.put("lastName", setULast);
                        hashMap.put("email", setUEmail);
                        hashMap.put("mobile", setUmobile);
                        hashMap.put("profileImage", downloadImageUri);
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
                        ref.child(firebaseAuth.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MyProfile.this, "Your Profile Has Update", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MyProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MyProfile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void checkUser() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            Intent i = new Intent(MyProfile.this, loginuser.class);
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

                    Headline.setText(name);
                    Personname.getEditText().setText(name);
                    PersonlastName.getEditText().setText(lname);
                    Personemail.getEditText().setText(email);
                    PersonMobile.getEditText().setText(mobile);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode==CODE_DRIVER){
//
//            if(resultCode==RESULT_OK){
//
//                image_uri=data.getData();
//                Picasso.with(MyProfile.this).load(image_uri).into(profileImage);
//
//            }
//        }
    }
}