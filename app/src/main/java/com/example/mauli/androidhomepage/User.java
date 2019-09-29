package com.example.mauli.androidhomepage;

import android.net.Uri;

import java.net.URI;
import java.net.URL;

public class User {

    String profilePicture,collegeName,gendre,users_name,users_email,users_address,user_id,phone_no;
    boolean emailVerified;

    public User(String profilePicture, String collegeName, String gendre, String users_name, String users_email, String users_address, String user_id, String phone_no, boolean emailVerified) {
        this.profilePicture = profilePicture;
        this.collegeName = collegeName;
        this.gendre = gendre;
        this.users_name = users_name;
        this.users_email = users_email;
        this.users_address = users_address;
        this.user_id = user_id;
        this.phone_no = phone_no;
        this.emailVerified = emailVerified;
    }

    public User(){

    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public String getGendre() {
        return gendre;
    }

    public String getUsers_name() {
        return users_name;
    }

    public String getUsers_email() {
        return users_email;
    }

    public String getUsers_address() {
        return users_address;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
