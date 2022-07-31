package com.tpicy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tpicy.ApiListener;
import com.bharatpickle.R;
import com.tpicy.RemoveCartFragment;
import com.tpicy.Utils;
import com.tpicy.models.ProductModel;
import com.bumptech.glide.Glide;


import java.util.List;

import ezy.ui.view.NumberStepper;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<ProductModel> arrayCart;
    ApiListener apiListener;


    public CartAdapter(Context context, List<ProductModel> arrayCart, ApiListener apiListener) {
        this.context = context;
        this.arrayCart = arrayCart;
        this.apiListener= apiListener;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_row, parent, false);

        ViewHolder viewHolder = new CartAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel model = arrayCart.get(position);
        Glide.with(context)
                .load(model.image)
                .into(holder.ivImageCartRow);
        holder.tvCartProductName.setText(model.name);
        holder.tvCartProductPrice.setText(model.price);
        holder.nsCartQtyAddSubtract.setValue(Integer.parseInt(model.quantity));
        holder.nsCartQtyAddSubtract.setOnValueChangedListener(new NumberStepper.OnValueChangedListener() {
            @Override
            public void onValueChanged(NumberStepper view, int value) {
                Utils.addToCart(context, model.product_id, value, apiListener);
            }
        });


        //holder.tvCartProductQuantity.setText(String.valueOf(model.quantity));
        holder.ivDeleteCartRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveCartFragment.newInstance(model.cart_item_id, "").show(
                        ((AppCompatActivity)context).getSupportFragmentManager(), "remove_cart_fragment");
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayCart.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCartProductName,  tvCartProductPrice;
        ImageView ivDeleteCartRow,ivImageCartRow;
        NumberStepper nsCartQtyAddSubtract;


        public ViewHolder(View itemView) {

            super(itemView);

            tvCartProductName = itemView.findViewById(R.id.tvNameCartRow);
            tvCartProductPrice = itemView.findViewById(R.id.tvPriceCartRow);
            ivDeleteCartRow = itemView.findViewById(R.id.ivDeleteCartRow);
            ivImageCartRow = itemView.findViewById(R.id.ivImageCartRow);
            nsCartQtyAddSubtract= itemView.findViewById(R.id.nsCartQtyAddSubtract);
        }

    }
}

