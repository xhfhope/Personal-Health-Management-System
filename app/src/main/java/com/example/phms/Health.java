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



public class Health extends AppCompatActivity {
    Button addVital, addWeight, addMedications, addFood, addCheckup, btnBack;
    Button viewVital, viewWeight, viewMedications, viewFood, viewCheckup;
    TextView headerText;
    DatabaseReference db_ref;
    private static final String TAG = "Health";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        getSupportActionBar().hide();

        //============================================
        // VARIABLES
        //============================================
        addVital = (Button)findViewById(R.id.btnAddVital);
        addWeight = (Button)findViewById(R.id.btnAddWeight);
        addMedications = (Button)findViewById(R.id.btnAddMedication);
        addFood = (Button)findViewById(R.id.btnAddFood);
        addCheckup = (Button)findViewById(R.id.btnAddCheckup);
        headerText = (TextView)findViewById(R.id.headerText);
        viewVital = (Button)findViewById(R.id.btnViewVitals);
        viewWeight = (Button)findViewById(R.id.btnViewWeights);
        viewMedications = (Button)findViewById(R.id.btnViewMedications);
        viewFood = (Button)findViewById(R.id.btnViewFood);
        viewCheckup = (Button)findViewById(R.id.btnViewCheckup);
        btnBack = (Button)findViewById(R.id.btnBack);
        headerText = (TextView)findViewById(R.id.headerText);


        //============================================
        // VITAL SIGN
        //============================================
        addVital.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), AddVitalSign.class);
                startActivity(intent);
            }
        });
        viewVital.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), ViewVitaldate.class);
                startActivity(intent);
            }
        });
        //============================================
        // FOOD
        //============================================
        addFood.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), AddFood.class);
                startActivity(intent);
            }
        });
        viewFood.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), ViewFood.class);
                startActivity(intent);
            }
        });

        //============================================
        // CHECKUP
        //============================================
        addCheckup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), AddCheckup.class);
                startActivity(intent);
            }
        });
        viewCheckup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), ViewCheckups.class);
                startActivity(intent);
            }
        });

        //============================================
        // MEDICATION
        //============================================
        addMedications.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), AddMedication.class);
                startActivity(intent);
            }
        });
        viewMedications.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), ViewMedications.class);
                startActivity(intent);
            }
        });

        //============================================
        // WEIGHT
        //============================================
        addWeight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), AddWeight.class);
                startActivity(intent);
            }
        });
        viewWeight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), ViewWeight.class);
                startActivity(intent);
            }
        });

        //============================================
        // BACK
        //============================================
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Home.class);
                startActivity(intent);
            }
        });

    }
}