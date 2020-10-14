package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class AddDoctor extends AppCompatActivity {
    Button docbtnSave, docbtnCancel;
    TextInputLayout docName, docPhone, docCheckupInfo;
    DatabaseReference db_ref;
    private static final String TAG = "AddDoctor";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        getSupportActionBar().hide();
        //============================================
        // Grab fields from XML
        //============================================
        docName = findViewById(R.id.doc_name);
        final EditText editName = (EditText) docName.getEditText();
        docPhone = findViewById(R.id.doc_phone);
        final EditText editPhone = (EditText) docPhone.getEditText();
        docCheckupInfo = findViewById(R.id.doc_CheckupInfo);
        final EditText editCheckup = (EditText) docCheckupInfo.getEditText();
        docbtnSave = (Button)findViewById(R.id.btnSave);
        docbtnCancel = (Button)findViewById(R.id.btnCancel);
        //============================================
        // DB INITIALIZATION
        //============================================
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Doctor");


        //============================================
        // GRAB DB VALUES AND FILL OUT FORM
        //============================================
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    editName.setText(snapshot.child("name").getValue().toString());
                    editPhone.setText(snapshot.child("date").getValue().toString());
                    editCheckup.setText(snapshot.child("checkInfo").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //============================================
        // WHEN USER CLICKS "Save"
        //============================================
        docbtnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(!docName.getEditText().getText().toString().matches(""))
                    db_ref.child("name").setValue(docName.getEditText().getText().toString());
                if(!docPhone.getEditText().getText().toString().matches(""))
                    db_ref.child("date").setValue(docPhone.getEditText().getText().toString());
                if(!docCheckupInfo.getEditText().getText().toString().matches(""))
                    db_ref.child("checkInfo").setValue(docCheckupInfo.getEditText().getText().toString());

                Intent intent = new Intent(view.getContext(), Home.class);
                startActivity(intent);
            }
        });

        docbtnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Home.class);
                startActivity(intent);
            }
        });

    }
    }