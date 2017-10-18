package com.bignerdranch.android.countrytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    private TextView counterCorrectAnswer;

    DatabaseReference hrefCountCorrectAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        counterCorrectAnswer = (TextView) findViewById(R.id.counterCorrectAnswer);

        findViewById(R.id.repeat_test).setOnClickListener(this);

        myRef = FirebaseDatabase.getInstance().getReference(); // ссылка на дефолтную БД
        hrefCountCorrectAnswer = myRef.child("Users").child(user.getUid()).child("results");

        hrefCountCorrectAnswer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               Integer countCorrectAnswer = dataSnapshot.getValue(Integer.class);


                counterCorrectAnswer.setText(countCorrectAnswer.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.repeat_test)
        {
            Toast.makeText(this, "Возвращаемся к тесту", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ResultsActivity.this, TestActivity.class);
            startActivity(intent);
        }
    }
}
