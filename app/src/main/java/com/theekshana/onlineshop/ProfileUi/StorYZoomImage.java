package com.theekshana.onlineshop.ProfileUi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.theekshana.onlineshop.LocationCustomer.HomeDashboard;
import com.theekshana.onlineshop.Model.Constants;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.story_full_detail;

public class StorYZoomImage extends AppCompatActivity {

    private String image;
    ImageView fullstoryimage;
    ImageButton backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stor_y_zoom_image);
        fullstoryimage = findViewById(R.id.fullstoryimage);
        backbtn = findViewById(R.id.backButton);
        Bundle bundle = getIntent().getExtras();
        image = bundle.getString("image");
        Glide.with(this).load(image).into(fullstoryimage);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backintent = new Intent(StorYZoomImage.this, HomeDashboard.class);
                startActivity(backintent);


            }
        });

    }
}