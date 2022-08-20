package com.tpicy;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tpicy.databinding.AddressRowBinding;
import com.tpicy.models.AddressModel;


public class AddressViewHolder extends RecyclerView.ViewHolder {
    AddressRowBinding b;

    public AddressViewHolder(@NonNull View itemView, AddressRowBinding binding) {
        super(itemView);
        this.b = binding;
    }

    public void bind(AddressModel model) {

        b.tvAddressName.setText(model.name);
        b.tvAddressVill.setText(model.house);
        b.tvAddressDistrict.setText(model.district);
        b.tvAddressState.setText(model.state);
        b.tvAddressPin.setText(model.pin_code);
        b.tvAddressMobile.setText(model.mobile);
        b.ivAddressRowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveAddressFragment.newInstance(model.id, "").show(
                        ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager(), "remove_address_fragment");
            }
        });
        b.ivAddressRowUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send data to another activity
                Intent intent = new Intent(itemView.getContext(), UpdateAddressActivity.class);
                intent.putExtra("data", new Gson().toJson(model));
                itemView.getContext().startActivity(intent);

            }
        });
    }
    public static AddressViewHolder create(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AddressRowBinding b = AddressRowBinding.inflate(inflater, parent, false);
        return new AddressViewHolder(b.getRoot(), b);
    }
}

