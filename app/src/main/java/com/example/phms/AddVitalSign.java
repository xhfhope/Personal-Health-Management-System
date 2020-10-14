package com.example.phms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddVitalSign extends AppCompatActivity {
    Button btnSave, btnCancel;
    TextInputLayout visType, visValue;
    DatabaseReference db_ref;
    private static final String TAG = "AddVitalSign";
    Vitalsign vital;
    long maxid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vitalsign);
        getSupportActionBar().hide();
        //============================================
        // Grab fields from XML
        //============================================
        visType= findViewById(R.id.vitalType);
        visValue = findViewById(R.id.vitalValue);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        //============================================
        // NOTE CLASS INSTANTIATION, DB INITIALIZATION
        //============================================
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d", Locale.US);
        final String time = df.format(new Date());
        Log.d(TAG, "phms_time: "+time);


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user's id
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("VitalSign").child(time); //refer to /Users/<uid>/Notes
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid=snapshot.getChildrenCount(); //make a snapshot, grab the "children count" which means the number of children of this users Notes (# of notes)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        vital = new Vitalsign();

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!visType.getEditText().getText().toString().matches("")
                        && visValue.getEditText().getText().toString().matches("\\d+")) {
                    vital.setType(visType.getEditText().getText().toString());
                    vital.setValue(visValue.getEditText().getText().toString());
                    vital.setDateRecorded(time.toString());
                    db_ref.child(String.valueOf(maxid + 1)).setValue(vital);
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
