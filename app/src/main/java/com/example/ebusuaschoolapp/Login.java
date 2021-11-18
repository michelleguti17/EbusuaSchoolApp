package com.example.ebusuaschoolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    Button createAccountBtn, loginBtn;
    EditText username, password; // to extract data
    FirebaseAuth firebaseAuth;

    //Variable for the menu to show inside the login activity- Michelle
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Change Status Bar Color-Michelle
        getWindow().setStatusBarColor(ContextCompat.getColor(Login.this,R.color.background_header_color));

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


        //Hooks for the menu - Michelle

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        textView =findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar);

        //ToolBar


        //Navigation Drawer Menu-Michelle
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new  ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }
    // Make Menu Return- Michelle
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
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

    //Navigation item Menu selected - Michelle
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.nav_home:
                Intent home = new Intent(Login.this,MainActivity.class);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;

            case R.id.nav_login:
                Intent login = new Intent(Login.this,Login.class);
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();


        }

        return true;
    }
  }