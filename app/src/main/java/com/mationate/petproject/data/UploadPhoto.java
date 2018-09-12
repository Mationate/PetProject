package com.mationate.petproject.data;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class UploadPhoto {

    private Callback callback;
    private int total = 0;
    private int count = 0;

    public UploadPhoto(Callback callback) {
        this.callback = callback;
    }

    public void toFirebase(List<Uri> uris) {
        total = uris.size();

        for (Uri uri : uris) {
            upload(uri);
        }


    }

    private void upload(Uri uri) {
        StorageReference reference = FirebaseStorage.getInstance().getReference();
        String userUid = new CurrentUser().getUid();
        StorageReference petRef = reference.child(userUid).child(uri.getLastPathSegment()+".jpg");

        Log.e("PHOTO", "start");
        Log.e("PHOTO", uri.getPath());
        Log.e("PHOTO", uri.getLastPathSegment());



        petRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                count++;
                validation();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("PHOTO", "error", e);
                count++;
                validation();
            }
        });
    }

    private void validation(){

        if (count == total) {

        }
    }

    public interface Callback {
        void progress(int progress);
        void done();
    }


}
