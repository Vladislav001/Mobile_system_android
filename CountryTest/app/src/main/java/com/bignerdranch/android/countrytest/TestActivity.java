package com.bignerdranch.android.countrytest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

    private static final String TAG = "Мой тег";

    private StorageReference mStorageRef;
    private DatabaseReference myRef;

    private ImageView mBTNtrue;
    private ImageView mBTNfalse;
    private ImageView mBTNlefttArrow;
    private ImageView mBTNrightArrow;
    private ImageView mBTNstopTest;
    private TextView mTVtitle;

    DatabaseReference manageButtons;
    DatabaseReference settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mBTNtrue = (ImageView) findViewById(R.id.btn_true);
        mBTNfalse = (ImageView) findViewById(R.id.btn_false);
        mBTNlefttArrow = (ImageView) findViewById(R.id.btn_left_arrow);
        mBTNrightArrow = (ImageView) findViewById(R.id.btn_right_arrow);
        mBTNstopTest = (ImageView) findViewById(R.id.btn_stop);
        mTVtitle = (TextView) findViewById(R.id.tv_title);

        mStorageRef = FirebaseStorage.getInstance().getReference(); // ссылка на дефолтное Storage
        myRef = FirebaseDatabase.getInstance().getReference(); // ссылка на дефолтную БД

        manageButtons = myRef.child("test/manage_buttons/");
        settings = myRef.child("test/settings/");

        manageButtons.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshotManageButtons) {

                settings.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshotSetting) {

                        // Получаем значения настроек из FB Database
                        String text_setting = dataSnapshotSetting.child("text").getValue(String.class);
                        String swap_setting = dataSnapshotSetting.child("swap").getValue(String.class);
                        String swap_arrows_setting = dataSnapshotSetting.child("swap_arrows").getValue(String.class);
                        String swap_finger_setting = dataSnapshotSetting.child("swap_finger").getValue(String.class);

                        // Получаем значения стилей из FB Database
                        String true_false_number = dataSnapshotManageButtons.child("style_images_like_dislike").getValue(String.class);
                        String next_back_number = dataSnapshotManageButtons.child("style_images_swap_arrows").getValue(String.class);
                        String stop_number = dataSnapshotManageButtons.child("style_image_stop_test").getValue(String.class);

                        // Создаем ссылку на картинку
                        StorageReference trueLink = mStorageRef.child("/manager_buttons/like_dislike/" + true_false_number +  "/like.png");
                        StorageReference falseLink = mStorageRef.child("/manager_buttons/like_dislike/" + true_false_number + "/dislike.png");
                        StorageReference NextLink = mStorageRef.child("/manager_buttons/next_back/" + next_back_number + "/next.png");
                        StorageReference BackLink = mStorageRef.child("/manager_buttons/next_back/" + next_back_number + "/back.png");
                        StorageReference StopLink = mStorageRef.child("/manager_buttons/stop_test/" + stop_number + "/stop.png");

                        // Устанавливаем вид стиля (картинки)
                        setImageFromFB(getBaseContext(), trueLink, mBTNtrue);
                        setImageFromFB(getBaseContext(), falseLink, mBTNfalse);
                        if (Boolean.valueOf(swap_setting)) {
                            setImageFromFB(getBaseContext(), NextLink, mBTNrightArrow);
                            setImageFromFB(getBaseContext(), BackLink, mBTNlefttArrow);
                        }
                        setImageFromFB(getBaseContext(), StopLink, mBTNstopTest);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });



    }

        // Загрузка изображения из FB Storage
        public static void setImageFromFB(Context context, StorageReference storageReference, ImageView imageView){
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);
    }

    // !!! Удаление слушателей наверно еще надо бы для addListenerForSingleValueEvent !!!
//Toast.makeText(TestActivity.this, "Значение тру и фалсе = " + peopleNumber, Toast.LENGTH_SHORT).show();
}
