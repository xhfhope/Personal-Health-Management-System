package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
Button btnAccount, btnDoctor, btnHealth, btnNotes, btnLogout, btnEmergencecontact;
TextView welcomeText;
DatabaseReference db_ref;
private static final String TAG = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "phms_uid in Home: "+FirebaseAuth.getInstance().getCurrentUser().getUid());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        //============================================
        // VARIABLES
        //============================================
        btnAccount = (Button)findViewById(R.id.btnAccount);
        btnDoctor = (Button)findViewById(R.id.btnDoctor);
        btnHealth = (Button)findViewById(R.id.btnHealth);
        btnNotes = (Button)findViewById(R.id.btnNotes);
        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnEmergencecontact = (Button)findViewById(R.id.btnEmergenceContact);
        welcomeText = (TextView)findViewById(R.id.welcomeText);

        //============================================
        // LOAD NAME
        //============================================
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()); // MUCH FUNCTION ACCESSING VERY WOW
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                welcomeText.setText("Welcome, "+name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //============================================
        // ACCOUNT BUTTON ONCLICK
        //============================================
        btnAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Account.class);
                startActivity(intent);
            }
        });

        //============================================
        // DOCTOR BUTTON ONCLICK
        //============================================
        btnDoctor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), AddDoctor.class);
                startActivity(intent);
            }
        });

        //============================================
        // HEALTH BUTTON ONCLICK
        //============================================
        btnHealth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Health.class);
                startActivity(intent);
            }
        });

        //============================================
        // NOTES BUTTON ONCLICK
        //============================================
        btnNotes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Notes.class);
                startActivity(intent);
            }
        });
        //============================================
        // EMERGENCY BUTTON ONCLICK
        //============================================
        btnEmergencecontact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), EmergencyContact.class);
                startActivity(intent);
            }
        });


        //============================================
        // SIGNOUT BUTTON ONCLICK
        //============================================
        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(view.getContext(), Login.class);
                startActivity(intent);
            }
        });
    }
}