package com.mationate.petproject.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CurrentUser {

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public String getMail(){
        return getCurrentUser().getEmail();
    }

    public String getUid(){
        return getCurrentUser().getUid();
    }

    public boolean isLogged(){
        if (currentUser != null) {
            return true;
        } else {
            return false;
        }
    }
}
