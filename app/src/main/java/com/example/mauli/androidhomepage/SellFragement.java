package com.example.mauli.androidhomepage;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SellFragement extends Fragment {

    private RecyclerView recyclerView;

    private List<BookSet> bookSetList;
    private DatabaseReference bookSetDatabase;
    private ProgressBar progressbarInSell;
    private FirebaseAuth mAuth;
    private SellerBookSetAdapter recyclerAdapter;
    private FloatingActionButton floatingActionButton;
    private TextView textView;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragement_sell, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.sellRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookSetList = new ArrayList<>();
        bookSetDatabase = FirebaseDatabase.getInstance().getReference("BookSets");
        progressbarInSell = (ProgressBar) view.findViewById(R.id.progressbarInSell);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        textView = (TextView) view.findViewById(R.id.textMessage);

        //DoubleBounce doubleBounce = new DoubleBounce();
        //progressbarInBuy.setIndeterminateDrawable(doubleBounce);

        toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbarInsell);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        getBookSetInList();

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SellActivity.class));
            }
        });



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy < 0) {
                    fab.show();

                } else if (dy > 0) {
                    fab.hide();
                }
            }


        });





        return view;

    }

    private void getBookSetInList() {

        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        bookSetDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookSetList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    BookSet bookSet = postSnapshot.getValue(BookSet.class);
                    if(bookSet.getUser_id().equals(firebaseUser.getUid())){
                        bookSetList.add(bookSet);
                    }

                }
                if(bookSetList==null){
                    textView.setVisibility(View.VISIBLE);
                }else {
                    textView.setVisibility(View.GONE);
                }
                progressbarInSell.setVisibility(View.GONE);
                recyclerAdapter = new SellerBookSetAdapter(getActivity(),bookSetList);
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}