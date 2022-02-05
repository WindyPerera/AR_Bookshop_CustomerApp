package com.lk.onlinebookshopcustomer;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.lk.onlinebookshopcustomer.UI.AboutUsActivity;
import com.lk.onlinebookshopcustomer.UI.CartActivity;
import com.lk.onlinebookshopcustomer.UI.ContactActivity;
import com.lk.onlinebookshopcustomer.UI.CustomizedActivity;
import com.lk.onlinebookshopcustomer.UI.DeliveryDetailsActivity;
import com.lk.onlinebookshopcustomer.UI.HelpActivity;
import com.lk.onlinebookshopcustomer.UI.HomeActivity;
import com.lk.onlinebookshopcustomer.UI.LoginActivity;
import com.lk.onlinebookshopcustomer.UI.PaymentOptions;
import com.lk.onlinebookshopcustomer.UI.ProfileActivity;

public class OptionsMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    String customer_ID;
    String num;

    public void setCustomer_ID(String UID) {
        this.customer_ID = UID;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = menuItem.getActionView();
        TextView cartBadgeTextView = actionView.findViewById(R.id.cartItemCountText);

        cartBadgeTextView.setText("0");



        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

//    public void setbatch(String num) {
//        this.num = num;
//        Log.d("awaaaaaaaaaaaaa",num);
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:

                Intent intent = new Intent(OptionsMenuActivity.this, CartActivity.class);
                intent.putExtra("userDocId", customer_ID);
                startActivity(intent);

                return true;
//            case R.id.notification_action:
//
//                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
//
//                return true;
            case R.id.sign_out:
                showPopup();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void drawerSet(DrawerLayout drawerLayout, String customerID) {
        this.drawerLayout = drawerLayout;
        this.customer_ID = customerID;
        Log.d("Option..............", "" + customerID);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(OptionsMenuActivity.this, HomeActivity.class);
                intent.putExtra("userDocId", customer_ID);
                startActivity(intent);


                break;
            case R.id.nav_profile:
                Intent prointent = new Intent(OptionsMenuActivity.this, ProfileActivity.class);
                prointent.putExtra("userDocId", customer_ID);
                startActivity(prointent);

                Log.d("Home..........>>>>>>", "profile avaaaaaaa");
                break;
            case R.id.nav_payment:
                Intent paymentIntent = new Intent(OptionsMenuActivity.this, PaymentOptions.class);
                paymentIntent.putExtra("userDocId", customer_ID);
                startActivity(paymentIntent);


                Toast.makeText(this, "Payment", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.cuz_order:
//                Intent cuzIntent = new Intent(OptionsMenuActivity.this, CustomizedActivity.class);
//                cuzIntent.putExtra("userDocId", customer_ID);
//                startActivity(cuzIntent);
//
//                Toast.makeText(this, "Customized Your Order Here", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.nav_delivery:
                Intent deliveryIntent = new Intent(OptionsMenuActivity.this, DeliveryDetailsActivity.class);
                deliveryIntent.putExtra("userDocId", customer_ID);
                startActivity(deliveryIntent);


                Toast.makeText(this, "Delivery Detials", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_Contact:
                Intent conIntent = new Intent(OptionsMenuActivity.this, ContactActivity.class);
                conIntent.putExtra("userDocId", customer_ID);
                startActivity(conIntent);
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_about:
                Intent aboutIntent = new Intent(OptionsMenuActivity.this, AboutUsActivity.class);
                aboutIntent.putExtra("userDocId", customer_ID);
                startActivity(aboutIntent);
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.nav_help:
//                Intent helpIntent = new Intent(OptionsMenuActivity.this, HelpActivity.class);
//                helpIntent.putExtra("userDocId", customer_ID);
//                startActivity(helpIntent);
//
//                Toast.makeText(this, "Hepl", Toast.LENGTH_SHORT).show();
//                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(OptionsMenuActivity.this);
        alert.setMessage("Are you sure?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        signOut(); // Last step. Logout function

                    }


                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }


    //    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {

                        Intent redirectIntent = new Intent(OptionsMenuActivity.this, LoginActivity.class);
                        redirectIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(redirectIntent);
                        finishActivity(100);
                    }
                });
        // [END auth_fui_signout]
    }
}
