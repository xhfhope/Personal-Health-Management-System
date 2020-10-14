package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddMedication extends AppCompatActivity {

    Button btnSave, btnCancel;
    TextInputLayout medicationDosage, medicationFrequency, medicationName;
    DatabaseReference db_ref;
    private static final String TAG = "AddMedication";
    Medication medication;
    long maxid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        getSupportActionBar().hide();
        //============================================
        // Grab fields from XML
        //============================================
        medicationDosage = findViewById(R.id.medicationDosage);
        medicationFrequency = findViewById(R.id.medicationFrequency);
        medicationName = findViewById(R.id.medicationName);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        //============================================
        // NOTE CLASS INSTANTIATION, DB INITIALIZATION
        //============================================
        String uid =FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user's id
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Medications"); //refer to /Users/<uid>/Medications
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid=snapshot.getChildrenCount(); //make a snapshot, grab the "children count" which means the number of children of this users Medications (# of medications)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        medication = new Medication();

        //============================================
        // WHEN USER CLICKS "NEXT"
        //============================================

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!medicationDosage.getEditText().getText().toString().matches("")
                        && !medicationFrequency.getEditText().getText().toString().matches("")
                        && !medicationName.getEditText().getText().toString().matches("")) {
                    medication.setDosage(medicationDosage.getEditText().getText().toString());
                    medication.setFrequency(medicationFrequency.getEditText().getText().toString());
                    medication.setName(medicationName.getEditText().getText().toString());
                    db_ref.child(String.valueOf(maxid + 1)).setValue(medication);

                    Intent intent = new Intent(view.getContext(), Health.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(AddMedication.this, "Input is invalid!",
                            Toast.LENGTH_SHORT).show();
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