package com.theekshana.onlineshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.theekshana.onlineshop.Model.FilterProducts;
import com.theekshana.onlineshop.Model.ProductModel;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.story_full_detail;

import java.util.List;

public class WishlistProductAdapter extends RecyclerView.Adapter<WishlistProductAdapter.ProductDetailHolder> {

    private Context context;
    List<ProductModel> productModelList,filterlist;
    private FilterProducts filter;

    public WishlistProductAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
        this.filterlist = productModelList;
    }

    public class ProductDetailHolder extends RecyclerView.ViewHolder{
        ImageView productimage;
        TextView productName,prodctPrice;
        public ProductDetailHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_head);
            prodctPrice = itemView.findViewById(R.id.product_price);
            productimage = itemView.findViewById(R.id.home_prdoct_image);
        }
    }

    @NonNull
    @Override
    public ProductDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishlist_item,parent,false);
        return new ProductDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailHolder holder, int position) {
        ProductModel modelprProduct = productModelList.get(position);
        String id = modelprProduct.getProductId();
        String discountAvailble = modelprProduct.getDiscountAvailble();
        String discountNote = modelprProduct.getDiscountNote();
        String discountPrice = modelprProduct.getDiscountPrice();
        String productMaincategory = modelprProduct.getProductMaincategory();
        String productsubCategory = modelprProduct.getProductsubCategory();
        String description = modelprProduct.getProductDescription();
        String icon = modelprProduct.getProductImage();
        String productQty = modelprProduct.getProductQty();
        String productTitle = modelprProduct.getProductTitle();
        String timestamp = modelprProduct.getTimestamp();
        String originalPrice = modelprProduct.getOriginalPrice();


        holder.productName.setText(productModelList.get(position).getProductTitle());
        holder.prodctPrice.setText(productModelList.get(position).getOriginalPrice());
        Glide.with(context).load(productModelList.get(position).getProductImage()).into(holder.productimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, story_full_detail.class);
                intent.putExtra("pid", productModelList.get(position).getProductId());
                intent.putExtra("prName", productModelList.get(position).getProductTitle());
                intent.putExtra("prPrice", productModelList.get(position).getOriginalPrice());
                intent.putExtra("image", productModelList.get(position).getProductImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }
}
