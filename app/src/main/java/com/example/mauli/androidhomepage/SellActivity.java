package com.example.mauli.androidhomepage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;

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

import java.io.IOException;
import java.util.ArrayList;

public class SellActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    private String item_category,course_name,branch_name,year,price,negotiable,subjects;
    private Button btnSubmit,btnUploadImage;
    private ImageView book_set_image;
     Uri uriBookSetImage;
    Integer REQUEST_CAMERA = 1,SELECT_FILE=0;
    private FirebaseAuth mAuth;
     String bookSetImageUrl;
    DatabaseReference databaseBooksSets;

    private ProgressDialog progressDialog1;
    private Spinner spinner1, spinner2, spinner3, spinner4, spinner5;
    private String[] year_array, branch_array;
    private LinearLayout checkboxGroup,electiveGrp;
    private CheckBox sub1, sub2, sub3, sub4, sub5, sub6, sub7, sub8, sub9, sub10, mcq, others;
    private Button customize,submit;
    private EditText e1,e2,e3,e4,editPrice,titleBookSet;
    private TextInputLayout elective1,elective2,elective3,elective4;
    private Switch aSwitch;
    private Integer count;
    private android.support.v7.widget.Toolbar toolbar;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        databaseBooksSets = FirebaseDatabase.getInstance().getReference("BookSets");

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarInSellActivity);
        setSupportActionBar(toolbar);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        book_set_image = (ImageView) findViewById(R.id.btnUploadImage);
        titleBookSet = (EditText) findViewById(R.id.titleBookSet);

        toolbar.setTitleTextColor(Color.WHITE);



        mAuth = FirebaseAuth.getInstance();



        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        aSwitch = (Switch) findViewById(R.id.mySwitch);

        findViewById(R.id.btnUploadImage).setOnClickListener(this);


        year_array = new String[]{};
        branch_array = new String[]{};

        customize = (Button) findViewById(R.id.customize);
        submit = (Button) findViewById(R.id.submit);

        editPrice = (EditText) findViewById(R.id.price);

        customize.setOnClickListener(this);
        submit.setOnClickListener(this);

        electiveGrp = (LinearLayout) findViewById(R.id.electalGrp);
        elective1 = (TextInputLayout) findViewById(R.id.elective1);
        elective2 = (TextInputLayout) findViewById(R.id.elective2);
        elective3 = (TextInputLayout) findViewById(R.id.elective3);
        elective4 = (TextInputLayout) findViewById(R.id.elective4);


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

        //CheckBoxGroup

        checkboxGroup = (LinearLayout) findViewById(R.id.chechboxGroup);

        //CheckBoxes

        sub1 = (CheckBox) findViewById(R.id.sub1);
        sub2 = (CheckBox) findViewById(R.id.sub2);
        sub3 = (CheckBox) findViewById(R.id.sub3);
        sub4 = (CheckBox) findViewById(R.id.sub4);
        sub5 = (CheckBox) findViewById(R.id.sub5);
        sub6 = (CheckBox) findViewById(R.id.sub6);
        sub7 = (CheckBox) findViewById(R.id.sub7);
        sub8 = (CheckBox) findViewById(R.id.sub8);
        sub9 = (CheckBox) findViewById(R.id.sub9);
        sub10 = (CheckBox) findViewById(R.id.sub10);
        mcq = (CheckBox) findViewById(R.id.mcq);
        others = (CheckBox) findViewById(R.id.others);

        e1 = (EditText) findViewById(R.id.e1);
        e2 = (EditText) findViewById(R.id.e2);
        e3 = (EditText) findViewById(R.id.e3);
        e4 = (EditText) findViewById(R.id.e4);



        spinner1 = (Spinner) findViewById(R.id.item_category);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.item_category, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        spinner2 = (Spinner) findViewById(R.id.course);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.course_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        spinner3 = (Spinner) findViewById(R.id.year);
        spinner3.setOnItemSelectedListener(this);

        spinner4 = (Spinner) findViewById(R.id.branch);
        spinner4.setOnItemSelectedListener(this);


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

    private void validations(){

        /*titleBookSet.setError(null);
        editPrice.setError(null);

        if (bookSetImageUrl==null) {
            Toast toast = Toast.makeText(this, "Please Upload Photo", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if (titleBookSet.getText().toString().isEmpty()){

            titleBookSet.setError("Title of Product is Required");
            titleBookSet.setFocusable(true);
            return false;
        }
        if (titleBookSet.getText().toString().length()<10){

            titleBookSet.setError("Title should be Informative");
            titleBookSet.setFocusable(true);
            return false;
        }
        if (editPrice.getText().toString().isEmpty()){

            editPrice.setError("Price is Required");
            editPrice.setFocusable(true);
            return false;
        }
        if (course_name.equals("")||branch_name.equals("")){
            Toast toast = Toast.makeText(this, "Enter All fields", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        return true;*/

        }
    private void AddBookSet(){
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String id = databaseBooksSets.push().getKey();
        BookSet bookSet = new BookSet(titleBookSet.getText().toString(),negotiable,id,item_category,course_name, branch_name, year,editPrice.getText().toString(), bookSetImageUrl,firebaseUser.getUid().toString(), subjects,count.toString());

        databaseBooksSets.child(id).setValue(bookSet).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                progressDialog1.dismiss();
                Toast.makeText(SellActivity.this, "Book Set is successfully Added", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(SellActivity.this,MainActivity.class);
                intent.putExtra("fragement_no",2);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

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
                            Toast.makeText(SellActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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



        /*StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("BookSetpics/" + System.currentTimeMillis() + ".jpg");


        if (uriBookSetImage != null) {
            // progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriBookSetImage)
                    .OnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.item_category:
                if (position == 1) {

                    item_category = "Books";
                    spinner2.setVisibility(View.VISIBLE);
                    spinner3.setVisibility(View.GONE);
                    spinner4.setVisibility(View.GONE);
                    checkboxGroup.setVisibility(View.GONE);
                    electiveGrp.setVisibility(View.GONE);



                } else if (position == 2) {


                    item_category = "Instruments";
                    spinner2.setVisibility(View.GONE);
                    spinner3.setVisibility(View.GONE);
                    spinner4.setVisibility(View.GONE);
                    checkboxGroup.setVisibility(View.GONE);

                }
                break;
            case R.id.course:
                if (position == 1) {

                    spinner3.setVisibility(View.GONE);
                    spinner4.setVisibility(View.GONE);
                    checkboxGroup.setVisibility(View.GONE);


                } else if (position == 2) {


                    course_name = "Engineering";
                    year_array = new String[]{"Select Year", "First Year(FE)", "Second Year(SE)", "Third Year(TE)", "Last Year(BE)"};
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, year_array);
                    spinner3.setAdapter(adapter3);
                    spinner3.setVisibility(View.VISIBLE);

                }else if (position==3){
                    spinner3.setVisibility(View.GONE);
                    spinner4.setVisibility(View.GONE);
                    checkboxGroup.setVisibility(View.GONE);

                }else if (position==4){
                    spinner3.setVisibility(View.GONE);
                    spinner4.setVisibility(View.GONE);
                    checkboxGroup.setVisibility(View.GONE);

                }else if (position==5){
                    spinner3.setVisibility(View.GONE);
                    spinner4.setVisibility(View.GONE);
                    checkboxGroup.setVisibility(View.GONE);

                }
                break;

            case R.id.year:
                if (position == 1) {

                    year = "First Year(FE)";
                    spinner4.setVisibility(View.GONE);
                    checkboxGroup.setVisibility(View.VISIBLE);
                    setUpSubjectsInCheckBox(position, year);


                } else if (position == 2) {

                    year = "Second Year(SE)";
                    branch_array = new String[]{"Select Branch", "Chemical", "Civil", "Computer", "Electrical", "E & TC ", "Information Technoloagy", "Instrumentation & Control", "Mechanical "};
                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, branch_array);
                    spinner4.setAdapter(adapter4);
                    spinner4.setVisibility(View.VISIBLE);
                    checkboxGroup.setVisibility(View.GONE);


                } else if (position == 3) {

                    year = "Third Year(TE)";
                    branch_array = new String[]{"Select Branch", "Chemical", "Civil", "Computer", "Electrical", "E & TC ", "Information Technoloagy", "Instrumentation & Control", "Mechanical "};
                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, branch_array);
                    spinner4.setAdapter(adapter4);
                    spinner4.setVisibility(View.VISIBLE);
                    checkboxGroup.setVisibility(View.GONE);

                } else if (position == 4) {

                    year = "Last Year(BE)";
                    branch_array = new String[]{"Select Branch", "Chemical", "Civil", "Computer", "Electrical", "E & TC ", "Information Technoloagy", "Instrumentation & Control", "Mechanical "};
                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, branch_array);
                    spinner4.setAdapter(adapter4);
                    spinner4.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.branch:
                setUpSubjectsInCheckBox(position, year);
                break;


        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setUpSubjectsInCheckBox(int position, String year) {




        if (year == "First Year(FE)") {

            electiveGrp.setVisibility(View.GONE);
            checkboxGroup.setVisibility(View.VISIBLE);


            sub1.setText("ENGINEERING MATHEMATICS-I ");
            sub2.setText("ENGINEERING CHEMISTRY");
            sub3.setText("ENGINEERING PHYSICS");
            sub4.setText("BASIC ELECTRONICS ENGINEERING ");
            sub5.setText("BASIC Electrical ENGINEERING");
            sub6.setText("BASIC Civil AND ENVIRONMENTAL ENGINEERING ");
            sub7.setText("ENGINEERING GRAPHICS-I ");
            sub8.setText("ENGINEERING MATHEMATICS-II ");
            sub9.setText("ENGINEERING MECHANICS ");
            sub10.setText("BASIC MECHANICAL ENGINEERING ");



        }else if (year == "Second Year(SE)") {
            if (position == 1) {

                electiveGrp.setVisibility(View.GONE);
                branch_name = "Chemical";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);
                //Set up the subjects

                sub1.setText("Process Calculations");
                sub2.setText("Engineering Mathematics-III");
                sub3.setText("Chemistry-I");
                sub4.setText("Fluid Mechanics");
                sub5.setText("Engineering Materials");
                sub6.setText("Chemistry-II");
                sub7.setText("Heat Transfer");
                sub8.setText("Principles of Design");
                sub9.setText("Chemical Engineering Thermodynamics-I");
                sub10.setText("Mechanical Operations");





            } else if (position == 2) {
                branch_name = "Civil";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Engineering Mathematics III");
                sub2.setText("Building Technology and Materials");
                sub3.setText("Strength of Materials");
                sub4.setText("Surveying");
                sub5.setText("Geotechnical Engineering");
                sub6.setText("Fluid Mechanics - I");
                sub7.setText("Architechtural Planning and Design of Buildings");
                sub8.setText("Concrete Technology");
                sub9.setText("Structural Analysis-I");
                sub10.setText("Engineering Geology");




            } else if (position == 3) {
                branch_name = "Computer";
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Discrete Mathematics");
                sub2.setText("Digital Electronics & Logic Design");
                sub3.setText("Data Structures & Algorithms");
                sub4.setText("Computer Organization and Architecture ");
                sub5.setText("Object Oriented Programming");
                sub6.setText("Engineering Mathematics-III");
                sub7.setText("Computer Graphics");
                sub8.setText("Advanced Data Structures");
                sub9.setText("Microprocessor");
                sub10.setText("Principles of Programming Languages");



            } else if (position == 4) {
                branch_name = "Electrical";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Engineering Mathematics- III");
                sub2.setText("Power Generation Technologies");
                sub3.setText("Analog and Digital Electronics");
                sub4.setText("Material Science");
                sub5.setText("Electrical Measurements and Instrumentation ");
                sub6.setText("Power System-I");
                sub7.setText("Electrical Machines-I");
                sub8.setText("Network Analysis");
                sub9.setText("Numerical Methods and Computer Programming");
                sub10.setText("Fundamentals of Microcontroller and Applications");




            } else if (position == 5) {
                branch_name = "E & TC";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Signals & Systems");
                sub2.setText("Electronic Devices & Circuits");
                sub3.setText("Electrical Circuits and Machines");
                sub4.setText("Data Structures & Algorithms");
                sub5.setText("Digital Electronics");
                sub6.setText("Analog Communication");
                sub7.setText("Engineering Mathematics-III");
                sub8.setText("Integrated Circuits");
                sub9.setText("Control Systems");
                sub10.setText("Object Oriented Programming");



            } else if (position == 6) {
                branch_name = "Information Technoloagy";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Discrete Structures");
                sub2.setText("Computer Organization & Architecture");
                sub4.setText("Digital Electronics and Logic Design");
                sub3.setText("Fundamentals of Data Structures");
                sub5.setText("Problem Solving and Object Oriented Programming");
                sub6.setText("Engineering Mathematics -III");
                sub7.setText("Computer Graphics");
                sub8.setText("Processor Architecture and Interfacing");
                sub9.setText("Data Structures & Files");
                sub10.setText("Foundations of Communication and Computer Network");





            } else if (position == 7) {
                branch_name = "Instrumentation & Control";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Engineering Mathematics III ");
                sub2.setText("Sensors And Transducers-I ");
                sub3.setText("Basic Instrumentation");
                sub4.setText("Linear Integrated Circuits");
                sub5.setText("Network Theory ");
                sub6.setText("Sensors & Transducers-II");
                sub7.setText("Automatic Control Systems ");
                sub8.setText("Electronic Instrumentation");
                sub9.setText("Digital Techniques");
                sub10.setText("Industrial Drives");




            } else if (position == 8) {
                branch_name = "Mechanical";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText(" Engineering Mathematics III ");
                sub2.setText("Thermodynamics ");
                sub3.setText("Manufacturing Process-I");
                sub4.setText("Material Science");
                sub5.setText("Strength of Materials");
                sub6.setText("Fluid Mechanics");
                sub7.setText(" Electrical and Electronics Engineering");
                sub8.setText("Applied Thermodynamics");
                sub9.setText("Theory of Machines-I ");
                sub10.setText("Engineering Metallurgy");


            }
        }else if (year == "Third Year(TE)") {
            if (position == 1) {
                electiveGrp.setVisibility(View.GONE);
                branch_name = "Chemical";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);
                //Set up the subjects

                sub1.setText("Chemical Engineering Mathematics");
                sub2.setText("Mass Transfer I");
                sub3.setText("Industrial Organization and Management ");
                sub4.setText("Chemical Process Technology");
                sub5.setText("Chemical Engineering Thermodynamics II");
                sub6.setText("Chemical Reaction Engineering I");
                sub7.setText("Transport Phenomena ");
                sub8.setText("Chemical Engineering Design I ");
                sub9.setText("Mass Transfer II ");
                sub10.setText("Process Instrumentation and Control");




            } else if (position == 2) {
                branch_name = "Civil";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Hydrology and water resource engineering");
                sub2.setText("Infrastructure Engineering and Construction Techniques");
                sub3.setText("Structural Design-I");
                sub4.setText("Structural Analysis-II ");
                sub5.setText("Fluid Mechanics-II");
                sub6.setText("Advanced Surveying");
                sub7.setText("Project Management and Engineering Economics");
                sub8.setText("Foundation Engineering");
                sub9.setText("Structural Design-II");
                sub10.setText("Environmental Engineering-I ");




            } else if (position == 3) {
                branch_name = "Computer";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Theory of Computation");
                sub2.setText("Database Management Systems (DBMS)");
                sub3.setText("Software Engineering and Project Management ");
                sub4.setText("Information Systems and Engineering Economics");
                sub5.setText("Computer Networks (CN) ");
                sub6.setText("Design and Analysis of Algorithms");
                sub7.setText("Systems Programming and Operating System (SP and OS)");
                sub8.setText("Embedded Systems and Internet of Things (ES and IoT)");
                sub9.setText("Software Modeling and Design");
                sub10.setText("Web Technology");




            } else if (position == 4) {
                branch_name = "Electrical";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Industrial and Technology Management");
                sub2.setText("Advance Microcontroller and its Applications");
                sub3.setText("Electrical Machines II");
                sub4.setText("Power Electronics");
                sub5.setText("Electrical Installation, Maintenance and Testing");
                sub6.setText("Power System II ");
                sub7.setText("Control System I ");
                sub8.setText("Utilization of Electrical Energy");
                sub9.setText("Design of Electrical Machines");
                sub10.setText("Energy Audit and Management");




            } else if (position == 5) {
                branch_name = "E & TC";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Digital Communication ");
                sub2.setText("Digital Signal Processing");
                sub3.setText("Electromagnetics");
                sub4.setText("Microcontrollers");
                sub5.setText("Mechatronics");
                sub6.setText("Power Electronics");
                sub7.setText("Information Theory, Coding and Communication Networks");
                sub8.setText("Business Management");
                sub9.setText("Advanced Processors");
                sub10.setText("System Programming and Operating Systems");




            } else if (position == 6) {
                branch_name = "Information Technoloagy";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Theory of Computation");
                sub2.setText("Database Management Systems");
                sub3.setText("Software Engineering and Project Management");
                sub4.setText("Operating System ");
                sub5.setText("Human-Computer Interaction");
                sub6.setText("Computer Network Technology");
                sub7.setText("Systems Programming");
                sub8.setText("Design and Analysis of Algorithms");
                sub9.setText("Cloud Computing");
                sub10.setText("Data Science and Big Data Analytics");



            } else if (position == 7) {
                branch_name = "Instrumentation & Control";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Embedded System Design");
                sub2.setText("Instrumental Methods for Chemical Analysis");
                sub3.setText("Control System Components");
                sub4.setText("Control System Design");
                sub5.setText("Industrial Organisation and Management");
                sub6.setText("Digital Signal Processing");
                sub7.setText("Process Loop Components");
                sub8.setText("Unit Operations and Power Plant Instrumentation");
                sub9.setText("Instrument and System Design");
                sub10.setText("Bio- Medical Instrumentation");




            } else if (position == 8) {
                branch_name = "MECHANICAL";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Design of Machine Elements-I");
                sub2.setText("Heat Transfer");
                sub3.setText("Theory of Machines-II");
                sub4.setText("Turbo Machines");
                sub5.setText("Metrology and Quality Control ");
                sub6.setText(" Mechatronics");
                sub7.setText("Numerical Methods and Optimization");
                sub8.setText("Manufacturing - Process-II");
                sub9.setText("Design of Machine Elements-II");
                sub10.setText("Refrigeration and Air Conditioning");


            }
        }else if (year == "Last Year(BE)"){

            electiveGrp.setVisibility(View.VISIBLE);
            if (position == 1) {
                branch_name = "Chemical";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Process Dynamics And Control ");
                sub2.setText("Chemical Reaction Engineering-II");
                sub3.setText("Chemical Engineering Design-II ");
                sub4.setText("ELECTIVE-I");
                sub5.setText("ELECTIVE-II ");
                sub6.setText("Process Modelling & Simulation");
                sub7.setText("Process Engineering Costing & Plant Design");
                sub8.setText("ELECTIVE-III ");
                sub9.setText("ELECTIVE-IV");
                sub10.setVisibility(View.GONE);



            } else if (position == 2) {
                branch_name = "Civil";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Environmental Engineering - II (401001)");
                sub2.setText("Transportation Engineering");
                sub3.setText("Structural Design And Drawing - III");
                sub4.setText("ELECTIVE-I");
                sub5.setText("ELECTIVE-II ");
                sub6.setText("Dams And Hydraulic Structures");
                sub7.setText("Quantity Surveying contract And Tenders ");
                sub8.setText("ELECTIVE-III");
                sub9.setText("ELECTIVE-IV");
                sub10.setVisibility(View.GONE);




            } else if (position == 3) {
                branch_name = "Computer";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Design & Analysis Of Algorithms");
                sub2.setText("Principles Of Modern Compiler Design ");
                sub3.setText("Smart System Design And Applications \n");
                sub4.setText("ELECTIVE-I");
                sub5.setText("ELECTIVE-Ii");
                sub6.setText("Software Design Methodology And Testing \n");
                sub7.setText("High Performance Computing");
                sub8.setText("ELECTIVE-III");
                sub9.setText("ELECTIVE-IV ");
                sub10.setVisibility(View.GONE);




            } else if (position == 4) {
                branch_name = "Electrical";

                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Power System Operation And Control");
                sub2.setText("PLC And SCADA Applications");
                sub3.setText("Control System - II ");
                sub4.setText("ELECTIVE-I ");
                sub5.setText("ELECTIVE-II");
                sub6.setText("Switchgear And Protection");
                sub7.setText("Power Electronic Controlled Drives");
                sub8.setText("ELECTIVE-III");
                sub9.setText("ELECTIVE-IV");
                sub10.setVisibility(View.GONE);




            } else if (position == 5) {
                branch_name = "E & TC";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("VLSI Design & Technology");
                sub2.setText("Computer Networks");
                sub3.setText("Microwave Engineering");
                sub4.setText("ELECTIVE-I");
                sub5.setText("ELECTIVE-II");
                sub6.setText("Mobile Communication");
                sub7.setText("Broadband Communication Systems");
                sub8.setText("ELECTIVE-III");
                sub9.setText("ELECTIVE-IV");
                sub10.setVisibility(View.GONE);


            } else if (position == 6) {
                branch_name = "Information Technoloagy";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);
                //Set up the subjects

                sub1.setText("Information And Cyber Security");
                sub2.setText("Software Modeling And Design");
                sub3.setText("Machine Learning");
                sub4.setText("ELECTIVE-I");
                sub5.setText("ELECTIVE-II");
                sub6.setText("Distributed System");
                sub7.setText("Advanced Databases");
                sub8.setText("ELECTIVE-III");
                sub9.setText("ELECTIVE-IV");
                sub10.setVisibility(View.GONE);



            } else if (position == 7) {
                branch_name = "Instrumentation & Control";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);
                //Set up the subjects

                sub1.setText("Process Instrumentation- I");
                sub2.setText("Project Engineering & Management");
                sub3.setText("Digital Control");
                sub4.setText("ELECTIVE-I");
                sub5.setText("ELECTIVE-II ");
                sub6.setText("Process Instrumentation- II ");
                sub7.setText("Industrial Automation ");
                sub8.setText("ELECTIVE-III");
                sub9.setText("ELECTIVE-IV");
                sub10.setVisibility(View.GONE);


            } else if (position == 8) {
                branch_name = "Mechanical";
                unchechedAll();
                checkboxGroup.setVisibility(View.VISIBLE);

                //Set up the subjects

                sub1.setText("Refrigeration And Air Conditioning");
                sub2.setText("CAD/CAM Automation");
                sub3.setText("Dynamics Of Machinery");
                sub4.setText("ELECTIVE-");
                sub5.setText("ELECTIVE-II ");
                sub6.setText("Power Plant Engineering");
                sub7.setText("Mechanical System Design");
                sub8.setText("ELECTIVE-III ");
                sub9.setText("ELECTIVE-IV ");
                sub10.setVisibility(View.GONE);


            }
        }
    }



    private void unchechedAll(){

        sub1.setChecked(false);
        sub2.setChecked(false);
        sub3.setChecked(false);
        sub4.setChecked(false);
        sub5.setChecked(false);
        sub6.setChecked(false);
        sub7.setChecked(false);
        sub8.setChecked(false);
        sub9.setChecked(false);
        sub10.setChecked(false);
        mcq.setChecked(false);
        others.setChecked(false);



    }
    private void makeArrayListofSubjects(){
        subjects="\n";
        count = 0;
        int i=1;
        if(sub1.isChecked()){
            subjects = subjects + i+". " +sub1.getText().toString()+"\n";
            i++;
        }
        if(sub2.isChecked()){
            subjects = subjects + i+". " +sub2.getText().toString()+"\n";
            i++;
        }
        if(sub3.isChecked()){
            subjects = subjects + i+". " +sub3.getText().toString()+"\n";
            i++;
        }
        if(sub4.isChecked()){
            if(year=="Last Year(BE)"){

                if(e1.getText()!=null){
                    subjects = subjects + i+". " +e1.getText().toString()+"\n";
                    i++;
                }

            }else{
                subjects = subjects + i+". " +sub4.getText().toString()+"\n";
                i++;

            }
        }
        if(sub5.isChecked()){
            if(year=="Last Year(BE)"){

                if(e2.getText()!=null){
                    subjects = subjects + i+". " +e2.getText().toString()+"\n";
                    i++;
                }

            }else{
                subjects = subjects + i+". " +sub5.getText().toString()+"\n";
                i++;


            }
        }
        if(sub6.isChecked()){
            subjects = subjects + i+". " +sub6.getText().toString()+"\n";


        }
        if(sub7.isChecked()){
            subjects = subjects + i+". " +sub7.getText().toString()+"\n";
            i++;
        }
        if(sub8.isChecked()){
            subjects = subjects + i+". " +sub8.getText().toString()+"\n";
            i++;

        }
        if(sub9.isChecked()){
            if(year=="Last Year(BE)"){

                if(e3.getText()!=null){
                    subjects = subjects + i+". " +sub3.getText().toString()+"\n";
                    i++;

                }

            }else{
                subjects = subjects + i+". " +sub9.getText().toString()+"\n";
                i++;


            }

        }
        if(sub10.isChecked()){
            if(year=="Last Year(BE)"){

                if(e4.getText()!=null){
                    subjects = subjects + i+". " +e4.getText().toString()+"\n";
                    i++;

                }

            }else{
                subjects = subjects + i+". " +sub10.getText().toString()+"\n";
                i++;


            }
        }
        if(mcq.isChecked()){
            subjects = subjects + "* " +mcq.getText().toString()+"\n";
        }
        if(others.isChecked()){
            subjects = subjects + "* " +others.getText().toString();
        }
        count = i-1;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                //if (validations()){
                    progressDialog1 = new ProgressDialog(this);
                    progressDialog1.setMessage("Please wait");
                    progressDialog1.setMessage("Please wait");
                    makeArrayListofSubjects();
                    AddBookSet();
               // }
            break;
            case R.id.customize:
                startActivity(new Intent(SellActivity.this,CustomList.class));
                break;

            case R.id.btnUploadImage:
                uploadBookSetImage();
                break;

        }
    }

}

