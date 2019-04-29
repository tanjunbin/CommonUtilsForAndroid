package com.tan.comm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tan.lib_utils_android.app.AppUtils;
import com.tan.lib_utils_android.common.FileProvider7;
import com.tan.lib_utils_android.common.ToastUtils;
import com.tan.lib_utils_android.libinterface.PermissionCallBack;
import com.tan.lib_utils_android.permission.PermissionUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private ImageView imageView;

    private static final int REQUEST_CODE_TAKE_PHOTO = 0x110;
    private static final int REQ_PERMISSION_CODE_TAKE_PHOTO = 0X112;

    private String mCurrentPhotoPath;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        imageView = findViewById(R.id.image_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        PermissionUtils.request(this, new PermissionCallBack() {
            @Override
            public void afterComplete() {
                ToastUtils.show(MainActivity.this,"同意全部权限");
                takePhotoNoCompress();
            }

            @Override
            public void afterError() {
                ToastUtils.show(MainActivity.this,"拒绝权限");
            }
        }, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void takePhotoNoCompress() {

        //调相机
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                    .format(new Date()) + ".png";
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            mCurrentPhotoPath = file.getAbsolutePath();

            Uri fileUri = FileProvider7.getUriForFile(this, file);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

       if (requestCode == REQ_PERMISSION_CODE_TAKE_PHOTO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoNoCompress();
            } else {
                // Permission Denied
                ToastUtils.show(MainActivity.this,"Permission Denied");
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_TAKE_PHOTO) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
        }

    }
}
