package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.widget.EditText;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewVitalsign extends AppCompatActivity {
    Button  btnDone;
    private static final String TAG = "ViewVitalsign";
    long maxid;
    DatabaseReference db_ref, db_ref2;
    String date;
    ListView listview;
    String[] ListElements = new String[] {};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vitalsign);
        getSupportActionBar().hide();

        //============================================
        // VARIABLES
        //============================================
        listview = (ListView) findViewById(R.id.listView1);
        btnDone = (Button)findViewById(R.id.btnDone);
        final List< String > ListElementsArrayList = new ArrayList< String >
                (Arrays.asList(ListElements));
        final ArrayAdapter< String > adapter = new ArrayAdapter < String >
                (ViewVitalsign.this, android.R.layout.simple_list_item_1,
                        ListElementsArrayList);
        listview.setAdapter(adapter);
        date = getIntent().getExtras().getString("DATE");

        //============================================
        // DATABASE
        //============================================
        String uid =FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user's id
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("VitalSign").child(date); //refer to /Users/<uid>/Notes
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    maxid=snapshot.getChildrenCount();
                    for(int i=1; i<=maxid; i++){
                        final int finalI = i;
                        db_ref2=db_ref.child(String.valueOf(i));
                        db_ref2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                if(snapshot2.exists()){
                                    String date = (snapshot2.child("dateRecorded").getValue().toString());
                                    String type = (snapshot2.child("type").getValue().toString());
                                    String value = (snapshot2.child("value").getValue().toString());
                                    ListElementsArrayList.add("Date: "+ date +"\n" +"Type: "+type+"\n"+"Value: "+value);
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

        btnDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Home.class);
                startActivity(intent);
            }
        });
    }
}