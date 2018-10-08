package com.mationate.petproject.views.main.form;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.mationate.petproject.R;
import com.mationate.petproject.models.Pet;
import com.mationate.petproject.partials.FieldCallback;
import com.mationate.petproject.partials.PartialField;
import com.mationate.petproject.partials.PhotoField;
import com.mationate.petproject.partials.TextAreaField;
import com.mationate.petproject.partials.TextInputField;
import com.mationate.petproject.views.main.MainActivity;
import java.util.ArrayList;
import java.util.List;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class StepperActivity extends GalleryActivity implements VerticalStepperForm, FieldCallback, PhotoField.Listener, UploadPhoto.Callback {

    private TextInputField nameField;
    private TextAreaField descriptionField;
    private VerticalStepperFormLayout stepperFormLayout;
    private PhotoField photoField;
    private List<PartialField> fields = new ArrayList<>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper);

        progressDialog = new ProgressDialog(this);
        String[] steps = getResources().getStringArray(R.array.form_fields);
        stepperFormLayout = findViewById(R.id.stepperForm);
        VerticalStepperFormLayout.Builder.newInstance(stepperFormLayout, steps, this, this)
                .displayBottomNavigation(true)
                .init();

    }

    @Override
    public View createStepContentView(int stepNumber) {
        switch (stepNumber) {
            case 0:
                nameField = new TextInputField(this);
                nameField.setCallback(this);
                nameField.setValidation(2);
                fields.add(nameField);
                return nameField;
            case 1:
                descriptionField = new TextAreaField(this);
                descriptionField.setCallback(this);
                descriptionField.setValidation(50);
                fields.add(descriptionField);
                return descriptionField;
            case 2:
                photoField = new PhotoField(stepperFormLayout);
                photoField.setCallback(this, this);
                fields.add(photoField);
                return photoField.getView();
            default:
                return new EditText(this);
        }

    }

    @Override
    public void onStepOpening(int stepNumber) {
        if (stepNumber >= fields.size()) {
            completed();
            return;
        }
        if (fields.get(stepNumber).isValid()) {
            completed();
        } else {
            uncompleted("Campo obligatorio");
        }
    }

    @Override
    public void sendData() {
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Subiendo Fotos");
        progressDialog.setMessage("Al finalizar, se publicará la mascota");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        List<Uri> uris = photoField.getPhoto();
        progressDialog.setMax(uris.size());
        progressDialog.show();
        Pet pet = new Pet();
        pet.setName(nameField.getResult());
        pet.setDescription(descriptionField.getResult());
        new UploadPhoto( this).toFirebase(uris, pet);
    }

    @Override
    public void completed() {
        stepperFormLayout.setActiveStepAsCompleted();
    }

    @Override
    public void uncompleted(String error) {
        stepperFormLayout.setActiveStepAsUncompleted(error);
    }

    @Override
    protected void photosReady(List<Uri> photos) {
        photoField.setPhotos(photos);
    }

    @Override
    public void requestPhotos() {
        openGallery();
    }

    @Override
    public void progress(int progress) {
        progressDialog.setProgress(progress);
    }


    @Override
    public void done() {
        progressDialog.dismiss();
        Intent intent = new Intent(StepperActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }



}
