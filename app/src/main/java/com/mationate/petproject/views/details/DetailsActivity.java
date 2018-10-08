package com.mationate.petproject.views.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mationate.petproject.R;
import com.mationate.petproject.models.Pet;
import com.mationate.petproject.views.details.gallery.GalleryFragment;


public class DetailsActivity extends AppCompatActivity implements GetPet.PetCallback {

    private TextView name, description, mailBtn;
    private Pet pet;
    private  GalleryFragment galleryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        pet = (Pet) getIntent().getSerializableExtra("pet");
        galleryFragment = (GalleryFragment) getSupportFragmentManager().findFragmentById(R.id.galleryFragment);

        name = findViewById(R.id.detailNameTv);
        description = findViewById(R.id.descriptionTv);
        mailBtn = findViewById(R.id.mailBtn);
        name.setText(pet.getName());

        new GetPet(this).withKey(pet.getKey());

    }



    @Override
    public void getData(final Pet pet) {
        description.setText(pet.getDescription());
        galleryFragment.updateAdapter(pet.getPhotos());
        mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("*/*");
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{pet.getMail()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Estoy interesado/a en la mascota");
                startActivity(intent);
            }

        });


    }
}
