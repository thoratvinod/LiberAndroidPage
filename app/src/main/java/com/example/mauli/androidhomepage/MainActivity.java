package com.example.mauli.androidhomepage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ProgressBar progressbarInBuy;
    private List<BookSet> bookSetList;
    private RecyclerView recyclerView;
    private BookSetAdapter recyclerAdapter;
    private DatabaseReference bookSetDatabase;
    private android.support.v7.widget.Toolbar toolbar;


    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);




        Intent intent = getIntent();
         Integer fragement= intent.getIntExtra("fragement_no",1);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);



        if (fragement==2)
        {
            loadFragment(new SellFragement());
            navigation.setSelectedItemId(R.id.navigation_sell);
        }else if (fragement==3){
            loadFragment(new EBookFragement());
            navigation.setSelectedItemId(R.id.navigation_home);

        }else if (fragement==4){
            loadFragment(new ProfileFragement());
            navigation.setSelectedItemId(R.id.navigation_profile);
        }else{
            loadFragment(new BuyFragement());
            navigation.setSelectedItemId(R.id.navigation_buy);

        }




        progressbarInBuy = (ProgressBar) findViewById(R.id.progressbarInBuy);
        bookSetDatabase = FirebaseDatabase.getInstance().getReference("BookSets");
        bookSetList = new ArrayList<>();
        //recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getBookSetInList();

        //getting bottom navigation view and attaching the listener

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new EBookFragement();
                break;

            case R.id.navigation_sell:
                fragment = new SellFragement();
                break;

            case R.id.navigation_buy:
                fragment = new BuyFragement();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragement();
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
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
                //progressbarInBuy.setVisibility(View.GONE);
                //recyclerAdapter = new BookSetAdapter(MainActivity.this,bookSetList);
                //recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.searchIconInMain);

        MenuItem menuItem1 = menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Fragment fragment = getVisibleFragment();



                if (fragment == getSupportFragmentManager().findFragmentByTag("BuyFragement")){
                    Toast.makeText(MainActivity.this, "hello there", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(MainActivity.this, SearchViewActivity.class));
                Toast.makeText(MainActivity.this, getVisibleFragment().toString(), Toast.LENGTH_SHORT).show();
                return true;

            }
        });

        //menuItem.expandActionView();
        //SearchView searchView = (SearchView) menuItem.getActionView();

        //searchView.setOnQueryTextListener(this);
        return true;

    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    /*@Override
    public boolean onQueryTextSubmit(String s) {
        String userInput = s.toLowerCase();
        List<BookSet> newBookSetList = new ArrayList<>();

        if(userInput.length()>3){
            for(BookSet bookSet : bookSetList)
            {
                if(bookSet.getCourse_name().toLowerCase().contains(userInput))
                {
                    newBookSetList.add(bookSet);
                }
            }
        }else{
            Toast.makeText(MainActivity.this, "Please Enter More Characters", Toast.LENGTH_SHORT).show();
        }

        if(newBookSetList==null){
            Toast.makeText(MainActivity.this, "No Result Found", Toast.LENGTH_SHORT).show();
        }


        Intent intent = new Intent(MainActivity.this, SearchViewActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("SearchBookSetList",(Serializable)newBookSetList);
        intent.putExtra("BUNDLE",args);
        //Bundle args1 = new Bundle();
        //args1.putSerializable("BookSetList",(Serializable)bookSetList);
        //intent.putExtra("BUNDLE1",args1);
        startActivity(intent);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        return false;
    }*/
}