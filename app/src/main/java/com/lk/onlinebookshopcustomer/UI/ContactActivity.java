package com.lk.onlinebookshopcustomer.UI;

import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lk.onlinebookshopcustomer.OptionsMenuActivity;
import com.lk.onlinebookshopcustomer.R;

public class ContactActivity extends OptionsMenuActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    Button chatBtn;
    TextView time, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

//        chatBtn = findViewById(R.id.chat_btn);
        time = findViewById(R.id.time_text);
        date = findViewById(R.id.date_text);

//        chatBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(ContactActivity.this, CustomizedActivity.class);
//
//                startActivity(intent);
//
//            }
//        });


    }


    public void setTime(String duration) {
        time.setText(duration);

    }

    public void setDistance(String duration) {
        time.setText(duration);

    }


}