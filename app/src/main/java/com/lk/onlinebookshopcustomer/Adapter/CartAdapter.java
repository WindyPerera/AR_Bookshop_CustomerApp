package com.lk.onlinebookshopcustomer.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lk.onlinebookshopcustomer.Holder.CartHolder;
import com.lk.onlinebookshopcustomer.Model.Cart;
import com.lk.onlinebookshopcustomer.R;
import com.lk.onlinebookshopcustomer.UI.CartActivity;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    Context context;
    List<Cart> list;

    public CartAdapter(CartActivity cartActivity, List<Cart> cartlist) {
        this.context = cartActivity;
        this.list = cartlist;
    }


    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        holder.productName.setText(list.get(position).getProductName());
        holder.price.setText(String.valueOf(list.get(position).getProductPrice()));
        holder.qty.setText(list.get(position).getTotalQty());
        holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));
        Log.d("CartActivity..............", "" + list.get(position).getTotalQty());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //    @Override
//    protected void onStart() {
//        super.onStart();
//        fsRecycleAdapter.startListening();
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        fsRecycleAdapter.stopListening();
//    }


    public class CartHolder extends RecyclerView.ViewHolder {

        public TextView productName,price,qty,totalPrice;
        public ImageView productImage,delete_btn;

        public CartHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productNameTextView);
            price = itemView.findViewById(R.id.product_price);
            qty = itemView.findViewById(R.id.qty);
            totalPrice = itemView.findViewById(R.id.total_price);
            productImage = itemView.findViewById(R.id.productImageView);
            delete_btn = itemView.findViewById(R.id.delete_button);


        }
    }
}



