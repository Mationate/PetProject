package com.mationate.petproject.views.main.form;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mationate.petproject.data.UploadPhoto;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;

import java.util.List;

abstract class GalleryActivity extends AppCompatActivity {

    private static final int RC_GALLERY = 543;
    private static final int RC_CHOOSE = 222;

    @SuppressLint("NewApi")
    protected void openGallery() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, RC_GALLERY);
        } else {
            selectPhotos();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (RC_GALLERY == requestCode) {
            if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                selectPhotos();
            }
        }
    }

    private void selectPhotos() {
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(9)
                .gridExpectedSize(200)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new PicassoEngine())
                .forResult(RC_CHOOSE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RC_CHOOSE == requestCode) {
            if (RESULT_OK == resultCode) {
                List<Uri> selections = Matisse.obtainResult(data);
                for (Uri uri : selections) {
                    Log.d("PHOTO", uri.toString());
                }
                photosReady(selections);
            }
        }
    }

    protected abstract void photosReady(List<Uri> photos);


}
