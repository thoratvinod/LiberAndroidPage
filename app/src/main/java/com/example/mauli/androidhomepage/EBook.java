package com.example.mauli.androidhomepage;

import android.os.Parcel;
import android.os.Parcelable;

public class EBook implements Parcelable{

    String eBookName,eBookAuthor,eBookId;

    public EBook(String eBookName, String eBookAuthor, String eBookId) {
        this.eBookName = eBookName;
        this.eBookAuthor = eBookAuthor;
        this.eBookId = eBookId;
    }

    public EBook(){

    }

    protected EBook(Parcel in) {
        eBookName = in.readString();
        eBookAuthor = in.readString();
        eBookId = in.readString();
    }

    public static final Creator<EBook> CREATOR = new Creator<EBook>() {
        @Override
        public EBook createFromParcel(Parcel in) {
            return new EBook(in);
        }

        @Override
        public EBook[] newArray(int size) {
            return new EBook[size];
        }
    };

    public String geteBookName() {
        return eBookName;
    }

    public String geteBookAuthor() {
        return eBookAuthor;
    }

    public String geteBookId() {
        return eBookId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eBookName);
        dest.writeString(eBookAuthor);
        dest.writeString(eBookId);
    }
}
