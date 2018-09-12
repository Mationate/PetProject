package com.mationate.petproject.views.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.mationate.petproject.R;
import com.mationate.petproject.views.main.MainActivity;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginCallback {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new UserValidation(this).userValidation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RC_SIGN_IN == requestCode) {
            if (RESULT_OK == resultCode) {
                logged();
            } else {
                signUp();
            }
        }
    }

    @Override
    public void signUp() {

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()
            /*  new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build()  */ );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.LoginTheme)
                        .setLogo(R.mipmap.ic_launcher_round)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void logged() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
