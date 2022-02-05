package com.lk.onlinebookshopcustomer.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.widget.Toolbar;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lk.onlinebookshopcustomer.Model.Customer;
import com.lk.onlinebookshopcustomer.OptionsMenuActivity;
import com.lk.onlinebookshopcustomer.R;
import com.lk.onlinebookshopcustomer.slider.SliderAdapter;
import com.lk.onlinebookshopcustomer.slider.SliderData;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeActivity extends OptionsMenuActivity {
    //Variables
    ProgressDialog progressDialog;
    DrawerLayout drawerLayout;
    NavigationView navigationView, listView;
    Toolbar toolbar;
    TextView durationLbl;
    private LatLng customerLocation,dropLocation;

    public ActionBarDrawerToggle toggle;
    public Fragment fragment = null;
    View view;
    SliderView sliderView;
    String url1 = "https://today.duke.edu/sites/default/files/styles/story_hero/public/books%20HERO%C6%92.jpg?itok=gGLyPFoX";
    String url2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTa0W-7wMg3YNg6xlXuBsaGmP3qdmU9o9X5PQ&usqp=CAU";
    String url3 = "https://static01.nyt.com/images/2018/06/17/books/review/17edchoice-covers/17edchoice-covers-articleLarge.jpg?quality=75&auto=webp&disable=upscale";


    //firebase reference
    FirebaseFirestore db;
    FirebaseStorage storage;
    ImageView imageView;

    ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
    CardView crbook_list, exe_book_list, pen_list, pencil_list, eraser, stantionery_list, papers_list, glue_list;

    ScrollView category_list;
    TextView userName, userEmail;
    String customerName,customerEmail;
    ImageView userImage;
    TextView name;
    TextView email;
    String customer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setTitle("Welcome To AR BOOK SHOP");
        progressDialog.setMessage("please wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        Bundle bundle = getIntent().getExtras();

        customer_id = bundle.getString("userDocId");
        setCustomer_ID(customer_id);

        Log.d("Home..............", "" + customer_id);


        // toolbar set
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AR Book Store");

        drawerSet(drawerLayout,customer_id);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        userName =  navigationView.findViewById(R.id.nameTextView);
        userEmail = navigationView.findViewById(R.id.emailText);
        userImage = navigationView.findViewById(R.id.userImage);

        sliderView = findViewById(R.id.slider);

        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        slidershow();
        progressDialog.dismiss();

        category_list = findViewById(R.id.load_catogory);
        //category list
        crbook_list = findViewById(R.id.crbooks_list);
        //Toast.makeText(view.getContext(),"Sign In Ok",Toast.LENGTH_SHORT).show();
        exe_book_list = findViewById(R.id.exebook_list);
        pen_list = findViewById(R.id.pens_list);
        pencil_list = findViewById(R.id.pencils_list);
        glue_list = findViewById(R.id.glu_list);
        eraser = findViewById(R.id.eraser_list);

        setListener();

    }

    @Override
    public void setCustomer_ID(String UID) {
        super.setCustomer_ID(UID);
    }

    @Override
    public void drawerSet(DrawerLayout drawerLayout,String customer_id) {
        super.drawerSet(drawerLayout,customer_id);
        Log.d("HomeAactivity---------------", "" + customer_id);
    }

    public void slidershow() {

        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

    }

    private void setListener() {

        crbook_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductViewActivity.class);
                intent.putExtra("category_name", "CR Books");
                intent.putExtra("userDocId", customer_id);
                startActivity(intent);


            }
        });
        exe_book_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductViewActivity.class);
                intent.putExtra("category_name", "Exercise Books");
                intent.putExtra("userDocId", customer_id);
                startActivity(intent);


            }
        });
        pencil_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductViewActivity.class);
                intent.putExtra("category_name", "Pencils");
                intent.putExtra("userDocId", customer_id);
                startActivity(intent);


            }
        });
        pen_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductViewActivity.class);
                intent.putExtra("category_name", "Pens");
                intent.putExtra("userDocId", customer_id);
                startActivity(intent);


            }
        });
        glue_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductViewActivity.class);
                intent.putExtra("category_name", "Glue Bottles");
                intent.putExtra("userDocId", customer_id);
                startActivity(intent);


            }
        });
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductViewActivity.class);
                intent.putExtra("category_name", "Erasers");
                intent.putExtra("userDocId", customer_id);
                startActivity(intent);


            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                intent.putExtra("auth_id",customer_id);
                startActivity(intent);
//                finish();

                break;
            case R.id.nav_profile:
                Intent prointent = new Intent(HomeActivity.this, ProfileActivity.class);
                prointent.putExtra("auth_id",customer_id);
                startActivity(prointent);

                Log.d("Home..........>>>>>>", "profile avaaaaaaa");
                break;
            case R.id.nav_payment:
                Intent paymentIntent = new Intent(HomeActivity.this, PaymentOptions.class);
                paymentIntent.putExtra("auth_id",customer_id);
                startActivity(paymentIntent);


                Toast.makeText(this, "Payment", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.cuz_order:
//                Intent cuzIntent = new Intent(HomeActivity.this, CustomizedActivity.class);
//                cuzIntent.putExtra("auth_id", customer_id);
//                startActivity(cuzIntent);
//
//                Toast.makeText(this, "Customized Your Order Here", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.nav_delivery:
                Intent deliveryIntent = new Intent(HomeActivity.this, DeliveryDetailsActivity.class);
                deliveryIntent.putExtra("auth_id",customer_id);
                startActivity(deliveryIntent);


                Toast.makeText(this, "Delivery Detials", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_Contact:
                Intent conIntent = new Intent(HomeActivity.this, ContactActivity.class);
                conIntent.putExtra("auth_id",customer_id);
                startActivity(conIntent);


                break;

            case R.id.nav_about:
                Intent aboutIntent = new Intent(HomeActivity.this, AboutUsActivity.class);
                aboutIntent.putExtra("auth_id",customer_id);
                startActivity(aboutIntent);
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.nav_help:
//                Intent helpIntent = new Intent(HomeActivity.this, HelpActivity.class);
//                startActivity(helpIntent);
//
//                Toast.makeText(this, "Hepl", Toast.LENGTH_SHORT).show();
//                break;
        }
        return true;
    }

    public void  setDuration( String duration){
        durationLbl.setText(duration);

    }

    public  void setJobLatLang(LatLng customerLocation, LatLng dropLocatin){
        this.customerLocation = customerLocation;
        this.dropLocation = dropLocatin;
    }

}
