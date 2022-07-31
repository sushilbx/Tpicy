package com.tpicy;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatpickle.R;
import com.google.android.material.button.MaterialButton;

public class RemoveAddressFragment extends DialogFragment {
    MaterialButton mbRemoveAddress;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public RemoveAddressFragment() {
    }

    public static RemoveAddressFragment newInstance(String param1, String param2) {
        RemoveAddressFragment fragment = new RemoveAddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remove_address, container, false);
        mbRemoveAddress = view.findViewById(R.id.mbRemoveAddress);
        mbRemoveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.removeAddress(getContext(), mParam1, new ApiListener() {
                    @Override
                    public void onSuccess(String response) {
                        ((AddressActivity)requireActivity()).getAddress();
                        dismiss();
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });
            }
        });
        return view;
    }
}

