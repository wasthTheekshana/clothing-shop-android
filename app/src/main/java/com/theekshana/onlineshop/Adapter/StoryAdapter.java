package com.theekshana.onlineshop.Adapter;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.theekshana.onlineshop.Model.Story;
import com.theekshana.onlineshop.ProfileUi.StorYZoomImage;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.story_full_detail;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {
    private final String LOG = "RecAdapter";
    List<Story> stories;
    private  Context context;

    public StoryAdapter(List<Story> stories, Context context) {
        this.stories = stories;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView sortyImage;
        View v;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sortyImage = itemView.findViewById(R.id.storyList);
            v = itemView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.stroy_list_layout, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       final Story data = stories.get(position);
        ImageView imge = holder.sortyImage;
        Glide.with(holder.sortyImage.getContext()).load(data.getImage()).into(imge);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, StorYZoomImage.class);
//                intent.putExtra("image",data.getImage());
//                context.startActivity(intent);
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.story_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageView fullstoryimage = dialog.findViewById(R.id.fullstoryimage);
                Glide.with(context).load(data.getImage()).into(fullstoryimage);
                fullstoryimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

    }



    @Override
    public int getItemCount() {
        return stories.size();
    }
}
