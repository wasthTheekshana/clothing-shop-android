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
import com.theekshana.onlineshop.LocationCustomer.Category_main_page;
import com.theekshana.onlineshop.Model.ProductModel;
import com.theekshana.onlineshop.Model.subCateModel;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.select_cate_list;

import java.util.List;

public class SelectedItemAdapter extends RecyclerView.Adapter<SelectedItemAdapter.SubDetailHolder>{

    private Context context;
    public List<subCateModel> subCateModelList;
    select_cate_list select_cate_list;

    public SelectedItemAdapter(Context context, List<subCateModel> subCateModelList) {
        this.context = context;
        this.subCateModelList = subCateModelList;
        select_cate_list = new select_cate_list();
    }

    @NonNull
    @Override
    public SubDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.select_cate_list_item,parent,false);
        return new SubDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubDetailHolder holder, int position) {
        subCateModel subCateModel = subCateModelList.get(position);
        String id = subCateModel.getProductId();
        String title = subCateModel.getProductTitle();
        String Image = subCateModel.getProductImage();
        String headNAme = select_cate_list.intentGetData;

        holder.setlect_cate_text.setText(title);
        Glide.with(context).load(subCateModelList.get(position).getProductImage()).into(holder.select_cate_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Category_main_page.class);
                intent.putExtra("mainName",title);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCateModelList.size();
    }

    public class SubDetailHolder extends RecyclerView.ViewHolder{

        ImageView select_cate_image;
        TextView setlect_cate_text;
        public SubDetailHolder(@NonNull View itemView) {
            super(itemView);
            select_cate_image = itemView.findViewById(R.id.select_cate_image);
            setlect_cate_text = itemView.findViewById(R.id.setlect_cate_text);
        }
    }
}
