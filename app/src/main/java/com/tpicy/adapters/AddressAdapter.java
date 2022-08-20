package com.tpicy.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.tpicy.AddressViewHolder;
import com.tpicy.models.AddressModel;

import java.util.List;

public class AddressAdapter extends ListAdapter<AddressModel, AddressViewHolder> {

    public AddressAdapter(@NonNull DiffUtil.ItemCallback<AddressModel> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AddressViewHolder.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        holder.bind(getItem(position));

    }

}