package com.example.mauli.androidhomepage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class UpdateProfile extends AppCompatActivity implements View.OnClickListener{

    private static final int CHOOSE_IMAGE = 101;
    private View view;

    private EditText editUser,editCollege,editEmail,editAddress;
    private TextInputLayout textInputUser,textInputCollege,textInputEmail,textInputAddress;
    private String profileImageUrl;
    private String gendre;
    private boolean emailVerified;
    private Uri uriProfileImage;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabase;
    CircleImageView profileImage;
    private TextView textViewEmailVerify;
    private RadioButton male,female;
    private Button btnSaveInfo,btnProceed,btnUpdate;
    private ProgressBar progressBarInUpdateForActivity;
    private RelativeLayout updateLayout;
    private ScrollView scrollInUpdate;
    private Boolean okValidations;
    private Boolean isNewUser=true;
    private int num,image;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        scrollInUpdate = (ScrollView) findViewById(R.id.scrollInUpdate);
        scrollInUpdate.setVisibility(View.GONE);
        updateLayout = (RelativeLayout) findViewById(R.id.updateLayout);
        Snackbar.make(updateLayout, "Click On Update Profile To Update profile otherwise click on Proceed", Snackbar.LENGTH_INDEFINITE)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        editUser = (EditText) findViewById(R.id.editUser);
        editCollege = (EditText) findViewById(R.id.editCollege);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editAddress =(EditText) findViewById(R.id.editAddress);
        profileImage = (CircleImageView) findViewById(R.id.profileImage);
        userDatabase = FirebaseDatabase.getInstance().getReference("User_Information");
        textViewEmailVerify = (TextView) findViewById(R.id.textViewEmailVerify);
        btnSaveInfo= (Button) findViewById(R.id.btnSaveInfo);
        btnProceed = (Button) findViewById(R.id.btnProceed);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        progressBarInUpdateForActivity = (ProgressBar) findViewById(R.id.progressBarInUpdateForActivity);
        textInputAddress = (TextInputLayout) findViewById(R.id.textInputAddress);
        textInputCollege = (TextInputLayout) findViewById(R.id.textInputCollege);
        textInputEmail = (TextInputLayout) findViewById(R.id.textInputEmail);
        textInputUser = (TextInputLayout) findViewById(R.id.textInputUser);

        image=0;
        Intent intent = getIntent();
        num = intent.getIntExtra("hint",1);


        profileImage.setOnClickListener(this);
        btnSaveInfo.setOnClickListener(this);
        btnProceed.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        loadUserInformation();



    }


    private Boolean validations(){

        textInputAddress.setError(null);
        textInputCollege.setError(null);
        textInputEmail.setError(null);
        textInputUser.setError(null);
        if(uriProfileImage==null&&image==0){
            Toast.makeText(this, "Please Upload Profile Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(editUser.getText().toString().isEmpty()){

            textInputUser.setError("Your Name is Required");
            textInputUser.requestFocus();
            return false;

        }
        if(editEmail.getText().toString().isEmpty()){

            textInputEmail.setError("Email is required.");
            textInputEmail.requestFocus();
            return false;

        }
        if (gendre==null){
            Toast.makeText(this, "Please select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(editCollege.getText().toString().isEmpty()){

            textInputCollege.setError("Your College is Required");
            textInputCollege.requestFocus();
            return false;

        }

        if(editAddress.getText().toString().isEmpty()){

            textInputAddress.setError("Address is Required");
            textInputAddress.requestFocus();
            return false;

        }

        return true;


    }


    private void SaveUserInformation(){
        Boolean val = validations();

        if (val){
            FirebaseUser firebaseUser = mAuth.getCurrentUser();


            Toast.makeText(this, profileImageUrl, Toast.LENGTH_SHORT).show();

            User user = new User(profileImageUrl,editCollege.getText().toString(),gendre,editUser.getText().toString(), editEmail.getText().toString(), editAddress.getText().toString(),firebaseUser.getUid(), firebaseUser.getPhoneNumber(),firebaseUser.isEmailVerified());


            if(!firebaseUser.isEmailVerified()&&user.getUsers_email()!=null){

                firebaseUser.updateEmail(editEmail.getText().toString());
                sendVerificationMail();

            }


            userDatabase.child("My_App_User").child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(UpdateProfile.this,"Profile Sucessfully Saved",Toast.LENGTH_LONG).show();
                }
            });


        }
        isNewUser = false;





    }

    private void loadUserInformation() {

        final FirebaseUser firebaseUser = mAuth.getCurrentUser();


        /*if (firebaseUser.getPhotoUrl() != null) {
            Toast.makeText(this, "In if Loop", Toast.LENGTH_SHORT).show();
            profileImage.setImageAlpha((int) 1.00);
            Glide.with(this)
                    .load(firebaseUser.getPhotoUrl().toString())
                    .into(profileImage);
        }*/

        userDatabase.child("My_App_User").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);


                if(user!=null&&firebaseUser!=null){

                    isNewUser = false;

                    btnUpdate.setVisibility(View.VISIBLE);
                    editUser.setText(user.getUsers_name());
                    if (user.getGendre()!=null){
                        if(user.getGendre().equals("Male")){
                            male.setSelected(true);
                            male.setChecked(true);
                            gendre="Male";

                        }
                        else if(user.getGendre().equals("Female")){
                            female.setChecked(true);
                            female.setSelected(true);
                            gendre="Female";
                        }
                    }
                    editEmail.setText(user.getUsers_email());
                    editCollege.setText(user.getCollegeName());
                    editAddress.setText(user.getUsers_address());

                    if (firebaseUser.isEmailVerified()) {
                        textViewEmailVerify.setText("Email Verified");
                        textViewEmailVerify.setTextColor(getResources().getColor(R.color.Ggreen));
                        user.setEmailVerified(true);
                    }

                    if (user.getProfilePicture() != null) {

                        Glide.with(getBaseContext())
                                .load(user.getProfilePicture())
                                .into(profileImage);
                        profileImageUrl = user.getProfilePicture();
                        image=1;

                    }else{
                        Toast.makeText(UpdateProfile.this, "Profile Image is not uploaded", Toast.LENGTH_SHORT).show();
                    }
                    if (num==2){
                        clickOnUpdateProfileButton();
                    }
                    progressBarInUpdateForActivity.setVisibility(View.GONE);
                    scrollInUpdate.setVisibility(View.VISIBLE);



                }
                else{
                    updateUI();
                    isNewUser = true;
                    progressBarInUpdateForActivity.setVisibility(View.GONE);
                    scrollInUpdate.setVisibility(View.VISIBLE);

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(UpdateProfile.this, databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });




    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.male:
                if (checked)
                    gendre="Male";
                break;
            case R.id.female:
                if (checked)
                    gendre="Female";
                break;
        }
    }


    private void uploadImageToFirebaseStorage() {

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Profile Image....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        final StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage != null) {
            // progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //progressBar.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();// progressBar.setVisibility(View.GONE);
                            Toast.makeText(UpdateProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            long progress = (100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(progress+"% Image is Uploaded");
                        }
                    });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                profileImage.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profileImage:
                showImageChooser();
                break;
            case R.id.btnSaveInfo:
                SaveUserInformation();
                break;
            case R.id.btnProceed:
                if (isNewUser==false){
                    startActivity(new Intent(UpdateProfile.this,MainActivity.class));
                }else {
                    Toast.makeText(this, "For proceed first fill all fields and click on save information", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnUpdate:
                clickOnUpdateProfileButton();
                break;
        }


    }

    private void sendVerificationMail() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);
                builder.setTitle("Instruction");
                builder.setMessage("Verfication Email is already sent. Please Verify Your Email");
                builder.show();
            }
        });
    }


    private void updateUI(){
        btnUpdate.setVisibility(View.GONE);
        editUser.setEnabled(true);
        editCollege.setEnabled(true);
        editAddress.setEnabled(true);
        editEmail.setEnabled(true);
        male.setEnabled(true);
        female.setEnabled(true);
        btnSaveInfo.setEnabled(true);

    }

    private void SignoutMethod() {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(UpdateProfile.this, MainActivity.class));
    }

    private void clickOnUpdateProfileButton(){

        btnUpdate.setVisibility(View.INVISIBLE);
        updateUI();

    }





}



