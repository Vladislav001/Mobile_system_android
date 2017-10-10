package com.bignerdranch.android.countrytest;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;

public class TestActivity extends AppCompatActivity {

    //Android (Java)
    private StorageReference mStorageRef;

    private ImageView mBTNtrue;
    private ImageView mBTNfalse;
    private ImageView mBTNlefttArrow;
    private ImageView mBTNrightArrow;
    private ImageView mBTNstopTest;
    private TextView mTVtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mStorageRef = FirebaseStorage.getInstance().getReference();
       // Uri file = Uri.fromFile(new File("manager_buttons/like_dislike/0/dislike.jpg"));
        StorageReference riversRef = mStorageRef.child("manager_buttons/like_dislike/0/dislike.jpg");

        mBTNtrue = (ImageView) findViewById(R.id.btn_true);
        mBTNfalse = (ImageView) findViewById(R.id.btn_false);
        mBTNlefttArrow = (ImageView) findViewById(R.id.btn_left_arrow);
        mBTNrightArrow = (ImageView) findViewById(R.id.btn_right_arrow);
        mBTNstopTest = (ImageView) findViewById(R.id.btn_stop);
        mTVtitle = (TextView) findViewById(R.id.tv_title);

        mBTNtrue.setImageResource(R.drawable.authorization);

        Picasso.with(getBaseContext()) //передаем контекст приложения
                .load("https://www.likeni.ru/upload/rk/33f/1024dp90.jpg") //адрес изображения
                .into(mBTNfalse); //ссылка на ImageView

    }

}
