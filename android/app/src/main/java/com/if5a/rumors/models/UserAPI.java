package com.if5a.rumors.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import kotlin.jvm.internal.SerializedIr;

public class UserAPI implements Parcelable {

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private String username;

    @SerializedName("profile_picture")
    private String profile_picture;

    public UserAPI() {
    }

    public UserAPI(String user_id, String name, String username, String profile_picture) {
        this.user_id = user_id;
        this.name = name;
        this.username = username;
        this.profile_picture = profile_picture;
    }

    protected UserAPI(Parcel in) {
        user_id = in.readString();
        name = in.readString();
        username = in.readString();
        profile_picture = in.readString();
    }

    public static final Creator<UserAPI> CREATOR = new Creator<UserAPI>() {
        @Override
        public UserAPI createFromParcel(Parcel in) {
            return new UserAPI(in);
        }

        @Override
        public UserAPI[] newArray(int size) {
            return new UserAPI[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_id);
        parcel.writeString(name);
        parcel.writeString(username);
        parcel.writeString(profile_picture);
    }
}
