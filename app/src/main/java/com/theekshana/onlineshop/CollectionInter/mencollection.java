package com.theekshana.onlineshop.CollectionInter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.theekshana.onlineshop.R;

public class mencollection extends AppCompatActivity {
    ImageButton colle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mencollection);

        colle = findViewById(R.id.collect);

        colle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mencollection.this, kidscollection.class);
                startActivity(intent);
            }
        });
    }
}