package com.Koal.PettoApplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class feedRecyclerAdapter extends RecyclerView.Adapter<feedRecyclerAdapter.PostHolder> {
    private ArrayList<String> titleList;
    private ArrayList<String> descList;
    private ArrayList<String> imageList;

    public feedRecyclerAdapter(ArrayList<String> titleList, ArrayList<String> descList, ArrayList<String> imageList) {
        this.titleList = titleList;
        this.descList = descList;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.titleText.setText(titleList.get(position));
        holder.descText.setText(descList.get(position));
        Picasso.get().load(imageList.get(position)).into(holder.imageViews);




    }

    @Override
    public int getItemCount() {
        return descList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{
        ImageView imageViews;
        TextView titleText;
        TextView descText;

        public PostHolder(@NonNull View itemView) {
            super(itemView);


            imageViews = itemView.findViewById(R.id.recyclerView_imageview);
            titleText = itemView.findViewById(R.id.recyclerView_title);
            descText = itemView.findViewById(R.id.recyclerView_desc);
        }
    }
}
