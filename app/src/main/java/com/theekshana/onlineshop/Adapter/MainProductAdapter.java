package com.theekshana.onlineshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.theekshana.onlineshop.Model.FilterProducts;
import com.theekshana.onlineshop.Model.ProductModel;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.story_full_detail;

import java.util.List;

public class MainProductAdapter extends RecyclerView.Adapter<MainProductAdapter.ProductDetailHolder> {

    private Context context;
    public List<ProductModel> productModelList,filterlist;
    private FilterProducts filter;

    public MainProductAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
        this.filterlist = productModelList;
    }


    public FilterProducts getFilter() {
        if (filter == null){
            filter = new FilterProducts(filterlist, this);
        }
        return filter;
    }

    public class ProductDetailHolder extends RecyclerView.ViewHolder{
        ImageView productimage;
        TextView productName,prodctPrice,discount,discountprice;
        public ProductDetailHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_head);
            prodctPrice = itemView.findViewById(R.id.product_price);
            productimage = itemView.findViewById(R.id.home_prdoct_image);
            discountprice = itemView.findViewById(R.id.discountPrice);
            discount = itemView.findViewById(R.id.discount);
        }
    }

    @NonNull
    @Override
    public ProductDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_product_list,parent,false);
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
        String size = modelprProduct.getProductSize();
        String originalPrice = modelprProduct.getOriginalPrice();

        holder.productName.setText(productTitle);
        holder.prodctPrice.setText("RS "+originalPrice);
        holder.discountprice.setText("RS "+discountPrice);
        holder.discount.setText(discountNote);

        if (discountAvailble.equals("true")){
            holder.discountprice.setVisibility(View.VISIBLE);
            holder.discount.setVisibility(View.VISIBLE);
            holder.prodctPrice.setPaintFlags(holder.prodctPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }else if (discountAvailble.equals("false")){
            holder.discountprice.setVisibility(View.GONE);
            holder.discount.setVisibility(View.GONE);

        }
        if (productQty.equals("0")){
            holder.discountprice.setVisibility(View.VISIBLE);
            holder.discountprice.setText("Out of Stock");
        }
        try{
            Glide.with(context).load(productModelList.get(position).getProductImage()).into(holder.productimage);
        }catch (Exception e){

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, story_full_detail.class);
                if (productQty.equals("0")){
                    Toast.makeText(context, "The Product is Out of Stock", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (discountAvailble.equals("true")){
                    intent.putExtra("pid", productModelList.get(position).getProductId());
                    intent.putExtra("prName", productModelList.get(position).getProductTitle());
                    intent.putExtra("prPrice", productModelList.get(position).getDiscountPrice());
                    intent.putExtra("image", productModelList.get(position).getProductImage());
                    intent.putExtra("size", productModelList.get(position).getProductSize());
                    context.startActivity(intent);
                }else if (discountAvailble.equals("false")){
                    intent.putExtra("pid", productModelList.get(position).getProductId());
                    intent.putExtra("prName", productModelList.get(position).getProductTitle());
                    intent.putExtra("prPrice", productModelList.get(position).getOriginalPrice());
                    intent.putExtra("image", productModelList.get(position).getProductImage());
                    intent.putExtra("size", productModelList.get(position).getProductSize());
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }
}
