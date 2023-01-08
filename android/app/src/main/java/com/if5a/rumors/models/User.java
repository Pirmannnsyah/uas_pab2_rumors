package com.if5a.rumors.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String email;
    private String fullName;

    public User() {
    }

    public User (String email, String fullName){
        this.email = email;
        this.fullName = fullName;
    }

    protected User(Parcel in) {
        email = in.readString();
        fullName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(fullName);
    }
}
