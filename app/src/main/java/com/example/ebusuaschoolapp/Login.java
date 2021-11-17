package com.example.ebusuaschoolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button createAccountBtn, loginBtn, forgotPasswordBtn;
    EditText username, password; // to extract data
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance(); // initialize
//        Handle onClick event when create button is clicked
        createAccountBtn = findViewById(R.id.createAccountBtn);
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        username = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginbtn);

        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start alert dialog (when user clicks on the button
                AlertDialog.Builder reset_alert = new AlertDialog.Builder(getApplicationContext());
                reset_alert.setTitle("Forgot Password? Click continue to reset.")
                        .setMessage("Enter your email to receive password reset link.")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // validate email address
                                // send reset link

                            }
                        }).setNegativeButton("Cancel", null)
                        .create().show();

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // extract and validate data

                // if fields are empty
                if (username.getText().toString().isEmpty()) {
                    username.setError("Email is missing.");
                    return;
                }
                if (password.getText().toString().isEmpty()) {
                    password.setError("Password is missing.");
                    return;
                }
                // data is valid
                // login user
                firebaseAuth.signInWithEmailAndPassword(username.getText().toString(),
                        password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // Login is success
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // check firebase authentication
    // if user had previously created account, then app will go straight to the menu
    // the next time they open it
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
}