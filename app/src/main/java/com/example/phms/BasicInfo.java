package com.example.phms;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BasicInfo extends AppCompatActivity {

    Button regNextBtn;
    TextInputLayout regName, regPhoneNumber, regGender, regWeight, regHeight, regCalorieGoal;
    DatabaseReference db_ref;
    private static final String TAG = "BasicInfo";
    User user;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        uid = getIntent().getStringExtra("uid");
        Button reg_nextBtn;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        getSupportActionBar().hide();
        //============================================
        // Grab fields from XML
        //============================================
        regName = findViewById(R.id.reg_name);
        regPhoneNumber = findViewById(R.id.reg_phoneNumber);
        regGender = findViewById(R.id.reg_gender);
        regWeight = findViewById(R.id.reg_weight);
        regHeight = findViewById(R.id.reg_height);
        regCalorieGoal = findViewById(R.id.reg_calorieGoal);
        regNextBtn = findViewById(R.id.reg_nextBtn);
        //============================================
        // USER CLASS INSTANTIATION, DB INITIALIZATION
        //============================================
        user = new User();
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users");

        //============================================
        // WHEN USER CLICKS "NEXT"
        //============================================
        regNextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!regName.getEditText().getText().toString().matches("")
                && !regPhoneNumber.getEditText().getText().toString().matches("")
                && !regGender.getEditText().getText().toString().matches("")
                && regWeight.getEditText().getText().toString().matches("\\d+")
                && regHeight.getEditText().getText().toString().matches("\\d+")
                && regCalorieGoal.getEditText().getText().toString().matches("\\d+")) {


                    //Get all the values
                    String name = regName.getEditText().getText().toString();
                    String phoneNumber = regPhoneNumber.getEditText().getText().toString();
                    String gender = regGender.getEditText().getText().toString();
                    int weight = Integer.parseInt(regWeight.getEditText().getText().toString());
                    int height = Integer.parseInt(regHeight.getEditText().getText().toString());
                    int calorieGoal = Integer.parseInt(regCalorieGoal.getEditText().getText().toString());
                    user.setName(name);
                    user.setPhoneNumber(phoneNumber);
                    user.setGender(gender);
                    user.setWeight(weight);
                    user.setHeight(height);
                    user.setCalorieGoal(calorieGoal);

                    //============================================
                    // SPECIFY NAME OF CHILD NODE OF USER CLASS THAT WE ARE GOING TO ADD
                    //============================================
                    db_ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                    //upload to db
                    Log.d(TAG, "DB Upload executed");

                    Intent intent = new Intent(view.getContext(), Home.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(BasicInfo.this, "Input is invalid!",
                            Toast.LENGTH_SHORT).show();
            }
        });
    }
}