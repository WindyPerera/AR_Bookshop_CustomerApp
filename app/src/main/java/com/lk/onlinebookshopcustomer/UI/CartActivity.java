package com.lk.onlinebookshopcustomer.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lk.onlinebookshopcustomer.Adapter.CartAdapter;
import com.lk.onlinebookshopcustomer.Holder.CartHolder;
import com.lk.onlinebookshopcustomer.Model.Cart;
import com.lk.onlinebookshopcustomer.Model.Product;
import com.lk.onlinebookshopcustomer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends NormalOptionMenu {

    NavigationView navigationView;
    Toolbar toolbar;
    public ActionBarDrawerToggle toggle;
    ConstraintLayout constraintLayout;

    RecyclerView cartItem_list;
    TextView subTotal_Text;
    Button place_order;
    double subtotal, total;
    FirebaseFirestore db;
    public FirebaseStorage storage;
    private FirestoreRecyclerAdapter<Cart, CartHolder> fsRecycleAdapter;

    String customer_id;

    ArrayList<Cart> cartlist;
    CartAdapter cartAdapter;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        storage = FirebaseStorage.getInstance();

        Bundle bundle = getIntent().getExtras();
        customer_id = bundle.getString("userDocId");
        Log.d("customer_idcustomer_id..............", "" + customer_id);
        place_order = findViewById(R.id.place_order);

        db = FirebaseFirestore.getInstance();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Cart");

        cartItem_list = findViewById(R.id.cartRecycleView);
        subTotal_Text = findViewById(R.id.subTotal);
        auth = FirebaseAuth.getInstance();


        Query query = db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("UserCart");
        FirestoreRecyclerOptions recycleoptions = new FirestoreRecyclerOptions.Builder<Cart>().setQuery(query, Cart.class).build();


        fsRecycleAdapter = new FirestoreRecyclerAdapter<Cart, CartHolder>(recycleoptions) {

            @NonNull
            @Override
            public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
                return new CartHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CartHolder holder, int position, @NonNull Cart model) {

                holder.productID = model.getProductID();
                holder.position = position;
                holder.productName.setText(model.getProductName());
                Log.d("cuproductName..............", "" + model.getProductName());

                holder.price.setText(String.valueOf("Rs. " + model.getProductPrice()));

                holder.qty.setText(String.valueOf(model.getTotalQty()));
                holder.totalPrice.setText(String.valueOf("Rs. " + model.getTotalPrice()));
                total = model.getTotalPrice();
                subtotal = total + subtotal;
                subTotal_Text.setText(String.valueOf(subtotal + "0"));
                holder.cart = model;
                holder.authID = auth.getCurrentUser().getUid();

                //get product Image
                db.collection("Product").document(holder.productID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Product product = documentSnapshot.toObject(Product.class);
                        double unicprice = product.getPrice();

                        Log.d("CartActivity........ppppp......", "" + model.getProductID());

                        //                Create a storage reference from our app
                        StorageReference storageRef = storage.getReference();
                        Log.d("storageRef....................", "" + storageRef + "ProductImages/");
                        storageRef.child("ProductImages/" + product.getPro_image()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.d("uriiiiiiiiiiiiiiiiiii....................", "" + uri);

                                Picasso.with(CartActivity.this).load(uri).resize(250, 250).into(holder.productImage);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("CartActivity..............", "" + e.getMessage());
                            }
                        });

                    }
                });

                //cart Item remove button
                holder.delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                                .collection("UserCart").document(getSnapshots().getSnapshot(position).getId())
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
//                                Log.w(TAG, "Error deleting document", e);
                            }
                        });

                    }
                });


            }
        };



        cartItem_list.setAdapter(fsRecycleAdapter);
        cartItem_list.setLayoutManager(new LinearLayoutManager(this));

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subtotal >500){
                    Intent intent = new Intent(CartActivity.this, PaymentMethod.class);
                    subTotal_Text.setText("");
                    intent.putExtra("subTotal", String.valueOf(subtotal));
                    intent.putExtra("cartID",auth.getCurrentUser().getUid());
                    intent.putExtra("userDocID",customer_id);
                    Log.d("iiiiddddddddd",customer_id);

                    startActivity(intent);
                }else{

                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        fsRecycleAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        fsRecycleAdapter.stopListening();
    }

//


}



