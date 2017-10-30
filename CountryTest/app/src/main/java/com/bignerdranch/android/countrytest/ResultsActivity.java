package com.bignerdranch.android.countrytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

    private static final String TAG = "Мой тег";

    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    private TextView counterCorrectAnswer;
    private ListView answer;

    DatabaseReference hrefCountCorrectAnswer;
    DatabaseReference resultAnswer;

    private int mCorrectAnswer = 0; // счетчик правильных ответов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //counterCorrectAnswer = (TextView) findViewById(R.id.counterCorrectAnswer);
        answer = (ListView) findViewById(R.id.answer);

        // Устанавливаем возможность клика на кнопку для повторения теста
        findViewById(R.id.repeat_test).setOnClickListener(this);

        myRef = FirebaseDatabase.getInstance().getReference(); // ссылка на дефолтную БД
       // hrefCountCorrectAnswer = myRef.child("Users").child(user.getUid()).child("results");
        resultAnswer =  myRef.child("Users").child(user.getUid()).child("test_results"); // ссылка на узел БД настроек

                resultAnswer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshotAnswer) {
                        // Получаем значения ответов из FB Database
                        String answer_0 = "1) " + dataSnapshotAnswer.child("0/answer").getValue(String.class);
                        String answer_1 = "2) " + dataSnapshotAnswer.child("1/answer").getValue(String.class);
                        String answer_2 = "3) " + dataSnapshotAnswer.child("2/answer").getValue(String.class);
                        String answer_3 = "4) " + dataSnapshotAnswer.child("3/answer").getValue(String.class);
                        String answer_4 = "5) " + dataSnapshotAnswer.child("4/answer").getValue(String.class);
                        String answer_5 = "6) " + dataSnapshotAnswer.child("5/answer").getValue(String.class);
                        String answer_6 = "7) " + dataSnapshotAnswer.child("6/answer").getValue(String.class);
                        String answer_7 = "8) " + dataSnapshotAnswer.child("7/answer").getValue(String.class);
                        String answer_8 = "9) " + dataSnapshotAnswer.child("8/answer").getValue(String.class);
                        String answer_9 = "10) " + dataSnapshotAnswer.child("9/answer").getValue(String.class);

                        String[] resultsAnswers = { answer_0, answer_1, answer_2, answer_3, answer_4,
                                answer_5, answer_6, answer_7, answer_8, answer_9 };

                        // создаем адаптер
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ResultsActivity.this,
                                R.layout.my_list_result, resultsAnswers);

                        // присваиваем адаптер списку
                        answer.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
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
