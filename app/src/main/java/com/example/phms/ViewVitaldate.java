package com.example.phms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewVitaldate extends AppCompatActivity {
    DatePicker picker;
    Button btnBack, btnView;
    DatabaseReference db_ref;
    private static final String TAG = "ViewFood";
    long maxid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vital_date);
        getSupportActionBar().hide();

        //============================================
        // Grab fields from XML
        //============================================

        btnView = (Button)findViewById(R.id.btnView);
        btnBack = (Button)findViewById(R.id.btnBack);
        picker=(DatePicker)findViewById(R.id.datePicker1);
        btnView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String date = picker.getYear() + "-" + (picker.getMonth()+1) + "-" + picker.getDayOfMonth();
                Log.d(TAG, "phms_viewvitaldate: "+date);

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user's id
                db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("VitalSign").child(date); //refer to /Users/<uid>/Notes
                db_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                            maxid=snapshot.getChildrenCount(); //make a snapshot, grab the "children count" which means the number of children of this users Notes (# of notes)
                        Log.d(TAG, "phms_viewvitaldate maxid: "+ maxid);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Intent intent = new Intent(view.getContext(), ViewVitalsign.class);
                intent.putExtra("DATE", date);
                startActivity(intent);
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Health.class);
                startActivity(intent);
            }
        });


    }
}
