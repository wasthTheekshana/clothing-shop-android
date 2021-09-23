package com.theekshana.onlineshop.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theekshana.onlineshop.Model.ModelOderedItem;
import com.theekshana.onlineshop.R;

import java.util.ArrayList;

public class AdapterOderedItem extends RecyclerView.Adapter<AdapterOderedItem.HolderOrderedItem> {

    private Context context;
    private ArrayList<ModelOderedItem> oderedItemArrayList;

    public AdapterOderedItem(Context context, ArrayList<ModelOderedItem> oderedItemArrayList) {
        this.context = context;
        this.oderedItemArrayList = oderedItemArrayList;
    }

    @NonNull
    @Override
    public HolderOrderedItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_order_full_details,parent,false);
        return new HolderOrderedItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderedItem holder, int position) {
        ModelOderedItem modelOderedItem = oderedItemArrayList.get(position);
        String pid = modelOderedItem.getPid();
        String name = modelOderedItem.getName();
        String price = modelOderedItem.getPrice();
        String qty = modelOderedItem.getQty();

        holder.ItemTitileTv.setText(name);
        holder.ItempriceTv.setText("RS "+price);
        holder.itemQuantituTv.setText(qty);
    }

    @Override
    public int getItemCount() {
        return oderedItemArrayList.size();
    }

    public class HolderOrderedItem extends RecyclerView.ViewHolder{

        private TextView ItemTitileTv,ItempriceTv,itemPriceEachTv,itemQuantituTv;
        public HolderOrderedItem(@NonNull View itemView) {
            super(itemView);
            ItemTitileTv = itemView.findViewById(R.id.ItemTitileTv);
            ItempriceTv = itemView.findViewById(R.id.ItempriceTv);
            itemQuantituTv = itemView.findViewById(R.id.itemQuantituTv);
        }
    }
}
