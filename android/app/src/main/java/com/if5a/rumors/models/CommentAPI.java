package com.if5a.rumors.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CommentAPI implements Parcelable {

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("post_id")
    private String post_id;

    @SerializedName("coment")
    private String content;

    public CommentAPI() {
    }

    public CommentAPI(String user_id, String post_id, String content) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.content = content;
    }

    protected CommentAPI(Parcel in) {
        user_id = in.readString();
        post_id = in.readString();
        content = in.readString();
    }

    public static final Creator<CommentAPI> CREATOR = new Creator<CommentAPI>() {
        @Override
        public CommentAPI createFromParcel(Parcel in) {
            return new CommentAPI(in);
        }

        @Override
        public CommentAPI[] newArray(int size) {
            return new CommentAPI[size];
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_id);
        parcel.writeString(post_id);
        parcel.writeString(content);
    }
}
