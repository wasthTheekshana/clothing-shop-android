package com.theekshana.onlineshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.theekshana.onlineshop.Database.DBHelper;
import com.theekshana.onlineshop.Model.CartModel;
import com.theekshana.onlineshop.R;

import java.util.List;

public class CartAdapte extends RecyclerView.Adapter<CartAdapte.HolderCartItem> {

    private Context context;
    List<CartModel> cartModelList;
    public String prNAme;
    public String prPrice;
    public String qty;
    DBHelper dbHelper;
    public CartAdapte(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        dbHelper = new DBHelper(context);
    }

    public class HolderCartItem extends RecyclerView.ViewHolder{
        ImageView productimage;
        TextView productName,prodctPrice,qtyview;
        ImageButton dlt_btn,discreas;

        public HolderCartItem(@NonNull View itemView) {
            super(itemView);
            productimage = itemView.findViewById(R.id.cartProductImage);
            productName = itemView.findViewById(R.id.cartPr_name);
            prodctPrice = itemView.findViewById(R.id.cartPr_price);
            qtyview = itemView.findViewById(R.id.qtyText);
            dlt_btn = itemView.findViewById(R.id.dlt_btn);
        }
    }

    @NonNull
    @Override
    public HolderCartItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_iten_list,parent,false);
        return new HolderCartItem(view);
    }

    private double cost = 0;
    private double finalcost = 0;
    private int quantity = 0;

    @Override
    public void onBindViewHolder(@NonNull HolderCartItem holder, int position) {
            CartModel cartModel = cartModelList.get(position);
            String id = cartModel.getId();
             prNAme = cartModel.getName();
             prPrice = cartModel.getPrice();
             qty = cartModel.getQuantity();

            holder.productName.setText(prNAme);
            holder.prodctPrice.setText(prPrice);
            holder.qtyview.setText(qty);
        Glide.with(context).load(cartModelList.get(position).getImage()).into(holder.productimage);
         cost = Double.parseDouble(prPrice.replaceAll("Rs",""));
        finalcost =Double.parseDouble(prPrice.replaceAll("Rs",""));
        quantity =1;

        holder.dlt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deletedata(id);
                cartModelList.remove(position);
                notifyItemChanged(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }
}
