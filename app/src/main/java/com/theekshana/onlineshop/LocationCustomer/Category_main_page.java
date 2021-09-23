package com.theekshana.onlineshop.LocationCustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.theekshana.onlineshop.Adapter.MainProductAdapter;
import com.theekshana.onlineshop.Model.ProductModel;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.select_cate_list;

import java.util.ArrayList;

public class Category_main_page extends AppCompatActivity {

    ArrayList<ProductModel> productList;
    private FirebaseAuth firebaseAuth;
    RecyclerView cate_Rv;
    MainProductAdapter mainProductAdapter;
    View cateBackground;
    TextView cateMainHeadName;
    private String headlineName;
    select_cate_list selectcateist;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_main_page);
        name = getIntent().getStringExtra("name");
        cate_Rv = findViewById(R.id.cate_Rv);
        cateBackground = findViewById(R.id.cateBackground);
        cateMainHeadName = findViewById(R.id.cateMainHeadName);
        cateMainHeadName.setText(name);

        firebaseAuth = FirebaseAuth.getInstance();
        productList = new ArrayList<>();
           // loadShoes();
            //loadAllProducts();

        if (name.equals("men")) {
           cateBackground.setBackgroundResource(R.drawable.men_clothes);
            loadMEnPRoducts();
        } else if (name.equals("women")){
            loadwomenPRoducts();
        }else if (name.equals("kids")){
            loadkidsProducts();
        }else if (name.equals("shoes")){
            loadshoesProducts();
        }
    }

    private void loadshoesProducts() {
        productList = new ArrayList<>();
        DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("Product");
        Query query = ref.orderByChild("productsubCategory").equalTo("Shoes");
        query .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String uid = ""+ds.getRef().getKey();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer").child(uid).child("Orders");
                    ProductModel modelProduct = ds.getValue(ProductModel.class);
                    productList.add(modelProduct);

                }
                cate_Rv.setLayoutManager(new GridLayoutManager(Category_main_page.this, 2));
                mainProductAdapter = new MainProductAdapter(Category_main_page.this, productList);
                cate_Rv.setAdapter(mainProductAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadwomenPRoducts() {
        productList = new ArrayList<>();
        DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("Product");
        Query query = ref.orderByChild("productMaincategory").equalTo("women");
        query .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String uid = ""+ds.getRef().getKey();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer").child(uid).child("Orders");
                    ProductModel modelProduct = ds.getValue(ProductModel.class);
                    productList.add(modelProduct);

                }
                cate_Rv.setLayoutManager(new GridLayoutManager(Category_main_page.this, 2));
                mainProductAdapter = new MainProductAdapter(Category_main_page.this, productList);
                cate_Rv.setAdapter(mainProductAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadMEnPRoducts() {
        productList = new ArrayList<>();
        DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("Product");
        Query query = ref.orderByChild("productMaincategory").equalTo("men");
        query .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String uid = ""+ds.getRef().getKey();
                    //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product").child(uid).child("Orders");
                    ProductModel modelProduct = ds.getValue(ProductModel.class);
                    productList.add(modelProduct);

                }
                cate_Rv.setLayoutManager(new GridLayoutManager(Category_main_page.this, 2));
                mainProductAdapter = new MainProductAdapter(Category_main_page.this, productList);
                cate_Rv.setAdapter(mainProductAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadkidsProducts() {
        productList = new ArrayList<>();
        DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("Product");
        Query query = ref.orderByChild("productMaincategory").equalTo("kids");
        query .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String uid = ""+ds.getRef().getKey();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer").child(uid).child("Orders");
                    ProductModel modelProduct = ds.getValue(ProductModel.class);
                    productList.add(modelProduct);

                }
                cate_Rv.setLayoutManager(new GridLayoutManager(Category_main_page.this, 2));
                mainProductAdapter = new MainProductAdapter(Category_main_page.this, productList);
                cate_Rv.setAdapter(mainProductAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadShoes() {
        productList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Product").equalTo("women").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ProductModel modelProduct = ds.getValue(ProductModel.class);
                    productList.add(modelProduct);

                }
                cate_Rv.setLayoutManager(new GridLayoutManager(Category_main_page.this, 2));
                mainProductAdapter = new MainProductAdapter(Category_main_page.this, productList);
                cate_Rv.setAdapter(mainProductAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}