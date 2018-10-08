package com.mationate.petproject.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Nodes {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();

    public DatabaseReference users (){
        return root.child("users");
    }

    public DatabaseReference user (String mail){
        return users().child(mail);
    }

    public DatabaseReference petField(){
        return root.child("pets");
    }

    public DatabaseReference petList(){
        return root.child("pets_list");
    }





}
