package com.if5a.rumors.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PostAPiIndex implements Parcelable {
    @SerializedName("id")
    private String id;

    @SerializedName("kategori")
    private String kategori;

    @SerializedName("judul")
    private String judul;

    @SerializedName("content")
    private String content;

    @SerializedName("username")
    private String username;

    @SerializedName("created_at")
    private String date;

    @SerializedName("profile_picture")
    private String profile_picture;

    public PostAPiIndex() {
    }

    public PostAPiIndex(String id, String kategori, String judul, String content, String username, String date, String profile_picture) {
        this.id = id;
        this.kategori = kategori;
        this.judul = judul;
        this.content = content;
        this.username = username;
        this.date = date;
        this.profile_picture = profile_picture;
    }

    protected PostAPiIndex(Parcel in) {
        id = in.readString();
        kategori = in.readString();
        judul = in.readString();
        content = in.readString();
        username = in.readString();
        date = in.readString();
        profile_picture = in.readString();
    }

    public static final Creator<PostAPiIndex> CREATOR = new Creator<PostAPiIndex>() {
        @Override
        public PostAPiIndex createFromParcel(Parcel in) {
            return new PostAPiIndex(in);
        }

        @Override
        public PostAPiIndex[] newArray(int size) {
            return new PostAPiIndex[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        parcel.writeString(id);
        parcel.writeString(kategori);
        parcel.writeString(judul);
        parcel.writeString(content);
        parcel.writeString(username);
        parcel.writeString(date);
        parcel.writeString(profile_picture);
    }
}
