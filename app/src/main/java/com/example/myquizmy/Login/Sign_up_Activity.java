package com.example.myquizmy.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquizmy.Database.TestDatabase;
import com.example.myquizmy.R;

public class Sign_up_Activity extends AppCompatActivity {

    private TextView signin;
    private EditText editTextName, editTextPhone, editTextPassword;
    private Button buttonSignUp;
    private TestDatabase databaseHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signin = findViewById(R.id.signlink);
        editTextName = findViewById(R.id.name);
        editTextPhone = findViewById(R.id.phone);
        editTextPassword = findViewById(R.id.pass);
        buttonSignUp = findViewById(R.id.sign_up_button);


        databaseHelper = new TestDatabase(this);

        buttonSignUp.setOnClickListener(v -> {
            // Get user input
            String name = editTextName.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();


            if (name.isEmpty()) {
                Toast.makeText(Sign_up_Activity.this, "Name is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (phone.isEmpty() || !phone.matches("[6-9][0-9]{9}")) {
                Toast.makeText(Sign_up_Activity.this, "Phone number must start with 6-9 and be 10 digits long", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.isEmpty()) {
                Toast.makeText(Sign_up_Activity.this, "Password is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (databaseHelper.checkPhone(phone)) {
                Toast.makeText(Sign_up_Activity.this, "Phone number already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert data into database
            boolean isInserted = databaseHelper.insertLoginData(name, phone, password, "", "", "", "","","", "", "", "", "", "", "","","",null);
            if (isInserted) {
                Toast.makeText(Sign_up_Activity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                clearFields(); // Clear input fields
                navigateToSignIn(); // Navigate to sign-in activity
            } else {
                Toast.makeText(Sign_up_Activity.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });

        signin.setOnClickListener(v -> navigateToSignIn());
    }



    private void clearFields() {
        editTextName.setText("");
        editTextPhone.setText("");
        editTextPassword.setText("");
    }


    private void navigateToSignIn() {
        Intent intent = new Intent(getApplicationContext(), Sign_in_Activity.class);
        startActivity(intent);
    }
}
