package com.example.edu.newimagegallery;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int LOAD_IMAGE = 101;
    int CAPTURE_IMAGE = 102;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnImagecall = findViewById(R.id.btnImagecall);
        btnImagecall.setOnClickListener(this);
        Button btnImagephoto = findViewById(R.id.btnImagephoto);
        btnImagephoto.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnImagecall:
              Intent intent = new Intent();
              intent.setAction(Intent.ACTION_GET_CONTENT);
              intent.setType("image/*");
              startActivityForResult(intent,LOAD_IMAGE);
            break;
            case R.id.btnImagephoto:
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
                    Intent intent1 = new Intent();
                    intent1.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent1, CAPTURE_IMAGE);
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case 101:
             if (data != null) {
                 ImageView imageView = findViewById(R.id.imageView);
                 Uri selecteImage = data.getData();
                    try {
                       InputStream inputStream = this.getContentResolver().openInputStream(selecteImage);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
                    }
                    catch (FileNotFoundException e) {e.printStackTrace();}
            }
            break;
            case 102:
               ImageView imageView1 = findViewById(R.id.imageView);
                if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap)extras.get("data");
                    imageView1.setImageBitmap(bitmap);
                }

                break;
        }
    }
}
