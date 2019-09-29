package com.example.mauli.androidhomepage;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddEBook extends AppCompatActivity implements View.OnClickListener{

    private static final int PICK_IMAGE_REQUEST = 234;
    private ImageView upload_image,check;
    private Button btnAdd;
    private Uri filePath;
    private StorageReference storageReference;
    private DatabaseReference eBookDatabase;
    private String eBookId;
    private EditText name,author;
    private Toolbar toolbar;
    private TextInputLayout textName,textAuthor;
    private ProgressDialog progressDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ebook);

        check = (ImageView) findViewById(R.id.check);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarInAddEbook);
        setSupportActionBar(toolbar);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitleTextColor(Color.WHITE);

        upload_image = (ImageView) findViewById(R.id.upload_image);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        eBookDatabase = FirebaseDatabase.getInstance().getReference("E-Books");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        name = (EditText) findViewById(R.id.name);
        author = (EditText) findViewById(R.id.author);

        textName = (TextInputLayout) findViewById(R.id.textName);
        textAuthor = (TextInputLayout) findViewById(R.id.textAuthor);

        storageReference = FirebaseStorage.getInstance().getReference();

        upload_image.setOnClickListener(this);
        btnAdd.setOnClickListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Integer id = item.getItemId();

        if (id == 16908332){


            onBackPressed();
            return true;
            //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        }


        return super.onOptionsItemSelected(item);
    }

    private void addEBook(){

        eBookId= eBookDatabase.push().getKey();
        EBook eBook = new EBook(name.getText().toString(),author.getText().toString(),eBookId);
        eBookDatabase.child(eBookId).setValue(eBook).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddEBook.this, "Database is Updated", Toast.LENGTH_SHORT).show();
            }
        });



    }
    private void validations(){

        /*if (filePath == null){
            Toast toast = Toast.makeText(this, "Select on pdf by clicking on icon", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if (name.getText().toString().isEmpty()){
            textName.setError("Book Name is Manadatory");
            textName.requestFocus();
            return false;

        }
        if (name.getText().toString().length()<5){
            textName.setError("Enter valid Name");
            textName.requestFocus();
            return false;
        }
        if (author.getText().toString().isEmpty()){
            textAuthor.setError("Author is Manadatory");
            textAuthor.requestFocus();
            return false;
        }
        if (author.getText().toString().length()<5){
            textAuthor.setError("Enter Full Bame OF author");
            textAuthor.requestFocus();
            return false;
        }
        return true;*/

    }
    private void uploadEBook(){

        StorageReference eBookReference = storageReference.child("Documents/"+eBookId);

        if(filePath!=null){

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading E-Book....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            eBookReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddEBook.this, "E-Book is successfully uploaded ", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(AddEBook.this,MainActivity.class);
                            intent.putExtra("fragement_no",3);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(AddEBook.this, exception.getMessage(), Toast.LENGTH_SHORT).show();                        // ...
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            long progress = (100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setProgress((int) progress);
                        }
                    });


        }else {
            Toast.makeText(this, "please select file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload_image:
                showFileChooser();
                break;
            case R.id.btnAdd:
                //if (validations()){
                    addEBook();
                    uploadEBook();
                    break;
                //}

        }
    }
    public void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an Document"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null &&data.getData()!=null){

            filePath = data.getData();
            check.setVisibility(View.VISIBLE);

        }
    }
}
