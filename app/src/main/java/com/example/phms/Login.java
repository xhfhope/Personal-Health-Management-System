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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {
    private static final String TAG = "LOGIN";
    //============================================
    // GRAB FIELDS FROM XML
    //============================================
    TextInputLayout regEmailAddress, regPassword;
    Button regLoginBtn, regGoToSignupBtn;
    private FirebaseAuth mAuth;

    //============================================
    // ONCREATE() FUNCTION TRIGGERS WHEN ACTIVITY LOADS
    //============================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {




            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            getSupportActionBar().hide();
            //============================================
            // CREATE AND RELATE JAVA VARIABLES WITH EACH XML ELEMENT
            //============================================
            regEmailAddress = findViewById(R.id.reg_emailAddress);
            regPassword = findViewById(R.id.reg_password);
            regLoginBtn = findViewById(R.id.reg_loginBtn);
            regGoToSignupBtn = findViewById(R.id.reg_goToSignupBtn);

            mAuth = FirebaseAuth.getInstance();

            //============================================
            // LOGIN BUTTON LISTENER
            //============================================
            regLoginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String emailAddress = regEmailAddress.getEditText().getText().toString();
                    String password = regPassword.getEditText().getText().toString();

                    signIn(emailAddress, password);
                }
            });
            regGoToSignupBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Signup.class); //Load home activity
                    view.getContext().startActivity(intent);
                }
            });
        }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Login.this, Home.class); //Load home activity
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}