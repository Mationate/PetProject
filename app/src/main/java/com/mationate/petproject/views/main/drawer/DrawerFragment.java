package com.mationate.petproject.views.main.drawer;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mationate.petproject.R;
import com.mationate.petproject.data.CurrentUser;
import com.mationate.petproject.data.UploadPhoto;
import com.mationate.petproject.views.login.LoginActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class DrawerFragment extends Fragment implements UploadPhoto.Callback {

    private static final int RC_GALLERY = 322;
    private static final int RC_CHOOSE = 343;


    public DrawerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drawer, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        TextView userEmail = view.findViewById(R.id.emailTv);
        TextView logoutTv = view.findViewById(R.id.logoutTv);
        logoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
            }
        });
        userEmail.setText(new CurrentUser().getMail());
        userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhotos();
            }
        });
    }

    private void selectPhotos() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (RC_GALLERY == requestCode) {
            if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {

            }
        }
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
                new UploadPhoto(this).toFirebase(selections);
            }
        }
    }

    @Override
    public void progress(int progress) {

    }

    @Override
    public void done() {

    }
}
