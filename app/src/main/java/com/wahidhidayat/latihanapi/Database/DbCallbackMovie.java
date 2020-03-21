package com.wahidhidayat.latihanapi.Database;

import com.wahidhidayat.latihanapi.Model.Movie;

import java.util.ArrayList;

public interface DbCallbackMovie {
    void preExecute();

    void postExecute(ArrayList<Movie> items);
}
