package com.mationate.petproject.views.main.form;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mationate.petproject.data.CurrentUser;
import com.mationate.petproject.data.Nodes;
import com.mationate.petproject.models.Pet;

import java.util.ArrayList;
import java.util.List;

public class UploadPhoto {

    private Callback callback;
    private int total = 0;
    private int count = 0;
    private Pet pet;
    private List<String> urls = new ArrayList<>();

    public UploadPhoto(Callback callback) {
        this.callback = callback;
    }

    public void toFirebase(List<Uri> uris, Pet pet) {
        this.pet = pet;
        total = uris.size();

        for (Uri uri : uris) {
            upload(uri);
        }


    }

    private void upload(final Uri uri) {
        StorageReference reference = FirebaseStorage.getInstance().getReference();
        String userUid = new CurrentUser().getUid();
        final StorageReference petRef = reference.child(userUid).child(uri.getLastPathSegment()+".jpg");

        Log.e("PHOTO", "start");
        Log.e("PHOTO", uri.getPath());
        Log.e("PHOTO", uri.getLastPathSegment());


        petRef.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    return petRef.getDownloadUrl();
                } else {
                    Log.e("UPLOAD_PHOTO", "exception", task.getException());
                    return null;
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                count++;
                if (task.isSuccessful() && task.getResult() != null) {
                    Uri downloadUri = task.getResult();
                    String[] fullUrl = downloadUri.toString().split("&token");
                    String url = fullUrl[0];
                    urls.add(url);
                }
                validation();
            }
        });

    }

    private void validation(){
        callback.progress(count);
        if (count == total) {
            DatabaseReference ref = new Nodes().petField();
            String key = ref.push().getKey();
            Log.d("KEY",key);
            pet.setPhotos(urls);
            pet.setMail(new CurrentUser().getMail());
            pet.setKey(key);
            ref.child(key).setValue(pet);

            Pet reduced = new Pet();
            reduced.setKey(key);
            reduced.setName(pet.getName());
            List<String> photo = pet.getPhotos();
            reduced.setPreviews(photo.get(0));
            new Nodes().petList().child(key).setValue(reduced);
            callback.done();
        }
    }

    public interface Callback {
        void progress(int progress);
        void done();
    }


}
