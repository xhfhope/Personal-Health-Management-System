package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddFood extends AppCompatActivity {

    Button btnSave, btnCancel;
    TextInputLayout foodName, foodCount;
    DatabaseReference db_ref;
    private static final String TAG = "AddFood";
    Food food;
    long maxid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        getSupportActionBar().hide();
        //============================================
        // Grab fields from XML
        //============================================
        foodName = findViewById(R.id.foodName);
        foodCount = findViewById(R.id.foodCount);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        //============================================
        // NOTE CLASS INSTANTIATION, DB INITIALIZATION
        //============================================
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d", Locale.US);
        String time = df.format(new Date());
        Log.d(TAG, "phms_time: "+time);


        String uid =FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user's id
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Food").child(time); //refer to /Users/<uid>/Notes
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
        food = new Food();

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!foodName.getEditText().getText().toString().matches("")
                && foodCount.getEditText().getText().toString().matches("\\d+")) {
                    food.setname(foodName.getEditText().getText().toString());
                    food.setcalories(foodCount.getEditText().getText().toString());
                    db_ref.child(String.valueOf(maxid + 1)).setValue(food);
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