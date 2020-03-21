package com.wahidhidayat.latihanapi.Database;

import com.wahidhidayat.latihanapi.Model.Tv;

import java.util.ArrayList;

public interface DbCallbackTv {
    void preExecute();

    void postExecute(ArrayList<Tv> items);
}
