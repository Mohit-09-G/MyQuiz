package com.example.myquizmy.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myquizmy.MainActivity;
import com.example.myquizmy.R;
import com.example.myquizmy.SessionManager;


public class IntroPage extends AppCompatActivity {
    Button sign, login;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);
        sessionManager=new SessionManager(this);
        if (sessionManager.isLogin(this)) {
            Intent intent = new Intent(IntroPage.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        sign = findViewById(R.id.signupbtn);
        login = findViewById(R.id.loginbtn);



        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroPage.this, Sign_up_Activity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroPage.this, Sign_in_Activity.class);
                startActivity(intent);
            }
        });
    }
}
