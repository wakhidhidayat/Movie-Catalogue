package com.wahidhidayat.latihanapi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wahidhidayat.latihanapi.Model.Tv;

import java.util.ArrayList;

import static com.wahidhidayat.latihanapi.Database.DatabaseContract.TABLE_TV;

public class TvHelper {

    private static final String DATABASE_TABLE = TABLE_TV;
    private static DatabaseHelper databaseHelper;
    private static TvHelper INSTANCE;
    private static SQLiteDatabase database;

    public TvHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public static TvHelper getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvHelper(context);

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

    public ArrayList<Tv> getAllTv() {
        ArrayList<Tv> items = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, DatabaseContract.TvColoumn._ID, null);
        cursor.moveToFirst();
        Tv mItem;
        if (cursor.getCount() > 0) {
            do {
                mItem = new Tv();
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
        String querySingleRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + DatabaseContract.TvColoumn.TITLE + " " + " LIKE " + "'" + name + "'";
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

    public long insertTv(Tv mItem) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.TvColoumn.TITLE, mItem.getTitle());
        args.put(DatabaseContract.TvColoumn.PHOTO, mItem.getPoster());
        args.put(DatabaseContract.TvColoumn.DESCRIPTION, mItem.getOverview());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTv(String title) {
        return database.delete(TABLE_TV, DatabaseContract.TvColoumn.TITLE + " = " + "'" + title + "'", null);
    }
}
