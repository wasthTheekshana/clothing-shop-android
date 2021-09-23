package com.theekshana.onlineshop.Adapter;


import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theekshana.onlineshop.Model.FilterOrder;
import com.theekshana.onlineshop.Model.ModelOrderUser;
import com.theekshana.onlineshop.Orders.OrderDetails;
import com.theekshana.onlineshop.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.HolderOrderUser> implements Filterable {

    private Context context;
    public ArrayList<ModelOrderUser> modelOrderUserList,filterlist;
    private FilterOrder filter;

    public AdapterOrder(Context context, ArrayList<ModelOrderUser> modelOrderUserList) {
        this.context = context;
        this.modelOrderUserList = modelOrderUserList;
        this.filterlist = modelOrderUserList;
    }

    @NonNull
    @Override
    public HolderOrderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_oder_list,parent,false);

        return new HolderOrderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderUser holder, int position) {

        ModelOrderUser modelOrderUser = modelOrderUserList.get(position);
        String orderId = modelOrderUser.getOrderId();
        String orderBy = modelOrderUser.getOderBy();
        String orderCost = modelOrderUser.getOrderCost();
        String orderStatus = modelOrderUser.getOrderStatus();
        String orderTime = modelOrderUser.getOrderTime();
        String deliveryStatus = modelOrderUser.getDeliveryStatus();

        loadUserInfo(modelOrderUser,holder);

        holder.oderCost.setText("Amount Rs :" +orderCost);
        holder.StatusTv.setText(orderStatus);
        holder.orderIdTv.setText("Order Id :" +orderId);
        holder.deliveryStatusTv.setText(deliveryStatus);
        if (orderStatus.equals("In Progress")){
            holder.StatusTv.setTextColor(context.getResources().getColor(R.color.blue));
        }else if (orderStatus.equals("Finished")){
            holder.StatusTv.setTextColor(context.getResources().getColor(R.color.teal_200));
        }else if (orderStatus.equals("Cancelled")){
            holder.StatusTv.setTextColor(context.getResources().getColor(R.color.red));
        }else if (orderStatus.equals("Confrim")){
            holder.StatusTv.setTextColor(context.getResources().getColor(R.color.hilightGreen));
        }else if (orderStatus.equals("Packaging")){
            holder.StatusTv.setTextColor(context.getResources().getColor(R.color.back3));
        }

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(Long.parseLong(orderTime));
//        String formatTime = DateFormat.format("dd/MM/yyyy",calendar).toString();
//        holder.dateTv.setText(formatTime);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, OrderDetails.class);
                intent.putExtra("OderId",orderId);
                intent.putExtra("OderBy",orderBy);
                context.startActivity(intent);
            }
        });
    }

    private void loadUserInfo(ModelOrderUser modelOrderUser, HolderOrderUser holder) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref.child(modelOrderUser.getOderBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelOrderUserList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new FilterOrder(filterlist,this);

        }
        return filter;
    }

    class HolderOrderUser extends RecyclerView.ViewHolder{
        TextView orderIdTv,oderCost,dateTv,deliveryStatusTv,StatusTv;
        ImageView nextIv;
        public HolderOrderUser(@NonNull View itemView) {
            super(itemView);
            orderIdTv = itemView.findViewById(R.id.orderIdTv);
            oderCost = itemView.findViewById(R.id.oderCost);
            dateTv = itemView.findViewById(R.id.dateTv);
            deliveryStatusTv = itemView.findViewById(R.id.deliveryStatusTv);
            StatusTv = itemView.findViewById(R.id.StatusTv);
            nextIv = itemView.findViewById(R.id.nextIv);
        }
    }
}
