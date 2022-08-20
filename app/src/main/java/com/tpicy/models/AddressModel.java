package com.tpicy.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.Gson;

import java.util.List;

public class AddressModel {

    public String name;
    public String mobile;
    public String district;
    public String house;
    public String state;
    public String status;
    public String pin_code;
    public String user_id;
    public String id;

    public List<AddressModel>address;

    public static DiffUtil.ItemCallback<AddressModel> ADDRESS_COMPARATOR = new DiffUtil.ItemCallback<AddressModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull AddressModel oldItem, @NonNull AddressModel newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull AddressModel oldItem, @NonNull AddressModel newItem) {
            return new Gson().toJson(oldItem).equals(new Gson().toJson(newItem));
        }
    };
}





