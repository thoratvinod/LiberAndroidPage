package com.example.mauli.androidhomepage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editPhone,editOTP;
    private Button btnOTP,btnVerifiction;
    private FirebaseAuth mAuth;
    private String phone,codeSent;
    private TextView resend,goBack;
    private ProgressBar progressBarInLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editPhone = (EditText) findViewById(R.id.editPhone);
        btnVerifiction = (Button) findViewById(R.id.btnVerification);
        btnOTP = (Button) findViewById(R.id.btnOTP);
        editOTP = (EditText) findViewById(R.id.editOTP);
        mAuth = FirebaseAuth.getInstance();
        resend = (TextView) findViewById(R.id.resend);
        goBack= (TextView) findViewById(R.id.goBack);
        progressBarInLogin = (ProgressBar) findViewById(R.id.progressBarInLogin);

        btnVerifiction.setOnClickListener(this);
        btnOTP.setOnClickListener(this);
        resend.setOnClickListener(this);
        goBack.setOnClickListener(this);

    }
    private void verifySignIncode(){
        String code = editOTP.getText().toString();
        if(code.isEmpty()){
            editOTP.setError("OTP is Required");
            editOTP.requestFocus();
            return;
        }
        if(code.length()<6)
        {
            editOTP.setError("Enter Valid OTP");
            editOTP.requestFocus();
            return;
        }
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
            signInWithPhoneAuthCredential(credential);
        }
        catch (Exception e)
        {
            Toast.makeText(LoginActivity.this,"OTP is not match",Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this,UpdateProfile.class));
        }

    }
    private void defaultUi(){
        editPhone.setVisibility(View.VISIBLE);
        btnVerifiction.setVisibility(View.VISIBLE);
        editOTP.setVisibility(View.INVISIBLE);
        btnOTP.setVisibility(View.INVISIBLE);
        resend.setVisibility(View.INVISIBLE);
        goBack.setVisibility(View.INVISIBLE);
    }
    private void updateUi(){

        editPhone.setVisibility(View.INVISIBLE);
        btnVerifiction.setVisibility(View.INVISIBLE);
        editOTP.setVisibility(View.VISIBLE);
        btnOTP.setVisibility(View.VISIBLE);
        resend.setVisibility(View.VISIBLE);
        goBack.setVisibility(View.VISIBLE);

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBarInLogin.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this,"Login is successfull",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this,UpdateProfile.class));

                        } else {
                            Toast.makeText(LoginActivity.this,"Login is failed",Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }
    private void sendVerificationCode(){



        phone = editPhone.getText().toString();
        if(phone.isEmpty()){
            editPhone.setError("Mobile Number is Required");
            editPhone.requestFocus();
            return;
        }
        if(phone.length()<10){
            editPhone.setError("Please Enter valid Mobile Number");
            editPhone.requestFocus();
            return;
        }
        progressBarInLogin.setVisibility(View.VISIBLE);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        updateUi();


    }
    private void ResendOTP(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            progressBarInLogin.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this,"OTP is Sent",Toast.LENGTH_LONG).show();


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            progressBarInLogin.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, UpdateProfile.class));
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnVerification:
                sendVerificationCode();
                break;
            case R.id.btnOTP:
                progressBarInLogin.setVisibility(View.VISIBLE);
                verifySignIncode();
                break;
            case R.id.resend:
                ResendOTP();
                Toast.makeText(LoginActivity.this,"OTP is Sent",Toast.LENGTH_LONG).show();
                break;
            case R.id.goBack:
                defaultUi();
                break;
        }
    }

}


