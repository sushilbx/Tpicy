package com.tpicy.adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tpicy.DetailsProductActivity;
import com.tpicy.ItemListener;
import com.bharatpickle.R;
import com.tpicy.models.ProductModel;
import com.bumptech.glide.Glide;


import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    List<ProductModel> arrayProduct;
    ItemListener itemListener;

    public ProductAdapter(Context context, List<ProductModel> arrayProduct, ItemListener itemListener) {
        this.context = context;
        this.arrayProduct = arrayProduct;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        ProductModel model = arrayProduct.get(position);
        Glide.with(context)
                .load(model.image)
                .into(holder.productImage);

        holder.productDiscription.setText(model.description);
        holder.productName.setText(model.name);
        holder.productPrice.setText(model.price);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                itemListener.onSelect(model.id);
                Intent myIntent = new Intent(context, DetailsProductActivity.class);
                myIntent.putExtra("id", model.id);
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productDiscription,productPrice;
        ImageView productImage;


        public ViewHolder(View itemView) {

            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productDiscription = itemView.findViewById(R.id.productDiscription);
            productPrice = itemView.findViewById(R.id.productPrice);

        }

    }
}
