package com.mationate.petproject.views.main;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mationate.petproject.R;
import com.mationate.petproject.partials.FieldCallback;
import com.mationate.petproject.partials.TextAreaField;
import com.mationate.petproject.partials.TextInputField;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class StepperActivity extends AppCompatActivity implements VerticalStepperForm, FieldCallback {

    private TextInputField nameField;
    private TextAreaField descriptionField;
    private VerticalStepperFormLayout stepperFormLayout;

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
                nameField.setValidation(3);
                return nameField;
            case 1:
                descriptionField = new TextAreaField(this);
                descriptionField.setCallback(this);
                descriptionField.setValidation(50);
                return descriptionField;
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
}
