package com.lk.onlinebookshopcustomer.Holder;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lk.onlinebookshopcustomer.Model.Cart;
import com.lk.onlinebookshopcustomer.Model.Customer;
import com.lk.onlinebookshopcustomer.Model.Product;
import com.lk.onlinebookshopcustomer.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ProductHolder extends RecyclerView.ViewHolder {

    public TextView product_bran, product_name, product_price, qty;
    public Button addCart;
    public ImageView product_image, remove_btn, add_btn;

    public Product product;
    public String productID;
    public String customer_ID;
    FirebaseAuth auth;
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();

    public int totalQty = 1;
    public int totQty = 0;
    double totalPrice = 0.0;

    public ProductHolder(@NonNull View itemView) {
        super(itemView);

        product_name = itemView.findViewById(R.id.pro_name);
        product_bran = itemView.findViewById(R.id.brantText);
        product_price = itemView.findViewById(R.id.prise_text);
        product_image = itemView.findViewById(R.id.pro_image);
        qty = itemView.findViewById(R.id.qty_text);
        addCart = itemView.findViewById(R.id.add_to_cart_brn);
        remove_btn = itemView.findViewById(R.id.remove_btn);
        add_btn = itemView.findViewById(R.id.add_btn);

        auth = FirebaseAuth.getInstance();


        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebase.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("UserCart").whereEqualTo("productID", productID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<Cart> cartList = queryDocumentSnapshots.toObjects(Cart.class);

                        if (cartList.size() > 0) {
                            DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                            String cartDocID = doc.getId();
                            Cart cartModel = doc.toObject(Cart.class);
                            int total_qty = cartModel.getTotalQty();
                            totalQty = total_qty + totalQty;
                            totQty = totalQty;

                            double total_price = cartModel.getTotalPrice();
                            double unicPrice = product.getPrice();
                            totalPrice = totalQty * unicPrice;

                            firebase.collection("AddToCart").document(auth.getCurrentUser().getUid())
                                    .collection("UserCart").document(cartDocID)
                                    .update("totalQty", totalQty, "totalPrice", totalPrice).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(itemView.getContext(), "Items Add Your Cart", Toast.LENGTH_SHORT).show();
                                    qty.setText("1");
                                    totalQty = 1;
                                }
                            });
                        } else {
                            addingToCartList();
                        }
                    }
                });

            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (totalQty < 10) {
                    totalQty++;
                    qty.setText(String.valueOf(totalQty));
                }
            }
        });

        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (totalQty > 1) {
                    totalQty--;
                    qty.setText(String.valueOf(totalQty));
                }
            }
        });


    }


    private void addingToCartList() {
        String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        totalPrice = product.getPrice() * totalQty;
        totQty = totalQty;
//        final HashMap<String, Object> cartItemMap = new HashMap<>();
//        final HashMap<String, Object> cartMap = new HashMap<>();
//
//        cartMap.put("subTotal", "thama naha");
//
//        cartItemMap.put("productID", productID);
//        cartItemMap.put("customerID", customer_ID);
//        cartItemMap.put("productName", product.getProduct_name());
//        cartItemMap.put("productPrice", product.getPrice());
//        cartItemMap.put("totalQty", totalQty);
//        cartItemMap.put("totalPrice", totalPrice);
//        cartItemMap.put("currentDate", saveCurrentDate);
//        cartItemMap.put("currentTime", saveCurrentTime);

        Cart cart = new Cart();
        cart.setProductID(productID);
        cart.setCustomerId(customer_ID);
        cart.setProductName(product.getProduct_name());
        cart.setProductPrice(product.getPrice());
        cart.setTotalQty(totalQty);
        cart.setTotalPrice(totalPrice);
        cart.setCurrentDate(saveCurrentDate);
        cart.setCurrentTime(saveCurrentTime);

        Log.d("CartActivity..............", "" + auth.getCurrentUser().getUid());
        firebase.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("UserCart").add(cart)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        qty.setText("1");
                        totalQty = 1;
                        Toast.makeText(itemView.getContext(), "add to cart", Toast.LENGTH_SHORT).show();
                    }
                });
//        StorageReference cartList = FirebaseStorage.getInstance().getReference().child();


    }
}
