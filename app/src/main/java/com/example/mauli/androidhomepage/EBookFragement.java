package com.example.mauli.androidhomepage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class EBookFragement extends android.support.v4.app.Fragment {

    private EBookAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private List<EBook> eBookList;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference eBookDatabase;
    private ProgressBar progressbarIneBook;
    private TextView textView;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ebook_fragement, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbarInEBook);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) view.findViewById(R.id.eBookRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eBookList = new ArrayList<>();
        eBookDatabase = FirebaseDatabase.getInstance().getReference("E-Books");
        progressbarIneBook = (ProgressBar) view.findViewById(R.id.progressbarIneBook);
        final FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_add);
        textView = (TextView) view.findViewById(R.id.textMessage);

        //DoubleBounce doubleBounce = new DoubleBounce();
        //progressbarInBuy.setIndeterminateDrawable(doubleBounce);
        //mAuth = FirebaseAuth.getInstance();
        //getBookSetInList();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddEBook.class));
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
                    floatingActionButton.show();

                } else if (dy > 0) {
                    floatingActionButton.hide();
                }
            }


        });

        getEBookInList();

        return view;
    }

    private void getEBookInList() {

        //final FirebaseUser firebaseUser = mAuth.getCurrentUser();


        eBookDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eBookList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    EBook eBook = postSnapshot.getValue(EBook.class);
                    eBookList.add(eBook);

                }

                if (eBookList == null) {
                    textView.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "List is null", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setVisibility(View.GONE);
                }
                progressbarIneBook.setVisibility(View.GONE);
                recyclerAdapter = new EBookAdapter(getActivity(),eBookList);
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
