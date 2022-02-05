package com.lk.onlinebookshopcustomer.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lk.onlinebookshopcustomer.Holder.ProductHolder;
import com.lk.onlinebookshopcustomer.Model.Cart;
import com.lk.onlinebookshopcustomer.Model.Product;
import com.lk.onlinebookshopcustomer.OptionsMenuActivity;
import com.lk.onlinebookshopcustomer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductViewActivity extends OptionsMenuActivity {

    String category_name, customer_ID;
    Spinner brandsList;
    TextView cat_text;

    RecyclerView product_list;
    List<Cart> cartsList;
    DrawerLayout drawerLayout;
    NavigationView navigationView, listView;
    Toolbar toolbar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage;
    FirestoreRecyclerAdapter<Product, ProductHolder> fsRecycleAdapter;

    public ActionBarDrawerToggle toggle;
    Query loadProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
//        Product product = new Product();
//        product.setCatogory_name("Pencils");
        storage = FirebaseStorage.getInstance();
        Bundle bundle = getIntent().getExtras();
        category_name = bundle.getString("category_name");
        customer_ID = bundle.getString("userDocId");

//        brandsList = findViewById(R.id.brand_list);
        product_list = findViewById(R.id.productView_list);
        cat_text = findViewById(R.id.catgery_name_text);
        cat_text.setText(category_name);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.productView_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AR Book Store");

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

//        String[] brands = {"Atles", "Weerodara", "Richerd", "Maped"};
//        ArrayAdapter brand_list = new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, brands);
//        brandsList.setAdapter(brand_list);

//        db.collection("Product").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<Product> productList = queryDocumentSnapshots.toObjects(Product.class);
//
//                if(productList.size()>0){
//                    for(int i=0; i<=productList.size(); i++){
//                        DocumentSnapshot doc= queryDocumentSnapshots.getDocuments().get(i);
//                        Product product = doc.toObject(Product.class);
//                        product.getBrand();
//
//                    }
//                }
//            }
//        });

        loadProduct = db.collection("Product").whereEqualTo("category", category_name);
        product_list.setLayoutManager(new GridLayoutManager(this, 2));

        FirestoreRecyclerOptions recyclerOptions = new FirestoreRecyclerOptions.
                Builder<Product>().setQuery(loadProduct, Product.class).build();

        fsRecycleAdapter = new FirestoreRecyclerAdapter<Product, ProductHolder>(recyclerOptions) {

            @NonNull
            @Override
            public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new ProductHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull Product model) {
                holder.product_name.setText(model.getProduct_name());
                holder.product_bran.setText(model.getBrand());
                // Log.d("ProNameProName.........>>>>>>>>>",model.getPro_name());
                holder.product_price.setText("Rs." + String.valueOf(model.getPrice()) + "0");
                holder.productID = getSnapshots().getSnapshot(position).getId();
                holder.customer_ID = customer_ID;
                holder.product = model;

                // Create a storage reference from our app

                // Create a reference with an initial file path and name
                StorageReference storageRef = storage.getReference();

                storageRef.child("ProductImages/" + model.getPro_image()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        String s = uri.toString();

                        Glide.with(ProductViewActivity.this).load(uri).into(holder.product_image);
                    }
                });


            }

        };

        product_list.setAdapter(fsRecycleAdapter);

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

}