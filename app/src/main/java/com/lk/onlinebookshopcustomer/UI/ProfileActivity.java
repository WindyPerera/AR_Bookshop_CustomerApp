package com.lk.onlinebookshopcustomer.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lk.onlinebookshopcustomer.Model.Customer;
import com.lk.onlinebookshopcustomer.Model.Product;
import com.lk.onlinebookshopcustomer.OptionsMenuActivity;
import com.lk.onlinebookshopcustomer.R;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends NormalOptionMenu {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    public ActionBarDrawerToggle toggle;
    public Fragment fragment = null;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirebaseStorage storage;

    TextView reName, reAddress, reEmail, reMobile;
    Button reSave, reImage;
    ImageView customer_reImage;
    String customer_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        customer_id = bundle.getString("auth_id");
        setCustomer_ID(customer_id);
        storage = FirebaseStorage.getInstance();

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.profile_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AR Book Store");


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        reName = findViewById(R.id.name_field);
        reAddress = findViewById(R.id.address_field);
        reEmail = findViewById(R.id.email_field);
        reMobile = findViewById(R.id.mobile_field);

        reSave = findViewById(R.id.edit_btn);
        reImage = findViewById(R.id.change_image_btn);
        customer_reImage = findViewById(R.id.customer_image);


        db.collection("Customer").document(customer_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                Customer customer = value.toObject(Customer.class);

                reName.setText(customer.getCus_name());
                reAddress.setText(customer.getCus_address());
                reEmail.setText(customer.getCus_email());
                reMobile.setText(customer.getCus_mobileNo());

                StorageReference storageRef = storage.getReference();

                // Create a reference with an initial file path and name

                storageRef.child("CustomerImages/" + customer.getCus_picPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Log.d("HomeAactivity..............", "" + uri);
                        Picasso.with(ProfileActivity.this).load(uri).resize(250, 250).into(customer_reImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d("HomeAactivity..............", "" + exception.getMessage());
                        // Handle any errors
                    }
                });
            }
        });


        reSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ProfileActivity.this);
                alert.setMessage("Do You Want To Update Your Profile?")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                updatenow(); // Last step. update function

                            }


                        }).setNegativeButton("Cancel", null);

                AlertDialog alert1 = alert.create();
                alert1.show();


            }
        });

    }

    public void updatenow() {

        String name = reName.getText().toString();
        String address = reAddress.getText().toString();
        String email = reEmail.getText().toString();
        String mobile = reMobile.getText().toString();

        db.collection("Customer").document(customer_id).
                update("cus_name", name, "cus_address", address, "cus_email", email, "cus_mobileNo", mobile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivity.this, "Update successfully!!!!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "Error!!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}