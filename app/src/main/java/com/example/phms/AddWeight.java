package com.example.phms;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddWeight extends AppCompatActivity {

    Button btnSave, btnCancel;
    TextInputLayout weight;
    DatabaseReference db_ref;
    private static final String TAG = "AddWeight";
    int iWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);
        getSupportActionBar().hide();
        //============================================
        // Grab fields from XML
        //============================================
        weight= findViewById(R.id.weight);


        btnSave = (Button)findViewById(R.id.btnSave);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        //============================================
        // NOTE CLASS INSTANTIATION, DB INITIALIZATION
        //============================================
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d", Locale.US);
        String time = df.format(new Date());

        String uid =FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user's id
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Weights").child(time);

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(weight.getEditText().getText().toString().matches("\\d+")) {
                    iWeight = Integer.parseInt(weight.getEditText().getText().toString());
                    db_ref.setValue(iWeight); //update account main weight
                    db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("weight");
                    db_ref.setValue(iWeight); //update daily weight log
                }
                Intent intent = new Intent(view.getContext(), Health.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Health.class);
                startActivity(intent);
            }
        });


    }
}