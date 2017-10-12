package com.bignerdranch.android.countrytest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class TestActivity extends AppCompatActivity  {

    //Android (Java)
    private StorageReference mStorageRef;
    private DatabaseReference myRef;

    private ImageView mBTNtrue;
    private ImageView mBTNfalse;
    private ImageView mBTNlefttArrow;
    private ImageView mBTNrightArrow;
    private ImageView mBTNstopTest;
    private TextView mTVtitle;

    DatabaseReference manageButtonsTrueFale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mStorageRef = FirebaseStorage.getInstance().getReference(); // ссылка на дефолтное Storage
        myRef = FirebaseDatabase.getInstance().getReference(); // ссылка на дефолтную БД

//
//        manageButtonsTrueFale = (DatabaseReference) myRef.child("test/manage_buttons").orderByChild("style_images_like_dislike");
//
//        //устанавливаем слушатель на узле пользователи (usersRef выступает ссылкой на соответствующий узел)
//        manageButtonsTrueFale.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //получаем информацию
//                String TrueFalse = (String) dataSnapshot.getValue();
//                Toast.makeText(TestActivity.this, "Значение тру и фалсе = " + TrueFalse, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                //ошибка
//            }
//        });

        StorageReference trueLink = mStorageRef.child("/manager_buttons/like_dislike/0/like.png");
        StorageReference falseLink = mStorageRef.child("/manager_buttons/like_dislike/0/dislike.png");
        StorageReference NextLink = mStorageRef.child("/manager_buttons/next_back/0/next.png");
        StorageReference BackLink = mStorageRef.child("/manager_buttons/next_back/0/back.png");
        StorageReference StopLink = mStorageRef.child("/manager_buttons/stop_test/0/stop.png");

        mBTNtrue = (ImageView) findViewById(R.id.btn_true);
        mBTNfalse = (ImageView) findViewById(R.id.btn_false);
        mBTNlefttArrow = (ImageView) findViewById(R.id.btn_left_arrow);
        mBTNrightArrow = (ImageView) findViewById(R.id.btn_right_arrow);
        mBTNstopTest = (ImageView) findViewById(R.id.btn_stop);
        mTVtitle = (TextView) findViewById(R.id.tv_title);



        setImageFromFB(getBaseContext(), trueLink, mBTNtrue);
        setImageFromFB(getBaseContext(), falseLink, mBTNfalse);
        setImageFromFB(getBaseContext(), NextLink, mBTNrightArrow);
        setImageFromFB(getBaseContext(), BackLink, mBTNlefttArrow);
        setImageFromFB(getBaseContext(), StopLink, mBTNstopTest);

    }



        // Загрузка изображения из FB Storage
        public static void setImageFromFB(Context context, StorageReference storageReference, ImageView imageView){
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);
    }


}
