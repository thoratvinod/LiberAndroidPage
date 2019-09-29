package com.example.mauli.androidhomepage;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static int SPLASH_TIME = 3000;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progrssbarInWelcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAuth.getCurrentUser() != null) {
                    progressBar.setVisibility(View.GONE);
                    finish();
                    startActivity(new Intent(WelcomActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else {
                    progressBar.setVisibility(View.GONE);
                    finish();
                    startActivity(new Intent(WelcomActivity.this, LoginActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        }, SPLASH_TIME);
    }


}
