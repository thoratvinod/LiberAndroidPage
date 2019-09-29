package com.example.mauli.androidhomepage;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookSetInfo extends AppCompatActivity {

    private TextView course,price,item_category,year,brach,subList,negotiable,sellerName,title;
    private String seller_id,seller_phone,seller_name;
    private DatabaseReference userDatabase;
    private ImageView callImageView,messageImageView,whatsappImageView,bookSetImageView;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_set_info);

        userDatabase = FirebaseDatabase.getInstance().getReference("User_Information");

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarInSetInfo);
        setSupportActionBar(toolbar);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitleTextColor(Color.WHITE);


        course = (TextView) findViewById(R.id.courseDisplay);
        price = (TextView) findViewById(R.id.priceDisplay);
        item_category = (TextView) findViewById(R.id.item_categoryDisplay);
        year = (TextView) findViewById(R.id.yearDisplay);
        brach = (TextView) findViewById(R.id.branchDisplay);
        subList = (TextView) findViewById(R.id.subListDisplay);
        negotiable = (TextView) findViewById(R.id.negotiableDisplay);
        sellerName = (TextView) findViewById(R.id.sellerName);
        bookSetImageView = (ImageView) findViewById(R.id.booksetImage);
        callImageView = (ImageView) findViewById(R.id.callImageView);
        messageImageView = (ImageView) findViewById(R.id.messageImageView);
        whatsappImageView = (ImageView) findViewById(R.id.whatsappImageView);
        title = (TextView) findViewById(R.id.titleDisplay);

        Intent intent = getIntent();
        BookSet bookSet = intent.getParcelableExtra("BookSet");

        course.setText(bookSet.getCourse_name());;
        price.setText(bookSet.getPrice());;
        item_category.setText(bookSet.getItem_category());
        year.setText(bookSet.getYear());
        brach.setText(bookSet.getBranch_name());
        subList.setText(bookSet.getSubjects());
        negotiable.setText(bookSet.getNegotiable());
        sellerName.setText(seller_name);
        title.setText(bookSet.getTitle());


        String user_id = bookSet.getUser_id();
        String booksetImage = bookSet.getBookset_image();


        if(booksetImage!=null){
            Glide.with(this)
                    .load(booksetImage)
                    .into(bookSetImageView);
        }


        seller_id = bookSet.getUser_id();

        getInfoOfUser();

        callImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCalltoSeller();
            }
        });
        messageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSmstoSeller();
            }
        });
        whatsappImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeWhatsapptoSeller();
            }
        });







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

    private void makeSmstoSeller() {

        
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"+seller_phone));
        startActivity(sendIntent);

    }

    private void makeCalltoSeller(){

        Toast.makeText(this,"Phone :-"+seller_phone+"\nName : "+seller_name+" ",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+seller_phone));
        startActivity(intent);


    }
    private void makeWhatsapptoSeller(){
        Toast.makeText(this,"Phone :-"+seller_phone+"\nName : "+seller_name+" ",Toast.LENGTH_LONG).show();

        try{
            Uri uri = Uri.parse("smsto:" + seller_phone);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(i, ""));
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }


    private void getInfoOfUser(){



        userDatabase.child("My_App_User").child(seller_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                seller_phone = user.getPhone_no();
                seller_name = user.getUsers_name();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
