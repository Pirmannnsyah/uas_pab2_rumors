package com.if5a.rumors.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LikeAPI implements Parcelable {

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("post_id")
    private String post_id;

    public LikeAPI() {

    }

    public LikeAPI(String user_id, String post_id) {
        this.user_id = user_id;
        this.post_id = post_id;
    }

    protected LikeAPI(Parcel in) {
        user_id = in.readString();
        post_id = in.readString();
    }

    public static final Creator<LikeAPI> CREATOR = new Creator<LikeAPI>() {
        @Override
        public LikeAPI createFromParcel(Parcel in) {
            return new LikeAPI(in);
        }

        @Override
        public LikeAPI[] newArray(int size) {
            return new LikeAPI[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_id);
        parcel.writeString(post_id);
    }
}
