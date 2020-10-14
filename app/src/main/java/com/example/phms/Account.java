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



public class Account extends AppCompatActivity {
    //============================================
    // VARIABLES
    //============================================
    Button regUpdateBtn, btnCancel;
    TextInputLayout regName, regPhoneNumber, regGender, regWeight, regHeight, regCalorieGoal;
    DatabaseReference db_ref;
    private static final String TAG = "Account";
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().hide();


        //============================================
        // Grab fields from XML
        //============================================
        regName = findViewById(R.id.reg_name);
        final EditText editName = (EditText) regName.getEditText();

        regPhoneNumber = findViewById(R.id.reg_phoneNumber);
        final EditText editPhoneNumber = (EditText) regPhoneNumber.getEditText();

        regGender = findViewById(R.id.reg_gender);
        final EditText editGender = (EditText) regGender.getEditText();

        regWeight = findViewById(R.id.reg_weight);
        final EditText editWeight = (EditText) regWeight.getEditText();

        regHeight = findViewById(R.id.reg_height);
        final EditText editHeight = (EditText) regHeight.getEditText();

        regCalorieGoal = findViewById(R.id.reg_calorieGoal);
        final EditText editCalorieGoal = (EditText) regCalorieGoal.getEditText();

        regUpdateBtn = findViewById(R.id.reg_updateBtn);
        btnCancel = findViewById(R.id.btnCancel);

        //============================================
        // DB INITIALIZATION
        //============================================
        String uid =FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user's id
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);


        //============================================
        // GRAB DB VALUES AND FILL OUT FORM
        //============================================
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    editName.setText(snapshot.child("name").getValue().toString());
                    editPhoneNumber.setText(snapshot.child("phoneNumber").getValue().toString());
                    editGender.setText(snapshot.child("gender").getValue().toString());
                    editWeight.setText(snapshot.child("weight").getValue().toString());
                    editHeight.setText(snapshot.child("height").getValue().toString());
                    editCalorieGoal.setText(snapshot.child("calorieGoal").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //============================================
        // WHEN USER CLICKS "NEXT"
        //============================================
        regUpdateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //============================================
                // Could use some form validation!
                // Fine for now -> Proceed to register input in database
                //============================================
                //Get all the values
                String name = "", phoneNumber="", gender="";
                int weight=0, height=0, calorieGoal=0;

                if(!regName.getEditText().getText().toString().matches("")) {
                    name = regName.getEditText().getText().toString();
                    db_ref.child("name").setValue(name);
                }

                if(!regPhoneNumber.getEditText().getText().toString().matches("")) {
                    phoneNumber = regPhoneNumber.getEditText().getText().toString();
                    db_ref.child("phoneNumber").setValue(phoneNumber);
                }

                if(!regGender.getEditText().getText().toString().matches("")) {
                    gender = regGender.getEditText().getText().toString();
                    db_ref.child("gender").setValue(gender);
                }

                if(regWeight.getEditText().getText().toString().matches("\\d+")) {
                    weight = Integer.parseInt(regWeight.getEditText().getText().toString());
                    db_ref.child("weight").setValue(weight);
                }

                if(regHeight.getEditText().getText().toString().matches("\\d+")) {
                    height = Integer.parseInt(regHeight.getEditText().getText().toString());
                    db_ref.child("height").setValue(height);
                }

                if(regCalorieGoal.getEditText().getText().toString().matches("\\d+")) {
                    calorieGoal = Integer.parseInt(regCalorieGoal.getEditText().getText().toString());
                    db_ref.child("calorieGoal").setValue(calorieGoal);
                }

                //upload to db
                Log.d(TAG, "DB Update executed");

                Intent intent = new Intent(view.getContext(), Home.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Home.class);
                startActivity(intent);
            }
        });

    }
}