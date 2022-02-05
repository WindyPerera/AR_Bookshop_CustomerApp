package com.lk.onlinebookshopcustomer.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.lk.onlinebookshopcustomer.InvoiceActivity;
import com.lk.onlinebookshopcustomer.Model.Invoice;
import com.lk.onlinebookshopcustomer.OptionsMenuActivity;
import com.lk.onlinebookshopcustomer.R;

import java.util.ArrayList;
import java.util.List;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class DeliveryDetailsActivity extends NormalOptionMenu{
    DataTable dataTable;
    DataTableHeader header;
    private ArrayList<DataTableRow> rows = new ArrayList<>();
    String customer_ID;
    TableLayout table;
    Toolbar toolbar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Order Details");
//        table = findViewById(R.id.table_layout);

        dataTable = findViewById(R.id.data_table);

        Bundle bundle = getIntent().getExtras();
        customer_ID = bundle.getString("auth_id");
        Log.d("wwwwwwwwwww",customer_ID);
        db.collection("Invoice").whereEqualTo("customerId", customer_ID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Invoice> invoiceList = queryDocumentSnapshots.toObjects(Invoice.class);
                Log.d("wwwwwwwwwww",String.valueOf(invoiceList.size()));

                header = new DataTableHeader.Builder()
                        .item("No", 5)
                        .item("Invoice ID", 4)
                        .item("Amount", 5)
                        .item("Status", 5)
//                        .item("Amount", 5)
                        .build();

                if (invoiceList.size() > 0) {
                    for (int i=0; i<invoiceList.size(); i++) {
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(i);
                        String invoice_Id = doc.getId();
                        Log.d("wwwwwwwwwww",invoice_Id);
                        Invoice invoice = doc.toObject(Invoice.class);


                        DataTableRow row = new DataTableRow.Builder()
                                .value(String.valueOf(i+1))
                                .value(invoice_Id)
                                .value("Rs:" + invoice.getTotalAmount())
                                .value(invoice.getOrderedStatus())
//                                .value("Rs:" + String.valueOf(item.getQty() * product.getPrice()))
                                .build();
                        rows.add(row);
                        dataTable.setHeader(header);
                        dataTable.setRows(rows);
                        dataTable.inflate(DeliveryDetailsActivity.this);
//                        TableRow row = new TableRow(DeliveryDetailsActivity.this);
//
//                        TextView noText = new TextView(DeliveryDetailsActivity.this);
//                        TextView invoiceId = new TextView(DeliveryDetailsActivity.this);
//                        TextView totalText = new TextView(DeliveryDetailsActivity.this);
//                        TextView statusText = new TextView(DeliveryDetailsActivity.this);
//
//                        int no = 1+i;
//                        noText.setText(String.valueOf(no));
//                        invoiceId.setText(invoice_Id);
//                        totalText.setText(String.valueOf(invoice.getTotalAmount())+"0");
//                        statusText.setText(invoice.getOrderedStatus());
//
//
//                        row.addView(noText);
//                        row.addView(invoiceId);
//                        row.addView(totalText);
//                        row.addView(statusText);
//
//                        table.addView(row);




                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("wwwwwwwwwww",e.getMessage());
            }
        });

    }
}