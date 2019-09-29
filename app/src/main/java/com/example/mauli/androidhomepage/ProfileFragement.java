package com.example.mauli.androidhomepage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.ScriptC;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragement extends Fragment {
    private TextView user_name,user_email,user_phone,user_gender,user_address,user_college;
     CircleImageView userImageView;
    private ImageView signOut,updateProfile;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabase;
    private ProgressBar progressbarInProfile;
    private ScrollView scrollView;

    private android.support.v7.widget.Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View view = inflater.inflate(R.layout.fragement_profile, null);
        scrollView = (ScrollView) view.findViewById(R.id.scrollViewInProfileFragement);
        scrollView.setVisibility(View.INVISIBLE);

        toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbarInProfile);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);

        user_name = (TextView) view.findViewById(R.id.user_name);
        user_email = (TextView) view.findViewById(R.id.user_email);
        user_phone = (TextView) view.findViewById(R.id.user_phone);
        user_gender = (TextView) view.findViewById(R.id.user_gender);
        user_address = (TextView) view.findViewById(R.id.user_address);
        user_college = (TextView) view.findViewById(R.id.user_college);
        userImageView = (CircleImageView) view.findViewById(R.id.userImageView);
        signOut = (ImageView) view.findViewById(R.id.signOut);
        updateProfile = (ImageView) view.findViewById(R.id.updateProfile);
        progressbarInProfile = (ProgressBar) view.findViewById(R.id.progressbarInProfile);

        mAuth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance().getReference("User_Information");

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignoutMethod();
            }
        });
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),UpdateProfile.class);
                intent.putExtra("hint",2);
                startActivity(intent);
            }
        });

        userDatabase.child("My_App_User").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                putValuesInFields(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void putValuesInFields(User user) {

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        user_name.setText(user.getUsers_name());
        user_email.setText(user.getUsers_email());
        user_phone.setText(user.getPhone_no());
        user_gender.setText(user.getGendre());
        user_address.setText(user.getUsers_address());
        user_college.setText(user.getCollegeName());
        if(user.getProfilePicture()!=null){
            Glide.with(this)
                    .load(user.getProfilePicture())
                    .into(userImageView);
            progressbarInProfile.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);


        }
        else{
            Toast.makeText(getContext(),"Photo is not Uploaded. Please Upload",Toast.LENGTH_LONG).show();
            if (user.getGendre().equals("Male")){

                userImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy));


            }else if (user.getGendre().equals("Female")){

                userImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl));

            }

            progressbarInProfile.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void SignoutMethod() {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Signout Warning!!!");
        alertDialog.setMessage("Do you really want to sign out?");
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.sign_out_button);


        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getContext(), LoginActivity.class));

                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

}
