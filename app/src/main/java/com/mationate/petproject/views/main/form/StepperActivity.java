package com.mationate.petproject.views.main.form;

import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mationate.petproject.R;
import com.mationate.petproject.partials.FieldCallback;
import com.mationate.petproject.partials.PhotoField;
import com.mationate.petproject.partials.TextAreaField;
import com.mationate.petproject.partials.TextInputField;

import java.util.List;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class StepperActivity extends GalleryActivity implements VerticalStepperForm, FieldCallback, PhotoField.Listener {

    private TextInputField nameField;
    private TextAreaField descriptionField;
    private VerticalStepperFormLayout stepperFormLayout;
    private PhotoField photoField;


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
                return nameField;
            case 1:
                descriptionField = new TextAreaField(this);
                descriptionField.setCallback(this);
                descriptionField.setValidation(50);
                return descriptionField;
            case 2:
                photoField = new PhotoField(stepperFormLayout);
                photoField.setCallback(this, this);
                return photoField.getView();
            default:
                return new EditText(this);
        }

    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {
            case 0:
                if (nameField.isValid()) {
                    completed();
                } else {
                    uncompleted("Por favor llene el campo");
                }
                break;
            case 1:
                if (descriptionField.isValid()) {
                    completed();
                } else {
                    uncompleted("Por favor llene el campo");
                }
                break;
            default:
                break;

        }
    }

    @Override
    public void sendData() {

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
}
