package com.wahidhidayat.favoritemovie;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.wahidhidayat.favoritemovie.DatabaseContract.MovieColoumn.DESCRIPTION;
import static com.wahidhidayat.favoritemovie.DatabaseContract.MovieColoumn.PHOTO;
import static com.wahidhidayat.favoritemovie.DatabaseContract.MovieColoumn.TITLE;
import static com.wahidhidayat.favoritemovie.DatabaseContract.getColumnInt;
import static com.wahidhidayat.favoritemovie.DatabaseContract.getColumnString;

public class MovieItem implements Parcelable {
    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
    private int id;
    private String titleMovie, overView, imagePoster;

    private MovieItem(Parcel in) {
        this.id = in.readInt();
        this.titleMovie = in.readString();
        this.overView = in.readString();
        this.imagePoster = in.readString();
    }

    public MovieItem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.titleMovie = getColumnString(cursor, TITLE);
        this.overView = getColumnString(cursor, DESCRIPTION);
        this.imagePoster = getColumnString(cursor, PHOTO);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleMovie() {
        return titleMovie;
    }

    private void setTitleMovie(String titleMovie) {
        this.titleMovie = titleMovie;
    }

    public String getOverView() {
        return overView;
    }

    private void setOverView(String overView) {
        this.overView = overView;
    }

    public String getImagePoster() {
        return imagePoster;
    }

    private void setImagePoster(String imagePoster) {
        this.imagePoster = imagePoster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.titleMovie);
        dest.writeString(this.overView);
        dest.writeString(this.imagePoster);
    }
}
