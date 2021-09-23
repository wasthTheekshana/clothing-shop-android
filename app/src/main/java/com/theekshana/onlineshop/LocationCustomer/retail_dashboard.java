package com.theekshana.onlineshop.LocationCustomer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.theekshana.onlineshop.Adapter.MainProductAdapter;
import com.theekshana.onlineshop.Adapter.StoryAdapter;
import com.theekshana.onlineshop.CollectionInter.womencollection;
import com.theekshana.onlineshop.Database.SessionManager;
import com.theekshana.onlineshop.Login.loginuser;
import com.theekshana.onlineshop.Model.Constants;
import com.theekshana.onlineshop.Model.ProductModel;
import com.theekshana.onlineshop.Model.Story;
import com.theekshana.onlineshop.Payments.ScanandPay;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.utils.appConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class retail_dashboard extends Fragment {


    RecyclerView storyrecyle,mainProductRecycleView;
    ArrayList<ProductModel> productList;
    ArrayList<Story> storyList;
    private FirebaseAuth firebaseAuth;
    private String uid,name,email,mobile,fcmToken,lname,pw;

    TextView collec;
    ImageButton filterBtn;
    ImageView scanBoth;
    TextView filteredProductTv;
    MainProductAdapter mainProductAdapter;
    Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_retail_dashboard,container,false);
        storyrecyle = view.findViewById(R.id.storyrecyleView);
        collec = view.findViewById(R.id.collection);
        mainProductRecycleView = view.findViewById(R.id.home_main_recylce);
        filterBtn = view.findViewById(R.id.filterProductBtn);
        filteredProductTv = view.findViewById(R.id.filteredProductTv);
        scanBoth = view.findViewById(R.id.scanBoth);



        firebaseAuth = FirebaseAuth.getInstance();
        Log.d("Token", FirebaseInstanceId.getInstance().getToken());
        checkUser();
        loadShopDetails();
        storyList = new ArrayList<>();
        productList = new ArrayList<>();

       // AllProductDetails();
        StoryProductDetails();
        loadAllProducts();

        collec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), womencollection.class);
                startActivity(intent);
            }
        });

        scanBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent scan = new Intent(getContext(), ScanandPay.class);
               startActivity(scan);
            }
        });

        filteredProductTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(getContext());
                builder.setTitle("Choose Category").setItems(Constants.MainproductCategory, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String category = Constants.MainproductCategory[which];
                        filteredProductTv.setText(category);
                        if (category.equals("All")){
                            loadAllProducts();
                        }else {
                            loadFilterdMainProducts(category);
                        }
                        //setText krnna;
                    }
                }).show();
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(getContext());
                builder.setTitle("Choose Category").setItems(Constants.productCategory, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String category = Constants.productCategory[which];
                       // filteredProductTv.setText(category);
                        if (category.equals("All")){
                            loadAllProducts();
                        }else {
                            loadFilterdProducts(category);
                        }
                        //setText krnna;
                    }
                }).show();
            }
        });
            return view;
    }

    private void loadFilterdMainProducts(String category) {

        productList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    String productCategory = ""+ds.child("productMaincategory").getValue();
                    if (category.equals(productCategory)){
                        ProductModel modelProduct = ds.getValue(ProductModel.class);
                        productList.add(modelProduct);
                    }


                }
                mainProductRecycleView.setLayoutManager(new GridLayoutManager(getContext(),2));
                mainProductAdapter = new MainProductAdapter(getContext(),productList);
                mainProductRecycleView.setAdapter(mainProductAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private String shopAddress;

    private void loadShopDetails() {
        FirebaseDatabase.getInstance().getReference("ShopDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    shopAddress =""+ds.child("address").getValue();
                    Toast.makeText(getContext(), "SHopAddress"+""+shopAddress, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void checkUser() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null){
            Intent i = new Intent(getContext(), loginuser.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
        }else {
            loadMydata();
        }

    }
    private void loadAllProducts() {
        productList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    ProductModel modelProduct = ds.getValue(ProductModel.class);
                    productList.add(modelProduct);

                }
                mainProductRecycleView.setLayoutManager(new GridLayoutManager(getContext(),2));
                mainProductAdapter = new MainProductAdapter(getContext(),productList);
                mainProductRecycleView.setAdapter(mainProductAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadFilterdProducts(String category) {
        productList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    String productCategory = ""+ds.child("productsubCategory").getValue();
                    if (category.equals(productCategory)){
                        ProductModel modelProduct = ds.getValue(ProductModel.class);
                        productList.add(modelProduct);
                    }


                }
                mainProductRecycleView.setLayoutManager(new GridLayoutManager(getContext(),2));
                mainProductAdapter = new MainProductAdapter(getContext(),productList);
                mainProductRecycleView.setAdapter(mainProductAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                    name = ""+ds.child("firstName").getValue();
                     lname = ""+ds.child("lastName").getValue();
                    email = ""+ds.child("email").getValue();
                     pw = ""+ds.child("password").getValue();
                     mobile = ""+ds.child("mobile").getValue();
                     fcmToken = ""+ds.child("fcmToken").getValue();
                    String createDate = ""+ds.child("createDate").getValue();
                    String cardStatus = ""+ds.child("cardStatus").getValue();
                    String isActive = ""+ds.child("isActive").getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.createuserSession(name,"lNname",email,pw,null,fcmToken,mobile,"2","2","1","2");

    }

    private void StoryProductDetails() {
        storyList = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("New Stock").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storyList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Story modelStory = ds.getValue(Story.class);
                    storyList.add(modelStory);

                }
                StoryAdapter rea = new StoryAdapter(storyList, getContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                storyrecyle.setAdapter(rea);
                storyrecyle.setLayoutManager(linearLayoutManager);
                storyrecyle.setItemAnimator(new DefaultItemAnimator());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}