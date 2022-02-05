package com.lk.onlinebookshopcustomer.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lk.onlinebookshopcustomer.Model.Customer;
import com.lk.onlinebookshopcustomer.R;
import com.squareup.picasso.Picasso;


public class RegisterActivity extends AppCompatActivity {

    Button registerButton,selectProfile_btn;
    private int FILE_CHOOSE_ACTIVITY_RESULT_CODE = 1;
    String FCMToken = null;

    EditText nameField, emailField, mobileField, addressField,passwordField;
    ImageView customerImage;


    public StorageReference storageReference;
    FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
     Uri customerUri;
     String customerImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        storageReference = FirebaseStorage.getInstance().getReference();
       // initFCM();

        nameField = findViewById(R.id.customer_name);
        addressField = findViewById(R.id.customer_address);
        emailField = findViewById(R.id.customer_email);
        mobileField = findViewById(R.id.cus_mobile_no);
        passwordField = findViewById(R.id.password_text);

        customerImage = findViewById(R.id.customer_pic);
        selectProfile_btn =findViewById(R.id.image_select_btn);
        registerButton = findViewById(R.id.register_btn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validation
                if(TextUtils.isEmpty(nameField.getText().toString())){
                    nameField.setError("Name is Required.");
                    return;
                }if(TextUtils.isEmpty(addressField.getText().toString())){
                    addressField.setError("Address is Required.");
                    return;
                }if(TextUtils.isEmpty(emailField.getText().toString())){
                    emailField.setError("Email is Required.");
                    return;
                }if(TextUtils.isEmpty(mobileField.getText().toString())){
                    mobileField.setError("Mobile is Required.");
                    return;
                }if(mobileField.length()<10){
                    passwordField.setError("Mobile No Must be >= 10 Characters");
                }if(passwordField.length()>10){
                    passwordField.setError("Please Check Mobile No ");
                }if(TextUtils.isEmpty(passwordField.getText().toString())){
                    passwordField.setError("Password is Required.");
                }if(passwordField.length()<6){
                    passwordField.setError("Password Must be >= 6 Characters");
                }

                Customer customer = new Customer();
                customer.setCus_name(nameField.getText().toString());
                customer.setCus_address(addressField.getText().toString());
                customer.setCus_email(emailField.getText().toString());
                customer.setCus_mobileNo(mobileField.getText().toString());
                customer.setPassword(passwordField.getText().toString());

                 customerImagePath = "CustomerImage_"+nameField.getText().toString()+".png";
                customer.setCus_picPath(customerImagePath);

//                FirebaseUser user = auth.getCurrentUser();
//                String customerID =user.getUid();
//                Log.d("customerID=============================================", customerID);

                storageReference.child("CustomerImages/"+customerImagePath).
                        putFile(customerUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(RegisterActivity.this," Photo Uploaded",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this," Photo Upload Error",Toast.LENGTH_SHORT).show();
                    }
                });





                db.collection("Customer").add(customer).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

//                        String customerList = documentReference.getId();
//                        String doc =documentReference.getId();
                        Intent custumerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        custumerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(custumerIntent);
                    }
                }).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
//                        DocumentReference documentSnapshot = task.;

                        Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Save Error!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        selectProfile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileChooser = new Intent();
                fileChooser.setAction(Intent.ACTION_GET_CONTENT);
                fileChooser.setType("image/*");
                startActivityForResult(Intent.createChooser(fileChooser, "Select Your Photo"), FILE_CHOOSE_ACTIVITY_RESULT_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_CHOOSE_ACTIVITY_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                customerUri = data.getData();
                Picasso.with(RegisterActivity.this).load(customerUri).into(customerImage);
                Log.d("URI----------------", String.valueOf(customerUri));

            } else {
                Toast.makeText(this, "File Not Selected", Toast.LENGTH_SHORT).show();
            }
        }
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
}


