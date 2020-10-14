package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewMedications extends AppCompatActivity {
    Button btnBack;
    private static final String TAG = "Medications";
    long maxid;
    DatabaseReference db_ref, db_ref2;
    ListView listview;
    String[] ListElements = new String[] {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medications);
        getSupportActionBar().hide();
        //============================================
        // VARIABLES
        //============================================
        btnBack = (Button)findViewById(R.id.btnBack);
        listview = (ListView) findViewById(R.id.listView1);
        final List< String > ListElementsArrayList = new ArrayList< String >
                (Arrays.asList(ListElements));
        final ArrayAdapter< String > adapter = new ArrayAdapter < String >
                (ViewMedications.this, android.R.layout.simple_list_item_1,
                        ListElementsArrayList);
        listview.setAdapter(adapter);

        //============================================
        // DATABASE
        //============================================
        String uid =FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user's id
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Medications"); //refer to /Users/<uid>/Medications
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    maxid=snapshot.getChildrenCount();
                    for(long i=maxid; i>=0; i--){
                        db_ref2=db_ref.child(String.valueOf(i));
                        db_ref2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                if(snapshot2.exists()){
                                    String fDosage = (snapshot2.child("dosage").getValue().toString());
                                    String fName = (snapshot2.child("name").getValue().toString());
                                    String fFrequency = (snapshot2.child("frequency").getValue().toString());
                                    ListElementsArrayList.add(fName + "  -  " + fDosage + ", " + fFrequency);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        //============================================
        // Back BUTTON ONCLICK
        //============================================
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Health.class);
                startActivity(intent);
            }
        });

    }
}