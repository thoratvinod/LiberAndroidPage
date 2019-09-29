package com.example.mauli.AndroidHomePackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;

public class CustomList extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener{

    private Spinner spinner1;
    private TextInputLayout book1,book2,book3,book4,book5,book6,book7,book8,book9,book10;
    private Button submitC,back;
    private ArrayList<String> subArrayList;
    private Switch mySwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        submitC = (Button) findViewById(R.id.submitC);
        back = (Button) findViewById(R.id.back);

        mySwitch = (Switch) findViewById(R.id.mySwitch);



        submitC.setOnClickListener(this);
        back.setOnClickListener(this);
        subArrayList = new ArrayList<>();


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

        spinner1 = (Spinner) findViewById(R.id.noOfSub);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.no_of_sub, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(com.example.mauli.sellactivitydemo.CustomList.this);
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
    private void makeArrayListofSubjects(){

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitC:
                break;
            case R.id.back:
                startActivity(new Intent(com.example.mauli.sellactivitydemo.CustomList.this,MainActivity.class));
                break;
        }
    }
}
