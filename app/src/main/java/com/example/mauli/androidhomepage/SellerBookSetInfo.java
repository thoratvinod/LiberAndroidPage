package com.example.mauli.androidhomepage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SellerBookSetInfo extends AppCompatActivity implements View.OnClickListener{

    private TextView course,price,seller_sub_List,itemCategoty,negotiable,branch,title;
    private String seller_id,seller_phone,seller_name,bookSetId;
    private DatabaseReference bookSetDatabase;
    private ImageView callImageView,messageImageView,whatsappImageView,bookSetImageView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_book_set);


        toolbar = (Toolbar) findViewById(R.id.toolbarInSellInfo);
        setSupportActionBar(toolbar);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitleTextColor(Color.WHITE);

        bookSetDatabase = FirebaseDatabase.getInstance().getReference("BookSets");

        course = (TextView) findViewById(R.id.sellerCourseDisplay);
        price = (TextView) findViewById(R.id.sellerPriceDisplay);
        seller_sub_List = (TextView) findViewById(R.id.seller_sub_ListDisplay);
        itemCategoty = (TextView) findViewById(R.id.sellerItemCategoryDisplay);
        branch = (TextView) findViewById(R.id.sellerBranchDisplay);
        negotiable = (TextView) findViewById(R.id.sellerNegotiableDisplay);
        title = (TextView) findViewById(R.id.sellerTitleDisplay);


        callImageView = (ImageView) findViewById(R.id.callImageView);
        messageImageView = (ImageView) findViewById(R.id.messageImageView);
        whatsappImageView = (ImageView) findViewById(R.id.whatsappImageView);
        bookSetImageView = (ImageView) findViewById(R.id.sellerbooksetImage);
        findViewById(R.id.btnDelete).setOnClickListener(this);

        Intent intent = getIntent();
        BookSet bookSet = intent.getParcelableExtra("BookSet");

        String subjects = bookSet.getSubjects();





        bookSetId = bookSet.getBook_set_id();

        String s_course = bookSet.getCourse_name();
        String s_price = bookSet.getPrice();
        String user_id = bookSet.getUser_id();


        course.setText("Course : "+s_course);
        price.setText("Price : "+s_price);
        seller_sub_List.setText("Subject List: \n"+bookSet.getSubjects());
        itemCategoty.setText("Item Category : "+bookSet.getItem_category());
        branch.setText("Branch : "+bookSet.getBranch_name());
        negotiable.setText("Negotiable : "+bookSet.getNegotiable());
        title.setText(bookSet.getTitle());

        if(bookSet.getBookset_image()!=null){
            Glide.with(this)
                    .load(bookSet.getBookset_image())
                    .into(bookSetImageView);
        }



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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDelete:
                Intent intent= new Intent(SellerBookSetInfo.this,MainActivity.class);
                intent.putExtra("fragement_no",2);
                startActivity(intent);
                deleteBookSet();
                break;
            case R.id.btnUpdateBookSet:
                break;
        }
    }

    private void deleteBookSet() {

        bookSetDatabase.child(bookSetId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SellerBookSetInfo.this, "Book Set is successfully removed", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void updateSellerBookSet() {

        bookSetDatabase.child(bookSetId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
