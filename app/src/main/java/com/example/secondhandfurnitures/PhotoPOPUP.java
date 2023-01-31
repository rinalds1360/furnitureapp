package com.example.secondhandfurnitures;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

public class PhotoPOPUP extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_popup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.8));

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        Uri photo = (Uri) intent.getExtras().get("photoUri");
        boolean isMainPhoto = intent.getBooleanExtra("mainPhoto",false);
        ImageView imageView = findViewById(R.id.imageview_ima);
        Button delete = findViewById(R.id.dzest);
        Button exit = findViewById(R.id.iziet);
        CheckBox mainPhoto = findViewById(R.id.checkBox_mainPhoto);
        imageView.setImageURI(photo);
        Intent resultIntent = new Intent();

        if (isMainPhoto) {
            mainPhoto.setChecked(true);
        } else {
            mainPhoto.setChecked(false);
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultIntent.putExtra("deletePhoto",false);
                resultIntent.putExtra("photoId",position);
                resultIntent.putExtra("mainPhoto",mainPhoto.isChecked());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultIntent.putExtra("deletePhoto",true);
                resultIntent.putExtra("photoId",position);
                resultIntent.putExtra("mainPhoto",mainPhoto.isChecked());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }
}