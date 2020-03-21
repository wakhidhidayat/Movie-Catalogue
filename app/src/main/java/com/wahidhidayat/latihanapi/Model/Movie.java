package com.wahidhidayat.latihanapi.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @SerializedName(value = "title", alternate = {"name"})
    private String title;
    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String poster;
    private int id;

    public Movie() {

    }

    protected Movie(Parcel in) {
        title = in.readString();
        overview = in.readString();
        poster = in.readString();
        id = in.readInt();
    }

    public Movie(JSONObject object) {
        try {
            String title = object.getString("title");
            String overview = object.getString("overview");
            String poster_path = object.getString("poster_path");
            int id = object.getInt("id");

            this.title = title;
            this.poster = poster_path;
            this.overview = overview;
            this.id = id;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(poster);
        dest.writeInt(id);
    }
}
