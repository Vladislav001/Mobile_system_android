package com.bignerdranch.android.countrytest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText ETemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


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

        findViewById(R.id.btn_reset_password).setOnClickListener(this);
        findViewById(R.id.href_back_authorization).setOnClickListener(this);

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
//            Intent intent = new Intent(AuthorizationActivity.this, TestActivity.class);
//            startActivity(intent);
        }

        checkExistenceEmailPassword();
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_reset_password)
        {

            mAuth.sendPasswordResetEmail(ETemail.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, "Пароль выслан на почту", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else if (view.getId() == R.id.href_back_authorization){
            Intent intent = new Intent(ResetPasswordActivity.this, AuthorizationActivity.class);
            startActivity(intent);
        }
    }

    // Активируем Авторизацию при заполненных полях
    public void checkExistenceEmailPassword(){

        ETemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (ETemail.getText().toString().length() > 0 ) {
                    findViewById(R.id.btn_reset_password).setEnabled(true);
                } else {
                    findViewById(R.id.btn_reset_password).setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

}
