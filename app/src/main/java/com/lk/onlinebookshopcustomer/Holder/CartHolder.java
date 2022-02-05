package com.lk.onlinebookshopcustomer.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lk.onlinebookshopcustomer.Model.Cart;
import com.lk.onlinebookshopcustomer.R;

import java.util.List;

public class CartHolder extends RecyclerView.ViewHolder {

    public TextView productName,price,qty,totalPrice;
    public ImageView productImage,remove_qty,add_qty;
    public Button delete_btn;
    int totalQty;
    //double total_price= 0.0;
    public Cart cart;
    public String authID;
    public String productID;
    public int position;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CartHolder(@NonNull View itemView) {
        super(itemView);

        productName = itemView.findViewById(R.id.productNameTextView);
        price = itemView.findViewById(R.id.product_price);
        qty = itemView.findViewById(R.id.qty);
        totalPrice = itemView.findViewById(R.id.total_price);
        productImage = itemView.findViewById(R.id.productImageView);
        delete_btn = itemView.findViewById(R.id.delete_button);

        remove_qty = itemView.findViewById(R.id.remove_qty);
        add_qty = itemView.findViewById(R.id.add_qty);

        totalQty =Integer.valueOf(qty.getText().toString());


        remove_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    totalQty--;
                    qty.setText(String.valueOf(totalQty));
                    updateQty();


            }
        });

        add_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    totalQty++;
                    qty.setText(String.valueOf(totalQty));
                    updateQty();


            }
        });



    }

    public void updateQty(){
//        db.collection("AddToCart").document(authID).collection("UserCart")
//                .whereEqualTo("productID",cart.getProductID())
//                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> document = queryDocumentSnapshots.getDocuments();
//
//                if(document.size()>0){
//                    DocumentSnapshot documentSnapshot = document.get(0);
//                    String userCartId = documentSnapshot.getId();
//
//                    db.collection("AddToCart").document(authID).collection("UserCart")
//                            .document(userCartId).update("totalQty",totalQty).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//
//                        }
//                    });
//                }
//            }
//        });
    }
}
