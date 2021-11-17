package com.example.ebusuaschoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Sanele - Firebase authentication variables
    FirebaseAuth auth;
    TextView verifyMsg;
    Button verifyEmailBtn;

    // Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        // logout button - Sanele
        ImageView logout = findViewById(R.id.login_main_img); // changed from button

        //Change Status Bar Color
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.background_header_color));

        //Hooks

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        textView =findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar);

        //ToolBar


        //Navigation Drawer Menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new  ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // logout button onClick listener
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

    }

   // Make Menu Return
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // reset password event listener
        if (item.getItemId() == R.id.resetUserPassword) {
            startActivity(new Intent(getApplicationContext(),ResetPassword.class));
        }

        return super.onOptionsItemSelected(item);
    }

}