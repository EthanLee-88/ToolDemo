package com.ethan.tooldemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CameraActivity extends AppCompatActivity {
    private static final String TAG = "CameraActivity";
    private Button one, two;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        one = findViewById(R.id.request_one);
        two = findViewById(R.id.request_two);
        imageView = findViewById(R.id.image_show);
        init();
    }

    private void init() {
        one.setOnClickListener((View view) -> {
            checkPermission();
        });
        two.setOnClickListener((View view) -> {
            getPicture();
        });
    }

    private void checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        Log.d(TAG, "result = " + result + " - PERMISSION_GRANTED = " + PackageManager.PERMISSION_GRANTED);
        if (result == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    10086);
            Log.d(TAG, "DENIED");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "requestCode = " + requestCode);
        Log.d(TAG, "permissions = " + Arrays.toString(permissions));
        Log.d(TAG, "permissions = " + Arrays.toString(grantResults));
    }

    private void getPicture(){
//        String filePath = Environment.getExternalStorageDirectory().getPath();
//        String path = filePath + "/toolP/" + "pic.jpg";
//        File file = new File(path);
//        if (!file.exists()){
//            file.mkdir();
//        }
        File file = new File("storage/sdcard/jiang.jpg");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,10010);
        Log.d(TAG, "" + Environment.getExternalStorageState());
        Log.d(TAG, "" + Environment.getDataDirectory());
        Log.d(TAG, "" + Environment.getDownloadCacheDirectory());
        Log.d(TAG, "" + Environment.getRootDirectory());
        Log.d(TAG, "" + Environment.getStorageDirectory());
        Log.d(TAG, "" + Environment.getExternalStorageDirectory());
        Log.d(TAG, "" + getExternalFilesDir(null));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "requestCode = " + requestCode);
        Log.d(TAG, "resultCode = " + resultCode + " - RESULT_PK = " +RESULT_OK);
        if (data == null) return;
        Bundle bundle = data.getExtras();
        if (bundle == null) return;
        Bitmap bitmap = (Bitmap) bundle.get("data");
        imageView.setImageBitmap(bitmap);
    }
}
