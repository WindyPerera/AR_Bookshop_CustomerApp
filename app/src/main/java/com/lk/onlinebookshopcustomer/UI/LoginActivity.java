package com.lk.onlinebookshopcustomer.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lk.onlinebookshopcustomer.Model.Customer;
import com.lk.onlinebookshopcustomer.R;
import com.lk.onlinebookshopcustomer.SQLite.DatabaseHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private int RC_SIGN_IN = 123;
    SignInButton login_btn;
    String FCMToken = null;
    TextView new_account;
    String customer_Id, user_email;
    FirebaseAuth firebaseAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference customerCollectionReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_google);
        final DatabaseHelper helper = new DatabaseHelper(this);
        helper.insert(customer_Id, user_email);
        customerCollectionReference = db.collection("Customer");
        firebaseAuth = FirebaseAuth.getInstance();
        initFCM();

        login_btn = findViewById(R.id.sign_in_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSignInIntent();
            }
        });


    }


    private void initFCM() {
// call firebase messaging and get the token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        FCMToken = task.getResult().toString();
                        //Toast.makeText(LoginActivity.this, FCMToken, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication
        // Intent from Google Firebse
        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String[] email = {user.getEmail()};
                String auth = user.getUid();
                String name = user.getDisplayName();


                Log.d("MAIN", name);
                Log.d("MAIN", email[0]);

                customerCollectionReference.whereEqualTo("cus_email", email[0]).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<Customer> customerList = queryDocumentSnapshots.toObjects(Customer.class);

                        if (customerList.size() > 0) {
                            DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                            customer_Id = doc.getId();
                            Customer customer = doc.toObject(Customer.class);
                            user_email = customer.getCus_email();

                            updateFCMToken(customer_Id);
                            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            Toast.makeText(LoginActivity.this, "Sing in ok", Toast.LENGTH_LONG).show();
                            homeIntent.putExtra("auth_name", customer.getCus_name() + "");
                            homeIntent.putExtra("auth_email", customer.getCus_email() + "");
                            homeIntent.putExtra("userDocId", customer_Id);
                            Log.d("Home..............", "" + customer_Id);
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(homeIntent);
                        } else {
                            Intent regiIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                            startActivity(regiIntent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    // [END auth_fui_result]
    private void updateFCMToken(String customer_id) {
        Log.d("Token----------", "Customer Id : " + customer_id);
        db.collection("Customer").document(customer_id).update("fcmId", FCMToken).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("token----------------------------------", "FCM Token Updated");
                    }
                });
    }

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_signout]
    }

    public void delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_delete]
    }


    public void themeAndLogo() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();

        // [START auth_fui_theme_logo]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        // .setLogo(R.drawable.logo)      // Set logo drawable
                        // .setTheme(R.style.MySuperAppTheme)      // Set theme
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_theme_logo]
    }

    public void privacyAndTerms() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();
        // [START auth_fui_pp_tos]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_pp_tos]
    }
}