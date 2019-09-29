package com.example.mauli.androidhomepage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BookSet implements Parcelable {

    String title,negotiable, book_set_id, item_category, course_name, branch_name, year, price, bookset_image, user_id, subjects,noOfSubjects;

    public BookSet(String title, String negotiable, String book_set_id, String item_category, String course_name, String branch_name, String year, String price, String bookset_image, String user_id, String subjects,String noOfSubjects) {
        this.title = title;
        this.negotiable = negotiable;
        this.book_set_id = book_set_id;
        this.item_category = item_category;
        this.course_name = course_name;
        this.branch_name = branch_name;
        this.year = year;
        this.price = price;
        this.bookset_image = bookset_image;
        this.user_id = user_id;
        this.subjects = subjects;
        this.noOfSubjects = noOfSubjects;
    }

    public BookSet(){

    }

    protected BookSet(Parcel in) {
        title = in.readString();
        negotiable = in.readString();
        book_set_id = in.readString();
        item_category = in.readString();
        course_name = in.readString();
        branch_name = in.readString();
        year = in.readString();
        price = in.readString();
        bookset_image = in.readString();
        user_id = in.readString();
        subjects = in.readString();
        noOfSubjects = in.readString();
    }

    public static final Creator<BookSet> CREATOR = new Creator<BookSet>() {
        @Override
        public BookSet createFromParcel(Parcel in) {
            return new BookSet(in);
        }

        @Override
        public BookSet[] newArray(int size) {
            return new BookSet[size];
        }
    };

    public String getNegotiable() {
        return negotiable;
    }

    public String getBook_set_id() {
        return book_set_id;
    }

    public String getItem_category() {
        return item_category;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public String getYear() {
        return year;
    }

    public String getPrice() {
        return price;
    }

    public String getBookset_image() {
        return bookset_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getSubjects() {
        return subjects;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getNoOfSubjects() {
        return noOfSubjects;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(negotiable);
        dest.writeString(book_set_id);
        dest.writeString(item_category);
        dest.writeString(course_name);
        dest.writeString(branch_name);
        dest.writeString(year);
        dest.writeString(price);
        dest.writeString(bookset_image);
        dest.writeString(user_id);
        dest.writeString(subjects);
        dest.writeString(noOfSubjects);
    }
}


