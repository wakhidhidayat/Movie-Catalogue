package com.wahidhidayat.latihanapi.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wahidhidayat.latihanapi.Adapter.MovieAdapter;
import com.wahidhidayat.latihanapi.Model.Movie;
import com.wahidhidayat.latihanapi.R;
import com.wahidhidayat.latihanapi.ViewModel.MovieViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private MovieAdapter adapter;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setData(movies);
            }
            showLoading(false);
        }
    };

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new MovieAdapter();

        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        progressBar = view.findViewById(R.id.pb_movie);
        rv = view.findViewById(R.id.rv_movie);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(adapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getMovie);
        movieViewModel.setMovies("EXTRA_MOVIE");

        showLoading(true);
        return view;
    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
