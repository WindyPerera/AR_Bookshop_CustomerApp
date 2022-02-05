package com.lk.onlinebookshopcustomer.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lk.onlinebookshopcustomer.Model.Invoice;
import com.lk.onlinebookshopcustomer.OptionsMenuActivity;
import com.lk.onlinebookshopcustomer.R;

import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.Item;
import lk.payhere.androidsdk.model.StatusResponse;


public class PaymentActivity extends OptionsMenuActivity {

    private int PAYHERE_REQUEST = 100;
    double total;
    String subtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

//        Bundle bundle = new Bundle();
//        subtotal = bundle.getString("total");
//        total = Double.valueOf(subtotal+".0");


        InitRequest request = new InitRequest();
        request.setMerchantId("1214251");       // Your Merchant PayHere ID
        request.setMerchantSecret("4JH7Ak1hIGM8bStUwXrZuY4uXIlH9Dhzh8gjJmW06M6K"); // Your Merchant secret (Add your app at Settings > Domains & Credentials, to get this))
        request.setCurrency("LKR");             // Currency code LKR/USD/GBP/EUR/AUD
        request.setAmount(1000.0);             // Final Amount to be charged
        request.setOrderId("5465456433");        // Unique Reference ID
        request.setItemsDescription("Door bell wireless");  // Item description title
        request.setCustom1("This is the custom message 1");
        request.setCustom2("This is the custom message 2");
        request.getCustomer().setFirstName("Saman");
        request.getCustomer().setLastName("Perera");
        request.getCustomer().setEmail("samanp@gmail.com");
        request.getCustomer().setPhone("+94771234567");
        request.getCustomer().getAddress().setAddress("No.1, Galle Road");
        request.getCustomer().getAddress().setCity("Colombo");
        request.getCustomer().getAddress().setCountry("Sri Lanka");

//Optional Params
        request.getCustomer().getDeliveryAddress().setAddress("No.2, Kandy Road");
        request.getCustomer().getDeliveryAddress().setCity("Kadawatha");
        request.getCustomer().getDeliveryAddress().setCountry("Sri Lanka");
        request.getItems().add(new Item(null, "Door bell wireless", 1, 1000.0));

        Intent intent = new Intent(this, PHMainActivity.class);
        intent.putExtra(PHConstants.INTENT_EXTRA_DATA, request);
        PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
        startActivityForResult(intent, PAYHERE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            if (resultCode == Activity.RESULT_OK) {
                String msg;
                if (response != null) {
                    if (response.isSuccess()){

                        Invoice invoice=new Invoice();

                        msg = "Activity result:" + response.getData().toString();

                    } else{

                        msg = "Result:" + response.toString();
                    }
                } else{

                    msg = "Result: no response";
                }

//                        order save


//                Log.d(TAG, msg);
//                payment_text.setText(msg);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (response != null) {
                    Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
//                    payment_text.setText(response.toString());


                } else {
                    Toast.makeText(this, "User canceled the request", Toast.LENGTH_SHORT).show();
//                    payment_text.setText("User canceled the request");
                }
            }
        }

    }
}
