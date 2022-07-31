package com.tpicy.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tpicy.ItemListener;
import com.bharatpickle.R;
import com.tpicy.ZoomActivity;
import com.tpicy.models.ImageModel;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    Context context;
    List<ImageModel> arrayProduct;
    ItemListener itemListener;

    public ImageAdapter(Context context, List<ImageModel> arrayProduct, ItemListener itemListener) {
        this.context = context;
        this.arrayProduct = arrayProduct;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.image_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageModel model = arrayProduct.get(position);
        Glide.with(context)
                .load(model.url)
                .into(holder.ivImageRow);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                itemListener.onSelect(model.id);
                Intent myIntent = new Intent(context, ZoomActivity.class);
                myIntent.putExtra("data", new Gson().toJson(arrayProduct));
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImageRow;


        public ViewHolder(View itemView) {

            super(itemView);

            ivImageRow = itemView.findViewById(R.id.ivImageRow);


        }
    }
}

