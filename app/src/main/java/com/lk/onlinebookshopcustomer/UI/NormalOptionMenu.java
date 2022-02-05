package com.lk.onlinebookshopcustomer.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.lk.onlinebookshopcustomer.OptionsMenuActivity;
import com.lk.onlinebookshopcustomer.R;

public class NormalOptionMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    String customer_ID;

    public void setCustomer_ID(String UID) {
        this.customer_ID = UID;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.sign_out:
                showPopup();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void drawerSet(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(NormalOptionMenu.this, HomeActivity.class);
                intent.putExtra("userDocId", customer_ID);
                startActivity(intent);


                break;
            case R.id.nav_profile:
                Intent prointent = new Intent(NormalOptionMenu.this, ProfileActivity.class);
                prointent.putExtra("userDocId", customer_ID);
                startActivity(prointent);

                Log.d("Home..........>>>>>>", "profile avaaaaaaa");
                break;
            case R.id.nav_payment:
                Intent paymentIntent = new Intent(NormalOptionMenu.this, PaymentOptions.class);
                paymentIntent.putExtra("userDocId", customer_ID);
                startActivity(paymentIntent);


                Toast.makeText(this, "Payment", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.cuz_order:
//                Intent cuzIntent = new Intent(NormalOptionMenu.this, CustomizedActivity.class);
//                cuzIntent.putExtra("userDocId", customer_ID);
//                startActivity(cuzIntent);
//
//                Toast.makeText(this, "Customized Your Order Here", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.nav_delivery:
                Intent deliveryIntent = new Intent(NormalOptionMenu.this, DeliveryDetailsActivity.class);
                deliveryIntent.putExtra("userDocId", customer_ID);
                startActivity(deliveryIntent);


                Toast.makeText(this, "Delivery Detials", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_Contact:
                Intent conIntent = new Intent(NormalOptionMenu.this, ContactActivity.class);
                conIntent.putExtra("userDocId", customer_ID);
                startActivity(conIntent);
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.nav_help:
//                Intent helpIntent = new Intent(NormalOptionMenu.this, HelpActivity.class);
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
        AlertDialog.Builder alert = new AlertDialog.Builder(NormalOptionMenu.this);
        alert.setMessage("Are you sure?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        signOut(); // Last step. Logout function

                    }


                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    //
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

                        Intent redirectIntent = new Intent(NormalOptionMenu.this, LoginActivity.class);
                        redirectIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(redirectIntent);
                        finishActivity(100);
                    }
                });
        // [END auth_fui_signout]
    }
}
