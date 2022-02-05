package com.lk.onlinebookshopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lk.onlinebookshopcustomer.R;
import com.lk.onlinebookshopcustomer.UI.CashOnDeliveryActivity;
import com.lk.onlinebookshopcustomer.UI.PaymentActivity;

public class PaymentMethod extends Fragment {

    LinearLayout online_pay,cash_on_pay;


    public PaymentMethod() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_payment_method, container, false);
        online_pay = view.findViewById(R.id.online_pay);
        cash_on_pay = view.findViewById(R.id.cashOn_pay);

        online_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), PaymentActivity.class);
                startActivity(intent);

            }
        });

        cash_on_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CashOnDeliveryActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


}