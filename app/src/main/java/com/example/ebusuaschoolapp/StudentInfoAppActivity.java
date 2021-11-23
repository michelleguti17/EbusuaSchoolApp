package com.example.ebusuaschoolapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

 class StudentInfoAppActivity extends AppCompatActivity {
    EditText editRollno;
    EditText editName;
    EditText editMarks;
    Button btnAdd;
    Button btnDelete;
    Button btnModify;
    Button btnView;
    Button btnViewAll;
    Button btnShowInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info_app);

        // Change Status Bar Color-Michelle
        getWindow().setStatusBarColor(ContextCompat.getColor(StudentInfoAppActivity.this, R.color.background_header_color));

        // Initializing controls

        editRollno = (EditText) findViewById(R.id.e1);
        editName = (EditText) findViewById(R.id.e2);
        editMarks = (EditText) findViewById(R.id.e3);
        btnAdd = (Button) findViewById(R.id.b1);
        btnDelete = (Button) findViewById(R.id.b2);
        btnModify = (Button) findViewById(R.id.b3);
        btnView = (Button) findViewById(R.id.b4);
        btnViewAll = (Button) findViewById(R.id.b5);
        btnShowInfo = (Button) findViewById(R.id.b6);

        // Creating database and table

        final SQLiteDatabase db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,marks VARCHAR);");


// Registering event handlers

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Checking empty fields
                if (editRollno.getText().toString().trim().length() == 0 || editName.getText().toString().trim().length() == 0 || editMarks.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter all values");
                    return;
                }
                // Inserting record
                db.execSQL("INSERT INTO student VALUES('" + editRollno.getText() + "','" +
                        editName.getText() + "','" + editMarks.getText() + "');");
                showMessage("Success", "Record added");
                clearText();
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Checking empty roll number
                if (editRollno.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                // Searching roll number
                Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + editRollno.getText() + "'", null);
                if (c.moveToFirst()) {
                    editName.setText(c.getString(1));
                    editMarks.setText(c.getString(2));
                } else {
                    showMessage("Error", "Invalid Rollno");
                    clearText();
                }


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // Checking empty roll number
                if (editRollno.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Enter Rollno");
                    return;
                }
                // Searching roll number
                Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + editRollno.getText() + "'", null);
                if (c.moveToFirst()) {
                    // Deleting record if found
                    db.execSQL("DELETE FROM student WHERE rollno='" + editRollno.getText() + "'");
                    showMessage("Success", "Record Deleted");

                } else {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();

            }


        });
        btnModify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (editRollno.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Enter Rollno");
                    return;
                }
                // Searching roll number
                Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + editRollno.getText() + "'", null);
                if (c.moveToFirst()) {
                    // Modifying record if found
                    db.execSQL("UPDATE student SET name='" + editName.getText() + "',marks='" + editMarks.getText() +
                            "' WHERE rollno='" + editRollno.getText() + "'");
                    showMessage("Success", "Record Modified");

                } else {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();


            }
        });

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                // Checking empty roll number
                Cursor c = db.rawQuery("SELECT * FROM student", null);
                if (c.getCount() == 0) {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (c.moveToNext()) {
                    buffer.append("Student ID: " + c.getString(0) + "\n");
                    buffer.append("Student Full Name: " + c.getString(1) + "\n");
                    buffer.append("Student Class: " + c.getString(2) + "\n\n");
                }
                showMessage("Student Details", buffer.toString());
            }
        });
        btnShowInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showMessage("Students Information Handling", "https://codeunplug.com/");

            }
        });

    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        editRollno.setText("");
        editName.setText("");
        editMarks.setText("");
        editRollno.requestFocus();
    }


}