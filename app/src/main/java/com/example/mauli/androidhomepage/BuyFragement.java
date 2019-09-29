package com.example.mauli.androidhomepage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.mauli.androidhomepage.interfaces.ILoadMore;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class BuyFragement extends Fragment{

    private RecyclerView recyclerView;
    private List<BookSet> bookSetList;
    private DatabaseReference booksetDatabase;
    private ProgressBar progressbarInBuy;
    private FirebaseAuth mAuth;
    private BookSetAdapter recyclerAdapter;
    private DatabaseReference bookSetDatabase;
    private android.support.v7.widget.Toolbar toolbar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_buy,container,false);



        bookSetDatabase = FirebaseDatabase.getInstance().getReference("BookSets");
        bookSetList = new ArrayList<>();
        progressbarInBuy = (ProgressBar) view.findViewById(R.id.progressbarInBuy);
        mAuth = FirebaseAuth.getInstance();

        toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbarInBuy);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);



        //setHasOptionsMenu(true);


        //month = Arrays.asList(getResources().getStringArray(R.array.month));

        //toolbar = (Toolbar) view.findViewById(R.id.toolbar);


        recyclerView = (RecyclerView) view.findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getBookSetInList();









        //recyclerAdapter = new BookSetAdapter(this,bookSetList);
        //recyclerView.setAdapter(recyclerAdapter);



        /*recyclerView = (RecyclerView) view.findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        booksetDatabase = FirebaseDatabase.getInstance().getReference("BookSets");
        progressbarInBuy = (ProgressBar) view.findViewById(R.id.progressbarInBuy);
        //DoubleBounce doubleBounce = new DoubleBounce();
        //progressbarInBuy.setIndeterminateDrawable(doubleBounce);
        mAuth = FirebaseAuth.getInstance();

        FirebaseRecyclerAdapter<BookSet,BookSetViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<BookSet, BookSetViewHolder>(
                BookSet.class,
                R.layout.bookset_list_view,
                BookSetViewHolder.class,
                booksetDatabase
        ) {
            @Override
            protected void populateViewHolder(BookSetViewHolder viewHolder, final BookSet model, final int position) {
                viewHolder.setCourse_name(model.getCourse_name());
                viewHolder.setBranch_name(model.getBranch_name());
                viewHolder.setYear(model.getYear());
                viewHolder.setType_of_set(model.getType_of_set());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setBookset_image(model.getBookset_image());

                viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),BookSetInfo.class);
                        intent.putExtra("BookSet", (Parcelable) model);
                        startActivity(intent);
                    }
                });
                progressbarInBuy.setVisibility(View.GONE);
            }
        };
        recyclerView.setAdapter(recyclerAdapter);*/

        return view;

    }
    /*public static class BookSetViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView course,branch,year,price,type,id,item_category;
        ImageView imageView;
        LinearLayout parentLayout;


        public BookSetViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            course = (TextView) itemView.findViewById(R.id.course);
            branch = (TextView) itemView.findViewById(R.id.branch);
            year = (TextView) itemView.findViewById(R.id.year);

            price = (TextView) itemView.findViewById(R.id.price);
            type = (TextView) itemView.findViewById(R.id.type);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.parentLayout);
        }


        public void setCourse_name(String course_name) {
            course.setText(course_name);
        }

        public void setBranch_name(String branch_name) {
            branch.setText(branch_name);
        }

        public void setYear(String year_name) {
            year.setText(year_name);
        }

        public void setPrice(String priceS) {
            price.setText(priceS);
        }

        public void setType_of_set(String type_of_set) {
            type.setText(type_of_set);
        }

        public void setBookset_image(String bookset_image){

            Glide.with(mView.getContext())
                    .load(bookset_image)
                    .into(imageView);
        }


    }*/

    private void getBookSetInList() {

        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        bookSetDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookSetList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    BookSet bookSet = postSnapshot.getValue(BookSet.class);
                    if(!bookSet.getUser_id().equals(firebaseUser.getUid())){
                        bookSetList.add(bookSet);
                    }

                }
                progressbarInBuy.setVisibility(View.GONE);
                recyclerAdapter = new BookSetAdapter(getActivity(),bookSetList);
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.toolbar_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.searchIconInMain);

        MenuItem menuItem1 = menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                startActivity(new Intent(getContext(), SearchViewActivity.class));
                return true;

            }
        });

        //menuItem.expandActionView();
        //SearchView searchView = (SearchView) menuItem.getActionView();

        //searchView.setOnQueryTextListener(this);

    }





    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Implementing ActionBar Search inside a fragment
        MenuItem item = menu.add("Search");
        item.setIcon(R.drawable.search_image); // sets icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView sv = new SearchView(getActivity());

        // modifying the text inside edittext component
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv.findViewById(id);
        textView.setHint("Search location...");
        textView.setHintTextColor(getResources().getColor(R.color.hintSearch));
        textView.setTextColor(getResources().getColor(R.color.white));

        // implementing the listener
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() < 2) {
                    Toast.makeText(getActivity(),
                            "Your search query must not be less than 1 characters",
                            Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    doSearch(s);
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        item.setActionView(sv);
    }*/

    /*private Boolean doSearch(String s) {
        String userInput = s.toLowerCase();
        List<BookSet> newBookSetList = new ArrayList<>();


        for(BookSet bookSet : bookSetList)
        {
            if(bookSet.getCourse_name().toLowerCase().contains(userInput))
            {
                newBookSetList.add(bookSet);
            }
        }
        if(newBookSetList==null){
            Toast.makeText(getContext(), "No Result Found", Toast.LENGTH_SHORT).show();
        }
        recyclerAdapter = new BookSetAdapter(getActivity(),bookSetList);
        recyclerView.setAdapter(recyclerAdapter);

        return true;


    }*/




    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String userInput = s.toLowerCase();
        List<BookSet> newBookSetList = new ArrayList<>();


        for(BookSet bookSet : bookSetList)
        {
            if(bookSet.getCourse_name().toLowerCase().contains(userInput))
            {
                newBookSetList.add(bookSet);
            }
        }
        if(newBookSetList==null){
            Toast.makeText(getContext(), "No Result Found", Toast.LENGTH_SHORT).show();
        }
        recyclerAdapter = new BookSetAdapter(getActivity(),bookSetList);
        recyclerView.setAdapter(recyclerAdapter);

        return true;
    }*/
}
