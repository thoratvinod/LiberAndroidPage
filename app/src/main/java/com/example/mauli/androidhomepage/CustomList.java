package com.example.mauli.androidhomepage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class CustomList extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener{

    private Spinner spinner1;
    private String subjectArray;
    private TextInputLayout book1,book2,book3,book4,book5,book6,book7,book8,book9,book10;
    private Button submitC,back;
    private EditText price,course,editBook1,editBook2,editBook3,editBook4,editBook5,editBook6,editBook7,editBook8,editBook9,editBook10,titleOfProduct;
    private Switch mySwitch;
    private Toolbar toolbar;
    private Switch aSwitch;
    private String negotiable;
    private DatabaseReference bookSetDatabase;
    private Integer count;
    private FirebaseAuth mAuth;
    String bookSetImageUrl;
    Uri uriBookSetImage;
    private ImageView book_set_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarInSellActivity);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);


        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        submitC = (Button) findViewById(R.id.submitC);
        back = (Button) findViewById(R.id.back);




        aSwitch = (Switch) findViewById(R.id.mySwitchC);
        bookSetDatabase = FirebaseDatabase.getInstance().getReference("BookSets");
        mAuth = FirebaseAuth.getInstance();
        course = (EditText) findViewById(R.id.courseC);
        price = (EditText) findViewById(R.id.priceC);
        book_set_image = (ImageView) findViewById(R.id.btnUploadImageC);


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                if (isChecked){
                    negotiable = "Yes";
                }else {
                    negotiable = "No";
                }
                // true if the switch is in the On position
            }
        });

        submitC.setOnClickListener(this);
        back.setOnClickListener(this);
        titleOfProduct = (EditText) findViewById(R.id.titleOfProduct);
        price = (EditText) findViewById(R.id.priceC);


        book1 = (TextInputLayout) findViewById(R.id.book1);
        book2 = (TextInputLayout) findViewById(R.id.book2);
        book3 = (TextInputLayout) findViewById(R.id.book3);
        book4 = (TextInputLayout) findViewById(R.id.book4);
        book5 = (TextInputLayout) findViewById(R.id.book5);
        book6 = (TextInputLayout) findViewById(R.id.book6);
        book7 = (TextInputLayout) findViewById(R.id.book7);
        book8 = (TextInputLayout) findViewById(R.id.book8);
        book9 = (TextInputLayout) findViewById(R.id.book9);
        book10 = (TextInputLayout) findViewById(R.id.book10);

        editBook1 = (EditText) findViewById(R.id.editBook1);
        editBook2 = (EditText) findViewById(R.id.editBook2);
        editBook3 = (EditText) findViewById(R.id.editBook3);
        editBook4 = (EditText) findViewById(R.id.editBook4);
        editBook5 = (EditText) findViewById(R.id.editBook5);
        editBook6 = (EditText) findViewById(R.id.editBook6);
        editBook7 = (EditText) findViewById(R.id.editBook7);
        editBook8 = (EditText) findViewById(R.id.editBook8);
        editBook9 = (EditText) findViewById(R.id.editBook9);
        editBook10 = (EditText) findViewById(R.id.editBook10);

        spinner1 = (Spinner) findViewById(R.id.noOfSub);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.no_of_sub, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(CustomList.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position==1){
            book1.setVisibility(View.VISIBLE);
            book2.setVisibility(View.GONE);
            book3.setVisibility(View.GONE);
            book4.setVisibility(View.GONE);
            book5.setVisibility(View.GONE);
            book6.setVisibility(View.GONE);
            book7.setVisibility(View.GONE);
            book8.setVisibility(View.GONE);
            book9.setVisibility(View.GONE);
            book10.setVisibility(View.GONE);
        }else if (position==2){
            book1.setVisibility(View.VISIBLE);
            book2.setVisibility(View.VISIBLE);
            book3.setVisibility(View.GONE);
            book4.setVisibility(View.GONE);
            book5.setVisibility(View.GONE);
            book6.setVisibility(View.GONE);
            book7.setVisibility(View.GONE);
            book8.setVisibility(View.GONE);
            book9.setVisibility(View.GONE);
            book10.setVisibility(View.GONE);
        }else if (position==3){
            book1.setVisibility(View.VISIBLE);
            book2.setVisibility(View.VISIBLE);
            book3.setVisibility(View.VISIBLE);
            book4.setVisibility(View.GONE);
            book5.setVisibility(View.GONE);
            book6.setVisibility(View.GONE);
            book7.setVisibility(View.GONE);
            book8.setVisibility(View.GONE);
            book9.setVisibility(View.GONE);
            book10.setVisibility(View.GONE);

        }else if (position==4){
            book1.setVisibility(View.VISIBLE);
            book2.setVisibility(View.VISIBLE);
            book3.setVisibility(View.VISIBLE);
            book4.setVisibility(View.VISIBLE);
            book5.setVisibility(View.GONE);
            book6.setVisibility(View.GONE);
            book7.setVisibility(View.GONE);
            book8.setVisibility(View.GONE);
            book9.setVisibility(View.GONE);
            book10.setVisibility(View.GONE);

        }else if (position==5) {
            book1.setVisibility(View.VISIBLE);
            book2.setVisibility(View.VISIBLE);
            book3.setVisibility(View.VISIBLE);
            book4.setVisibility(View.VISIBLE);
            book5.setVisibility(View.VISIBLE);
            book6.setVisibility(View.GONE);
            book7.setVisibility(View.GONE);
            book8.setVisibility(View.GONE);
            book9.setVisibility(View.GONE);
            book10.setVisibility(View.GONE);

        }else if (position==6){
            book1.setVisibility(View.VISIBLE);
            book2.setVisibility(View.VISIBLE);
            book3.setVisibility(View.VISIBLE);
            book4.setVisibility(View.VISIBLE);
            book5.setVisibility(View.VISIBLE);
            book6.setVisibility(View.VISIBLE);
            book7.setVisibility(View.GONE);
            book8.setVisibility(View.GONE);
            book9.setVisibility(View.GONE);
            book10.setVisibility(View.GONE);

        }else if (position==7){
            book1.setVisibility(View.VISIBLE);
            book2.setVisibility(View.VISIBLE);
            book3.setVisibility(View.VISIBLE);
            book4.setVisibility(View.VISIBLE);
            book5.setVisibility(View.VISIBLE);
            book6.setVisibility(View.VISIBLE);
            book7.setVisibility(View.VISIBLE);
            book8.setVisibility(View.GONE);
            book9.setVisibility(View.GONE);
            book10.setVisibility(View.GONE);

        }else if (position==8){
            book1.setVisibility(View.VISIBLE);
            book2.setVisibility(View.VISIBLE);
            book3.setVisibility(View.VISIBLE);
            book4.setVisibility(View.VISIBLE);
            book5.setVisibility(View.VISIBLE);
            book6.setVisibility(View.VISIBLE);
            book7.setVisibility(View.VISIBLE);
            book8.setVisibility(View.VISIBLE);
            book9.setVisibility(View.GONE);
            book10.setVisibility(View.GONE);
        }else if (position==9){
            book1.setVisibility(View.VISIBLE);
            book2.setVisibility(View.VISIBLE);
            book3.setVisibility(View.VISIBLE);
            book4.setVisibility(View.VISIBLE);
            book5.setVisibility(View.VISIBLE);
            book6.setVisibility(View.VISIBLE);
            book7.setVisibility(View.VISIBLE);
            book8.setVisibility(View.VISIBLE);
            book9.setVisibility(View.VISIBLE);
            book10.setVisibility(View.GONE);

        }else if (position==10){
            book1.setVisibility(View.VISIBLE);
            book2.setVisibility(View.VISIBLE);
            book3.setVisibility(View.VISIBLE);
            book4.setVisibility(View.VISIBLE);
            book5.setVisibility(View.VISIBLE);
            book6.setVisibility(View.VISIBLE);
            book7.setVisibility(View.VISIBLE);
            book8.setVisibility(View.VISIBLE);
            book9.setVisibility(View.VISIBLE);
            book10.setVisibility(View.VISIBLE);

        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void uploadBookSetImage(){
        final CharSequence[] items = {"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals("Camera")){

                    /*if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                REQUEST_CAMERA);
                    } else {*/

                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);




                }else if(items[which].equals("Gallery")){
                    /*Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"),SELECT_FILE);*/

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);


                }else if(items[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    uriBookSetImage = selectedImage;
                    Toast.makeText(this, uriBookSetImage.toString(), Toast.LENGTH_SHORT).show();
                    book_set_image.setImageURI(selectedImage);
                    book_set_image.setImageURI(selectedImage);

                }
                if (uriBookSetImage!=null){
                    uploadImageToFirebaseStorage();
                }else {
                    Toast.makeText(this, "Image is manadatry", Toast.LENGTH_SHORT).show();
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    uriBookSetImage = selectedImage;
                    Toast.makeText(this, uriBookSetImage.toString(), Toast.LENGTH_SHORT).show();
                    book_set_image.setImageURI(selectedImage);
                    book_set_image.setImageURI(selectedImage);
                    book_set_image.getLayoutParams().height = 175;
                    book_set_image.getLayoutParams().width = 150;
                }
                if (uriBookSetImage!=null){
                    uploadImageToFirebaseStorage();
                }else {
                    Toast.makeText(this, "Image is manadatry", Toast.LENGTH_SHORT).show();
                }
                break;
        }

       /* if(resultCode== Activity.RESULT_OK){
            if(requestCode==REQUEST_CAMERA){


                uriProfileImage = data.getData();
                Toast.makeText(SellActivity.this,uriProfileImage.toString(),Toast.LENGTH_LONG).show();
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                book_set_image.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage();



            }else if(requestCode==SELECT_FILE && resultCode == RESULT_OK && data != null && data.getData() != null){
                /*uriProfileImage = data.getData();
                Toast.makeText(SellActivity.this,uriProfileImage.toString(),Toast.LENGTH_LONG).show();
                Uri selecImageUri = data.getData();
                book_set_image.setImageURI(selecImageUri);
                uploadImageToFirebaseStorage();*/


               /* uriProfileImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                    book_set_image.setImageBitmap(bitmap);
                    Toast.makeText(SellActivity.this,uriProfileImage.toString(),Toast.LENGTH_LONG).show();

                    //uploadImageToFirebaseStorage();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }*/
    }

    private void uploadImageToFirebaseStorage() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Book Set Image....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);


        final StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriBookSetImage != null) {
            //progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriBookSetImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //progressBar.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            bookSetImageUrl = taskSnapshot.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressBar.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            Toast.makeText(CustomList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(CustomList.this,bookSetImageUrl,Toast.LENGTH_LONG).show();



        /*StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("BookSetpics/" + System.currentTimeMillis() + ".jpg");


        if (uriBookSetImage != null) {
            // progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriBookSetImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //progressBar.setVisibility(View.GONE);
                            bookSetImageUrl = taskSnapshot.getDownloadUrl().toString();
                            Toast.makeText(SellActivity.this,bookSetImageUrl,Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // progressBar.setVisibility(View.GONE);
                            Toast.makeText(SellActivity.this,"Image is not Uploaded "+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
        }
        */


    }

    private void makeArrayListofSubjects() {

        int i=1;

        if (!editBook1.getText().toString().isEmpty()){
            subjectArray = subjectArray + "\n"+i+". "+editBook1.getText().toString();
            i++;
        }
        if (!editBook2.getText().toString().isEmpty()){
            subjectArray = subjectArray + "\n"+i+". "+editBook2.getText().toString();
            i++;
        }
        if (!editBook3.getText().toString().isEmpty()){
            subjectArray = subjectArray + "\n"+i+". "+editBook3.getText().toString();
            i++;
        }
        if (!editBook4.getText().toString().isEmpty()){
            subjectArray = subjectArray + "\n"+i+". "+editBook4.getText().toString();
            i++;
        }
        if (!editBook5.getText().toString().isEmpty()){
            subjectArray = subjectArray + "\n"+i+". "+editBook5.getText().toString();
            i++;
        }
        if (!editBook6.getText().toString().isEmpty()){
            subjectArray = subjectArray + "\n"+i+". "+editBook6.getText().toString();
            i++;
        }
        if (!editBook7.getText().toString().isEmpty()){
            subjectArray = subjectArray + "\n"+i+". "+editBook7.getText().toString();
            i++;
        }
        if (!editBook8.getText().toString().isEmpty()){
            subjectArray = subjectArray + "\n"+i+". "+editBook8.getText().toString();
            i++;
        }
        if (!editBook9.getText().toString().isEmpty()){
            subjectArray = subjectArray + "\n"+i+". "+editBook9.getText().toString();
            i++;
        }
        if (!editBook10.getText().toString().isEmpty()){
            subjectArray = subjectArray + "\n"+i+". "+editBook10.getText().toString();
            i++;
        }

        count = i-1;



    }

    private void AddBookSet() {


        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String id = bookSetDatabase.push().getKey();
        BookSet bookSet = new BookSet(titleOfProduct.getText().toString(),negotiable,id," - ",course.getText().toString(), " - ", " - ",price.getText().toString(), bookSetImageUrl,firebaseUser.getUid().toString(),subjectArray,count.toString());

        bookSetDatabase.child(id).setValue(bookSet).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CustomList.this, "Book Set is successfully Added", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(CustomList.this,MainActivity.class);
                intent.putExtra("fragement_no",2);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitC:
                makeArrayListofSubjects();
                AddBookSet();
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.btnUploadImage:
                uploadBookSetImage();
                break;
        }
    }



    /*@Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                makeArrayListofSubjects();
                AddBookSet();
            break;
            case R.id.customize:
                startActivity(new Intent(SellActivity.this,CustomList.class));
                break;

            case R.id.btnUploadImage:
                uploadBookSetImage();
                break;

        }
    }*/




}
