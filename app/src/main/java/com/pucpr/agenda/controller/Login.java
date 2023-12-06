package com.pucpr.agenda.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.pucpr.agenda.R;
import com.pucpr.agenda.model.DataModel;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void loginButtonOnClick(View v) {
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        try {
            // Seu código atual de login

            if (username.equals(DataModel.getInstance().userDetails.getUsername()) &&
                    password.equals(DataModel.getInstance().userDetails.getPassword())) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}