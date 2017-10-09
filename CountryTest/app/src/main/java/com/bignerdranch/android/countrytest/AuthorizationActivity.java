package com.bignerdranch.android.countrytest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText ETemail;
    private EditText ETpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
//                    Intent intent = new Intent(AuthorizationActivity.this,ListTasks.class);
//                    startActivity(intent);
                    // User is signed in

                } else {
                    // User is signed out

                }

            }
        };


        ETemail = (EditText) findViewById(R.id.et_email);
        ETpassword = (EditText) findViewById(R.id.et_password);

        findViewById(R.id.btn_sign_in).setOnClickListener(this);
        findViewById(R.id.href_reset_password).setOnClickListener(this);
        findViewById(R.id.href_registration).setOnClickListener(this);


        // Активируем Авторизацию при заполненных полях
        ETemail.addTextChangedListener(generalTextWatcher);
        ETpassword.addTextChangedListener(generalTextWatcher);

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
//            Intent intent = new Intent(AuthorizationActivity.this, TestActivity.class);
//            startActivity(intent);
        }
    }



    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.btn_sign_in:
                signin(ETemail.getText().toString(),ETpassword.getText().toString());
                break;
            case R.id.href_registration:
                Intent intentAuthorization = new Intent(AuthorizationActivity.this, RegistrationActivity.class);
                startActivity(intentAuthorization);
                break;
            case R.id.href_reset_password:
                Intent ResetPassword = new Intent(AuthorizationActivity.this, ResetPasswordActivity.class);
                startActivity(ResetPassword);
                break;
        }

    }

    // Авторизация
    public void signin(String email , String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(AuthorizationActivity.this, TestActivity.class);
                    startActivity(intent);
                    Toast.makeText(AuthorizationActivity.this, "Aвторизация успешна", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(AuthorizationActivity.this, "Aвторизация провалена", Toast.LENGTH_SHORT).show();

            }
        });
    }

    // Активируем Авторизацию при заполненных полях
    private TextWatcher generalTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (ETemail.getText().toString().length() > 0 &&  ETpassword.getText().toString().length() > 0) {
                findViewById(R.id.btn_sign_in).setEnabled(true);
            } else {
                findViewById(R.id.btn_sign_in).setEnabled(false);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}