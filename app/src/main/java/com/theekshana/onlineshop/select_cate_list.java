package com.theekshana.onlineshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theekshana.onlineshop.Adapter.MainProductAdapter;
import com.theekshana.onlineshop.Adapter.SelectedItemAdapter;
import com.theekshana.onlineshop.Model.ProductModel;
import com.theekshana.onlineshop.Model.subCateModel;

import java.util.ArrayList;

public class select_cate_list extends AppCompatActivity {

    RecyclerView select_cate_list;
    ArrayList<subCateModel> subList;
    private FirebaseAuth firebaseAuth;
    TextView selectItemGeadTv;
    SelectedItemAdapter selectedItemAdapter;


    public String intentGetData;


    public String getselectItemGeadValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cate_list);
        select_cate_list = findViewById(R.id.select_cate_list);
        selectItemGeadTv = findViewById(R.id.selectItemGeadTv);
        firebaseAuth = FirebaseAuth.getInstance();

        intentGetData= getIntent().getStringExtra("name");
        selectItemGeadTv.setText(intentGetData);
        getselectItemGeadValue = selectItemGeadTv.getText().toString();
        subList = new ArrayList<>();
        loadAllProducts();
    }

    private void loadAllProducts() {
        subList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Sub Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    subCateModel modelProduct = ds.getValue(subCateModel.class);
                    subList.add(modelProduct);

                }
                select_cate_list.setLayoutManager(new LinearLayoutManager(select_cate_list.this));
                selectedItemAdapter = new SelectedItemAdapter(select_cate_list.this,subList);
                select_cate_list.setAdapter(selectedItemAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public String getIntentGetData() {
        return intentGetData;
    }

    public void setIntentGetData(String intentGetData) {
        this.intentGetData = intentGetData;
    }
}