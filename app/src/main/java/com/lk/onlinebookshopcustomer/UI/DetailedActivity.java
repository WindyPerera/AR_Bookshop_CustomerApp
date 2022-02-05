package com.lk.onlinebookshopcustomer.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lk.onlinebookshopcustomer.R;

public class DetailedActivity extends AppCompatActivity {

    TextView product_name,total_price,qty;
    Button addCart_btn;
    ImageView productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        productImage = findViewById(R.id.product_image);
        product_name = findViewById(R.id.name_text);
        total_price = findViewById(R.id.price_text);
        qty = findViewById(R.id.qty_text);
        addCart_btn = findViewById(R.id.addcart_btn);


    }
}