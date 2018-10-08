package com.mationate.petproject.views.details;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mationate.petproject.data.Nodes;
import com.mationate.petproject.models.Pet;

import java.util.List;

public class GetPet {

    private PetCallback callback;

    public GetPet(PetCallback callback) {
        this.callback = callback;
    }

    public void withKey(String key) {
        new Nodes().petField().child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pet pet = dataSnapshot.getValue(Pet.class);
                callback.getData(pet);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface PetCallback {
        void getData(Pet pet);
    }

}
