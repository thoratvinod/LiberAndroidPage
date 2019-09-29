package com.example.mauli.androidhomepage;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchViewActivity extends AppCompatActivity implements SearchView.OnQueryTextListener  {

    private RecyclerView recyclerView;
    private List<BookSet> bookSetList;
    private ProgressBar progressbarInSearchView;
    private BookSetAdapter recyclerAdapter;
    private DatabaseReference bookSetDatabase;
    private SearchView searchView;
    private Toolbar toolbar;
    List<String> idSet;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        progressbarInSearchView = (ProgressBar) findViewById(R.id.progressbarInSearchView);

        toolbar = (Toolbar) findViewById(R.id.toolbarInSearch);
        setSupportActionBar(toolbar);

        idSet = new ArrayList<>();


        bookSetDatabase = FirebaseDatabase.getInstance().getReference("BookSets");

        bookSetList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewInSearchActivity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getBookSetInList();



    }


    private void getBookSetInList(){

        bookSetDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                bookSetList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    BookSet bookSet = postSnapshot.getValue(BookSet.class);
                    bookSetList.add(bookSet);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_layout,menu);

        MenuItem menuItem = menu.findItem(R.id.searchIconInMain);
        menuItem.expandActionView();
        searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                startActivity(new Intent(SearchViewActivity.this,MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            }
        });


        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        progressbarInSearchView.setVisibility(View.VISIBLE);
        List<BookSet> newBookSetList = new ArrayList<>();

        String userInput = s.toLowerCase().trim();

        /*int flag = 0;


        boolean verified = true;

        String id;

        String[] userInput = s.split(" ");
        for (String a : userInput){

            String splitInput = a.toLowerCase();

            for(BookSet bookSet : bookSetList)
            {
                flag = 0;
                verified = verifyIdSet(bookSet.getBook_set_id());

                if (verified){
                    if (bookSet.getSubjects().contains(splitInput)){

                        if (bookSet.getYear().contains(splitInput)){

                            if (bookSet.getBranch_name().contains(splitInput)){


                                if (bookSet.getCourse_name().contains(splitInput)){

                                    newBookSetList.add(bookSet);
                                    idSet.add(bookSet.getBook_set_id());
                                    flag = 1;


                                }else {
                                    newBookSetList.add(bookSet);
                                    if (flag!=1){
                                        idSet.add(bookSet.getUser_id());
                                        flag=1;
                                    }
                                }

                            }else{
                                newBookSetList.add(bookSet);
                                if (flag!=1){
                                    idSet.add(bookSet.getUser_id());
                                    flag=1;
                                }
                            }

                        }else{
                            newBookSetList.add(bookSet);
                            if (flag!=1){
                                idSet.add(bookSet.getUser_id());
                                flag=1;
                            }
                        }
                    }else{
                        newBookSetList.add(bookSet);
                        if (flag!=1){
                            idSet.add(bookSet.getUser_id());
                            flag=1;
                        }
                    }
                }
            }

        }

        Toast.makeText(this, newBookSetList.toString(), Toast.LENGTH_SHORT).show();

        if(newBookSetList==null){
            Toast.makeText(SearchViewActivity.this, "No Result Found", Toast.LENGTH_SHORT).show();
        }else{
            progressbarInSearchView.setVisibility(View.GONE);
            recyclerAdapter = new BookSetAdapter(this,newBookSetList);
            recyclerView.setAdapter(recyclerAdapter);
            searchView.clearFocus();

        }*/




        if(userInput.length()>3){
            for(BookSet bookSet : bookSetList)
            {
                if(bookSet.getCourse_name().toLowerCase().contains(userInput)
                        || bookSet.getBranch_name().toLowerCase().contains(userInput)
                        || bookSet.getYear().toLowerCase().contains(userInput)
                        || bookSet.getTitle().toLowerCase().contains(userInput))
                {
                    newBookSetList.add(bookSet);
                }
            }
        }else{
            Toast.makeText(SearchViewActivity.this, "Please Enter More Characters", Toast.LENGTH_SHORT).show();
        }

        if(newBookSetList.toString().equals("")){
            Toast.makeText(SearchViewActivity.this, "No Result Found", Toast.LENGTH_SHORT).show();
        }else{
            progressbarInSearchView.setVisibility(View.GONE);
            recyclerAdapter = new BookSetAdapter(this,newBookSetList);
            recyclerView.setAdapter(recyclerAdapter);
            searchView.clearFocus();

        }



        return true;
    }

    private boolean verifyIdSet(String id) {


        for (String s : idSet){
            if (id.equals(s)){
                return false;
            }
        }
        return true;

    }

    @Override
    public boolean onQueryTextChange(String s) {

        return false;
    }
}
