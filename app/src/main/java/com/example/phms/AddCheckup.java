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

public class AddCheckup extends AppCompatActivity {

    Button btnSave, btnCancel;
    TextInputLayout checkupDoctor, checkupDate, checkupContents;
    DatabaseReference db_ref;
    private static final String TAG = "AddCheckup";
    Checkup checkup;
    long maxid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_checkup);
        getSupportActionBar().hide();
        //============================================
        // Grab fields from XML
        //============================================
        checkupDoctor = findViewById(R.id.checkupDoctor);
        checkupDate = findViewById(R.id.checkupDate);
        checkupContents = findViewById(R.id.checkupContents);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        //============================================
        // NOTE CLASS INSTANTIATION, DB INITIALIZATION
        //============================================
        String uid =FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user's id
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Checkups"); //refer to /Users/<uid>/Checkups
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid=snapshot.getChildrenCount(); //make a snapshot, grab the "children count" which means the number of children of this users Checkups (# of checkups)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        checkup = new Checkup();

        //============================================
        // WHEN USER CLICKS "NEXT"
        //============================================

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!checkupDoctor.getEditText().getText().toString().matches("")
                        && !checkupDate.getEditText().getText().toString().matches("")
                        && !checkupContents.getEditText().getText().toString().matches("")) {
                    checkup.setDoctor(checkupDoctor.getEditText().getText().toString());
                    checkup.setDate(checkupDate.getEditText().getText().toString());
                    checkup.setContents(checkupContents.getEditText().getText().toString());
                    db_ref.child(String.valueOf(maxid + 1)).setValue(checkup);

                    Intent intent = new Intent(view.getContext(), Health.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(AddCheckup.this, "Input is invalid!",
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