package com.tpicy.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tpicy.ItemListener;
import com.bharatpickle.R;
import com.tpicy.RemoveAddressFragment;
import com.tpicy.UpdateAddressActivity;
import com.tpicy.models.AddressModel;
import com.google.gson.Gson;


import java.util.List;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context context;
    List<AddressModel> arrayAddress;



    public AddressAdapter(Context context, List<AddressModel> arrayAddress, ItemListener itemListener) {
        this.context = context;
        this.arrayAddress = arrayAddress;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.address_row, parent, false);

        ViewHolder viewHolder = new AddressAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddressModel model = arrayAddress.get(position);

        holder.tvAddressName.setText(model.name);
        holder.tvAddressVill.setText(model.house);
        holder.tvAddressDistrict.setText(model.district);
        holder.tvAddressState.setText(model.state);
        holder.tvAddressPin.setText(model.pin_code);
        holder.tvAdressMobile.setText(model.mobile);
        holder.ivAddressRowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveAddressFragment.newInstance(model.id, "").show(
                        ((AppCompatActivity) context).getSupportFragmentManager(), "remove_address_fragment");
            }
        });
        holder.ivAddressRowUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send data to another activity
                Intent intent = new Intent(context, UpdateAddressActivity.class);
                intent.putExtra("data", new Gson().toJson(model));
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayAddress.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAddressName,  tvAddressVill,tvAddressDistrict,tvAddressState,tvAddressPin,tvAdressMobile;
        ImageView  ivAddressRowDelete,ivAddressRowUpdate;



        public ViewHolder(View itemView) {

            super(itemView);

            tvAddressName = itemView.findViewById(R.id.tvAddressName);
            tvAddressVill = itemView.findViewById(R.id.tvAddressVill);
            tvAddressDistrict = itemView.findViewById(R.id.tvAddressDistrict);
            tvAddressState = itemView.findViewById(R.id.tvAddressState);
            tvAddressPin = itemView.findViewById(R.id.tvAddressPin);
            tvAdressMobile = itemView.findViewById(R.id.tvAddressMobile);
            ivAddressRowDelete = itemView.findViewById(R.id.ivAddressRowDelete);
            ivAddressRowUpdate=itemView.findViewById(R.id.ivAddressRowUpdate);
        }

    }
}

