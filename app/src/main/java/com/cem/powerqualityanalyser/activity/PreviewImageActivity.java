package com.cem.powerqualityanalyser.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;


import com.cem.powerqualityanalyser.R;

import uk.co.senab.photoview.PhotoView;

public class PreviewImageActivity extends AllBaseActivity {
    private String imagePath;
    private PhotoView photoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);
        photoView = findViewById(R.id.photo_view);
        imagePath = getIntent().getStringExtra("imagePath");
        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        photoView.setImageBitmap(bm);
    }
}
