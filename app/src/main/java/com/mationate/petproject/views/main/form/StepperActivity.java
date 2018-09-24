package com.mationate.petproject.views.main.form;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mationate.petproject.R;
import com.mationate.petproject.adapter.PreviewPhotoAdapter;
import com.mationate.petproject.data.Nodes;
import com.mationate.petproject.data.UploadPhoto;
import com.mationate.petproject.models.UserField;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper);

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
        UserField userField = new UserField();
        userField.setName(nameField.getResult());
        userField.setDescription(descriptionField.getResult());
        userField.setPhoto(photoField.getResult());

        new Nodes().petField().push().setValue(userField);
        new UploadPhoto( this).toFirebase(photoField.getPhoto());
        Intent intent = new Intent(StepperActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

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
        Log.d("PHOTOSREADY", String.valueOf((photos)));
        photoField.setPhotos(photos);
    }

    @Override
    public void requestPhotos() {
        openGallery();
    }

    @Override
    public void progress(int progress) {

    }

    @Override
    public void done() {

    }
}
