package com.if5a.rumors.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PostAPI implements Parcelable {

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("kategori")
    private String kategori;

    @SerializedName("judul")
    private String judul;

    @SerializedName("content")
    private String content;

    @SerializedName("name")
    private String nama;

    @SerializedName("created_at")
    private String date;

    public PostAPI() {
    }

    public PostAPI(String user_id, String kategori, String judul, String content, String nama) {
        this.user_id = user_id;
        this.kategori = kategori;
        this.judul = judul;
        this.content = content;
        this.nama = nama;
    }

    protected PostAPI(Parcel in) {
        user_id = in.readString();
        kategori = in.readString();
        judul = in.readString();
        content = in.readString();
        nama = in.readString();
        date = in.readString();
    }

    public static final Creator<PostAPI> CREATOR = new Creator<PostAPI>() {
        @Override
        public PostAPI createFromParcel(Parcel in) {
            return new PostAPI(in);
        }

        @Override
        public PostAPI[] newArray(int size) {
            return new PostAPI[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_id);
        parcel.writeString(kategori);
        parcel.writeString(judul);
        parcel.writeString(content);
        parcel.writeString(nama);
        parcel.writeString(date);
    }
}
