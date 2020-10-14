package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.material.textfield.TextInputLayout;

public class Signup extends AppCompatActivity {

    //============================================
    // GRAB FIELDS FROM XML, INITIALIZE AUTHENTICATION FIREBASE MODULE
    //============================================
    TextInputLayout regEmailAddress, regPassword;
    Button regSignupBtn, regGoToLoginBtn;
    private FirebaseAuth mAuth;
    private static final String TAG = "SIGNUP";

    //============================================
    // ONCREATE() FUNCTION TRIGGERS WHEN ACTIVITY LOADS
    //============================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //This Line will hide the status bar from the screen
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        //============================================
        // AUTOMATICALLY LOGIN IF POSSIBLE
        //============================================
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(Signup.this, Home.class);
            startActivity(intent);
        }

        //============================================
        // CREATE AND RELATE JAVA VARIABLES WITH EACH XML ELEMENT
        //============================================
        regEmailAddress = findViewById(R.id.reg_emailAddress);
        regPassword = findViewById(R.id.reg_password);
        regSignupBtn = findViewById(R.id.reg_signupBtn);
        regGoToLoginBtn = findViewById(R.id.reg_goToLoginBtn);

        //============================================
        // SIGNUP BUTTON LISTENER
        //============================================
        regSignupBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //============================================
                // Could use some form validation!
                // Fine for now -> Proceed to register input in database
                //============================================
                //Get all the values
                String emailAddress = regEmailAddress.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                createAccount(emailAddress, password);
                Log.d(TAG, "phms_signInWithEmail:success");
                //signIn(emailAddress, password);

            }
        });

        //============================================
        // GO TO LOGIN PAGE BUTTON LISTENER
        //============================================
        regGoToLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Login.class);
                view.getContext().startActivity(intent);}
        });
    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void createAccount(String email, String password){
        final String em = email;
        final String pw = password;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //success, update UI with signed-in user info
                            //FirebaseUser user = mAuth.getCurrentUser();
                            /*Intent intent = new Intent(Signup.this, BasicInfo.class);
                            intent.putExtra("EMAIL", em);
                            intent.putExtra("PASSWORD", pw);
                            startActivity(intent);*/
                            mAuth.signInWithEmailAndPassword(em, pw)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {

                                                Intent intent = new Intent(Signup.this, BasicInfo.class);
                                                startActivity(intent);
                                            }
                                            else {
                                            }
                                        }
                                    });
                            Toast.makeText(Signup.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // sign in fails, dispaly fail message
                            Toast.makeText(Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}