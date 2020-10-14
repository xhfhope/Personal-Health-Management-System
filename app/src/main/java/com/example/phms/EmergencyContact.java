package com.example.phms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmergencyContact extends AppCompatActivity {
    Button btnSave, btnCancel;
    TextInputLayout name, email, phone;
    DatabaseReference db_ref;
    private static final String TAG = "AddEmergencyContact";
    long maxid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emergencycontact);
        getSupportActionBar().hide();
        //============================================
        // Grab fields from XML
        //============================================
        name = findViewById(R.id.em_name);
        final EditText editName = (EditText) name.getEditText();
        email = findViewById(R.id.em_email);
        final EditText editEmail = (EditText) email.getEditText();
        phone = findViewById(R.id.em_phoneNumber);
        final EditText editPhone = (EditText) phone.getEditText();
        btnSave = (Button)findViewById(R.id.btnSave);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        //============================================
        // DB INITIALIZATION
        //============================================
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Emergency");

        //============================================
        // GRAB DB VALUES AND FILL OUT FORM
        //============================================
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    editName.setText(snapshot.child("name").getValue().toString());
                    editEmail.setText(snapshot.child("email").getValue().toString());
                    editPhone.setText(snapshot.child("phoneNumber").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //============================================
        // WHEN USER CLICKS "Save"
        //============================================
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!name.getEditText().getText().toString().matches(""))
                    db_ref.child("name").setValue(name.getEditText().getText().toString());
                if(!email.getEditText().getText().toString().matches(""))
                    db_ref.child("email").setValue(email.getEditText().getText().toString());
                if(!phone.getEditText().getText().toString().matches(""))
                    db_ref.child("phoneNumber").setValue(phone.getEditText().getText().toString());


                Intent intent = new Intent(view.getContext(), Home.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Home.class);
                startActivity(intent);
            }
        });


    }
}
