package com.cem.powerqualityanalyser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.R;



public class LaunchActivity extends AllBaseActivity {

    private ImageView imageView;
    private final long time = 2000l;
    private boolean isExit = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     showTopView(false);
        setContentView(R.layout.activity_launch);
        imageView = findViewById(R.id.lauch_image);

        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isExit){
                    Intent intent = new Intent(LaunchActivity.this, ConfigActiity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isExit = true;
    }


}
