package com.example.crudsoccerleaguevolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView email,username;
    private Button btn_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        btn_logout = findViewById(R.id.btn_logout);

        Intent intent = getIntent();
        String extraEmail = intent.getStringExtra("email");
        String extraUsername = intent.getStringExtra("username");

        email.setText(extraEmail);
        username.setText(extraUsername);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}