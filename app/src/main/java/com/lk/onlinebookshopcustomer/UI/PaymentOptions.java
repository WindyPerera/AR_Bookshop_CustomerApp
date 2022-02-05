package com.lk.onlinebookshopcustomer.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.lk.onlinebookshopcustomer.InvoiceActivity;
import com.lk.onlinebookshopcustomer.Model.Cart;
import com.lk.onlinebookshopcustomer.Model.Invoice;
import com.lk.onlinebookshopcustomer.Model.InvoiceItem;
import com.lk.onlinebookshopcustomer.Model.Product;
import com.lk.onlinebookshopcustomer.R;
import com.smarteist.autoimageslider.Transformations.TossTransformation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.Item;
import lk.payhere.androidsdk.model.StatusResponse;

public class PaymentOptions extends NormalOptionMenu {

    Toolbar toolbar;
    Button checkOut_btn;
    private static final int PAYHERE_REQUEST = 1001;
    LinearLayout online_pay, cash_on_pay;
    String  customerID, cartID;
    TextView subTotal, paymentType;
    String name, address, email, mobileNo;
    String sub_total, post_Code;
    String saveCurrentDate, saveCurrentTime;

    EditText nameText, addresstext, postCodeText, mobileText, emailText;
    RadioButton on_pay, cashOn_pay;
    RadioGroup pay_type;
    String pay_method;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chech Out");


        Bundle bundle = getIntent().getExtras();
        sub_total = bundle.getString("subTotal");
        customerID = bundle.getString("userDocID");
        Log.d("pppppppppppppp",customerID);
        cartID = bundle.getString("cartID");

        subTotal = findViewById(R.id.subTotal);
        subTotal.setText(sub_total);

        nameText = findViewById(R.id.text_name);
        addresstext = findViewById(R.id.text_address);
        postCodeText = findViewById(R.id.postCode);
        mobileText = findViewById(R.id.text_mobile);
        emailText = findViewById(R.id.email_text);
        paymentType = findViewById(R.id.pay_type);

        pay_type = (RadioGroup) findViewById(R.id.payment_method);
        on_pay = (RadioButton) findViewById(R.id.online_pay);
        cashOn_pay = (RadioButton) findViewById(R.id.cashon_pay);

        pay_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.online_pay:
                        pay_method = "online";
                        paymentType.setText("Your Selected Payement Method is" + on_pay.getText().toString());
                        break;
                    case R.id.cashon_pay:
                        pay_method = "cashOn";
                        paymentType.setText("Your Selected Payement Method is" + cashOn_pay.getText().toString());
                        break;
                }
            }
        });


        checkOut_btn = findViewById(R.id.check_out_btn);
        checkOut_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pay_method == "online") {

                    getPayHere();
                } else if (pay_method=="cashOn") {
                    AlertDialog.Builder alert = new AlertDialog.Builder(PaymentOptions.this);
                    alert.setMessage("Your Order is Complete. In 3 days you can get it")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PaymentOptions.this,HomeActivity.class);
                                    intent.putExtra("userDocId",customerID);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                    AlertDialog alert1 = alert.create();
                    alert1.show();
                }
            }
        });
    }
    public void getPayHere() {
        InitRequest request = new InitRequest();
        request.setMerchantId("1214251");       // Your Merchant PayHere ID
        request.setMerchantSecret("4UptygYT3yb8bQZ03TAR4j8LKVskeQRK04DtREJmHDza"); // Your Merchant secret (Add your app at Settings > Domains & Credentials, to get this))
        request.setCurrency("LKR");             // Currency code LKR/USD/GBP/EUR/AUD
        request.setAmount(Double.parseDouble(subTotal.getText().toString()));             // Final Amount to be charged
        request.setOrderId("invoiceId");        // Unique Reference ID
        request.setItemsDescription("AR Book Shop Customer Payment");  // Item description title
        request.setCustom1("This is the custom message 1");
        request.setCustom2("This is the custom message 2");
        request.getCustomer().setFirstName(auth.getCurrentUser().getDisplayName());
        request.getCustomer().setLastName("");
        request.getCustomer().setEmail(auth.getCurrentUser().getEmail());
        request.getCustomer().setPhone(auth.getCurrentUser().getPhoneNumber());
        request.getCustomer().getAddress().setAddress(addresstext.getText().toString());
        request.getCustomer().getAddress().setCity("Gampaha District");
        request.getCustomer().getAddress().setCountry("Sri Lanka");


        Intent intent = new Intent(PaymentOptions.this, PHMainActivity.class);
        intent.putExtra(PHConstants.INTENT_EXTRA_DATA, request);
        PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
        startActivityForResult(intent, PAYHERE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            if (resultCode == Activity.RESULT_OK) {
                String msg;
                if (response != null) {
                    if (response.isSuccess()) {

                        Calendar calForDate = Calendar.getInstance();
                        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                        saveCurrentDate = currentDate.format(calForDate.getTime());

                        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                        saveCurrentTime = currentTime.format(calForDate.getTime());

                        Invoice invoice = new Invoice();
                        Log.d("complete", ""+customerID);

                        invoice.setCustomerId(customerID);
                        invoice.setTotalAmount(Double.valueOf(sub_total));
                        invoice.setCurrentDate(saveCurrentDate);
                        invoice.setCurrentTime(saveCurrentTime);
                        invoice.setRecipientName(nameText.getText().toString());
                        invoice.setAddress(addresstext.getText().toString());
                        invoice.setPostCode(Integer.parseInt(postCodeText.getText().toString()));
                        invoice.setPaymentType(pay_method);
                        invoice.setOrderedStatus("pending");

                        db.collection("Invoice").add(invoice).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                String invoiceID = documentReference.getId();

                                Log.d("auth",auth.getCurrentUser().getUid());
                               db.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("UserCart")
                                       .whereEqualTo("customerId",customerID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                   @Override
                                   public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                       Log.d("complete", ""+customerID);
                                       List<Cart> cartList = queryDocumentSnapshots.toObjects(Cart.class);

                                       Log.d("complete", String.valueOf(cartList.size()));
                                       if(cartList.size()>0){
// for(){}
//                                           DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
//                                           db.collection("InvoiceItem").add().addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                               @Override
//                                               public void onSuccess(DocumentReference documentReference) {
//
//                                               }
//                                           }).addOnFailureListener(new OnFailureListener() {
//                                               @Override
//                                               public void onFailure(@NonNull Exception e) {
//
//                                               }
//                                           });
                                       }
                                   }
                               });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PaymentOptions.this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
//                        nameText.setText("");
//                        addresstext.setText("");
//                        postCodeText.setText("");
//                        addresstext.setText("");
//                        mobileText.setText("");
//                        emailText.setText("");
//                        on_pay.setChecked(false);
//                        cashOn_pay.setChecked(false);
//
//                        Intent invoice = new Intent(PaymentOptions.this, InvoiceActivity.class);
//                        invoice.putExtra("invoiceId", invoiceID);
//                        startActivity(invoice);
//                        Toast.makeText(this, "Payment Success", Toast.LENGTH_LONG).show();
                    } else {
                        msg = "Result:" + response.toString();
                    }
                } else {
                    msg = "Result: no Response";
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (response != null) {
                    Toast.makeText(this, "Payment Not Success", Toast.LENGTH_SHORT).show();
//                    payment_text.setText(response.toString());


                } else {
                    Toast.makeText(this, "User canceled the request", Toast.LENGTH_SHORT).show();
//                    payment_text.setText("User canceled the request");
                }
            }
        }


    }
}