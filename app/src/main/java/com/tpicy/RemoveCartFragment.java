package com.tpicy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.bharatpickle.R;
import com.google.android.material.button.MaterialButton;

public class RemoveCartFragment extends DialogFragment {
    MaterialButton mbRemoveCart;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public RemoveCartFragment() {

    }

    public static RemoveCartFragment newInstance(String param1, String param2) {
        RemoveCartFragment fragment = new RemoveCartFragment();
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
        View view = inflater.inflate(R.layout.fragment_remove_cart, container, false);
        mbRemoveCart = view.findViewById(R.id.mbRemoveCart);
        mbRemoveCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.removeCart(getContext(), mParam1, new ApiListener() {
                    @Override
                    public void onSuccess(String response) {
                        Bundle result = new Bundle();
                        result.putBoolean("result", true);
                        getParentFragmentManager().setFragmentResult("cart", result);
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
