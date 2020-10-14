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

public class ViewWeight extends AppCompatActivity {
    Button  btnDone;
    private static final String TAG = "ViewWeights";
    long maxid;
    DatabaseReference db_ref, db_ref2;
    ListView listview;
    EditText GetValue;
    String[] ListElements = new String[] {};
    int totalCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_weight);
        getSupportActionBar().hide();
        totalCal = 0;
        listview = (ListView) findViewById(R.id.listView1);
        btnDone = (Button)findViewById(R.id.btnDone);
        final List< String > ListElementsArrayList = new ArrayList< String >
                (Arrays.asList(ListElements));
        final ArrayAdapter< String > adapter = new ArrayAdapter < String >
                (ViewWeight.this, android.R.layout.simple_list_item_1,
                        ListElementsArrayList);
        listview.setAdapter(adapter);
        String uid =FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user's id
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Weights"); //refer to /Users/<uid>/Notes

        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List weights = new ArrayList();
                        List dates = new ArrayList();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String weight = snapshot.getValue().toString();
                            String date = snapshot.getKey().toString();
                            weights.add(weight);
                            dates.add(date);
                        }
                        for(int i=weights.size()-1; i>=0; i--){
                            ListElementsArrayList.add(dates.get(i) + "\t\t\t\t\t\t" + weights.get(i));
                            adapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        btnDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Health.class);
                startActivity(intent);
            }
        });
    }
}