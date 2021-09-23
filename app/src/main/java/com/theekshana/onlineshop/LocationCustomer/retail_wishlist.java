package com.theekshana.onlineshop.LocationCustomer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theekshana.onlineshop.Adapter.AdapterOrder;
import com.theekshana.onlineshop.Adapter.MainProductAdapter;
import com.theekshana.onlineshop.Adapter.WishlistProductAdapter;
import com.theekshana.onlineshop.Model.ModelOrderUser;
import com.theekshana.onlineshop.Model.ProductModel;
import com.theekshana.onlineshop.Orders.order;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.utils.appConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class retail_wishlist extends Fragment {

    RecyclerView storyrecyle, mainProductRecycleView;
    List<ProductModel> productModelList;
    WishlistProductAdapter mainProductAdapter;
    TextView itemCount;
    private FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retail_wishlist, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        mainProductRecycleView = view.findViewById(R.id.wishlistrecycle);
        itemCount = view.findViewById(R.id.itemCount);
        //productModelList = new ArrayList<>();
        AllProductDetails();
        return view;
    }

    private void AllProductDetails() {
        productModelList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer").child(firebaseAuth.getUid()).child("Wishlist");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productModelList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ProductModel modelProduct = ds.getValue(ProductModel.class);
                    productModelList.add(modelProduct);
                }
                mainProductRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                mainProductAdapter = new WishlistProductAdapter(getContext(), productModelList);
                mainProductRecycleView.setAdapter(mainProductAdapter);

                itemCount.setText(""+snapshot.getChildrenCount()+" "+"Items");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

}
