package com.wahidhidayat.latihanapi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wahidhidayat.latihanapi.Model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.wahidhidayat.latihanapi.Database.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {

    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    public MovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public static MovieHelper getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);

                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen()) {
            database.close();
        }
    }

    public ArrayList<Movie> getAllMovie() {
        ArrayList<Movie> items = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, DatabaseContract.MovieColoumn._ID, null);
        cursor.moveToFirst();
        Movie mItem;
        if (cursor.getCount() > 0) {
            do {
                mItem = new Movie();
                mItem.setId(cursor.getInt(0));
                mItem.setOverview(String.valueOf(cursor.getString(2)));
                mItem.setPoster(String.valueOf(cursor.getString(3)));
                mItem.setTitle(String.valueOf(cursor.getString(1)));
                items.add(mItem);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return items;
    }

    public Boolean getOne(String name) {
        String querySingleRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + DatabaseContract.MovieColoumn.TITLE + " " + " LIKE " + "'" + name + "'";
        Cursor cursor = database.rawQuery(querySingleRecord, null);
        cursor.moveToFirst();
        Log.d("cursor", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0) {

            return true;
        } else if (cursor.getCount() == 0) {
            return false;
        }
        return false;
    }

    public long insertMovie(Movie mItem) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.MovieColoumn.TITLE, mItem.getTitle());
        args.put(DatabaseContract.MovieColoumn.PHOTO, mItem.getPoster());
        args.put(DatabaseContract.MovieColoumn.DESCRIPTION, mItem.getOverview());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public long insertProvider(ContentValues contentValues) {
        return database.insert(DATABASE_TABLE, null, contentValues);
    }

    public int deleteMovie(String title) {
        return database.delete(TABLE_MOVIE, DatabaseContract.MovieColoumn.TITLE + " = " + "'" + title + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null, _ID + "=?", new String[]{id}, null, null, null, null);
    }

    public int updateProvider(String id, ContentValues contentValues) {
        return database.update(DATABASE_TABLE, contentValues, _ID + "=?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + "=?", new String[]{id});
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE, null, null, null, null, null, _ID + " DESC");
    }
}
