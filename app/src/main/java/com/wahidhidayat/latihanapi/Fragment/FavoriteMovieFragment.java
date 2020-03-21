package com.wahidhidayat.latihanapi.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wahidhidayat.latihanapi.Activity.MovieDetailActivity;
import com.wahidhidayat.latihanapi.Adapter.ItemClickSupport;
import com.wahidhidayat.latihanapi.Adapter.MovieAdapter;
import com.wahidhidayat.latihanapi.Database.DbCallbackMovie;
import com.wahidhidayat.latihanapi.Database.MovieHelper;
import com.wahidhidayat.latihanapi.Model.Movie;
import com.wahidhidayat.latihanapi.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.wahidhidayat.latihanapi.Activity.MovieDetailActivity.EXTRA_MOVIE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements DbCallbackMovie {

    private ProgressBar mProgressBar;
    private MovieHelper mMovieHelper;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Movie> items = new ArrayList<>();

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        mProgressBar = view.findViewById(R.id.pb_fav_movie);
        recyclerView = view.findViewById(R.id.rv_fav_movie);

        mMovieHelper = MovieHelper.getINSTANCE(getContext());
        mMovieHelper.open();

        showRecyclerView();
        recyclerView.addOnItemTouchListener(new ItemClickSupport(getContext(), recyclerView, new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Movie mItem = new Movie();
                mItem.setId(items.get(position).getId());
                mItem.setPoster(items.get(position).getPoster());
                mItem.setTitle(items.get(position).getTitle());
                mItem.setOverview(items.get(position).getOverview());
                Intent detail = new Intent(getContext(), MovieDetailActivity.class);

                detail.putExtra(EXTRA_MOVIE, mItem);
                startActivity(detail);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Movie mItem = new Movie();
                mItem.setId(items.get(position).getId());
                mItem.setPoster(items.get(position).getPoster());
                mItem.setTitle(items.get(position).getTitle());
                mItem.setOverview(items.get(position).getOverview());
                Intent detail = new Intent(getContext(), MovieDetailActivity.class);

                detail.putExtra(EXTRA_MOVIE, mItem);
                startActivity(detail);
            }
        }));

        new FavoriteMovieFragment.LoadMovieAsync(mMovieHelper, this).execute();
        return view;
    }

    private void showRecyclerView() {
        movieAdapter = new MovieAdapter(getContext());
        movieAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(movieAdapter);
    }

    public void preExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void postExecute(ArrayList<Movie> mItem) {
        mProgressBar.setVisibility(View.GONE);
        movieAdapter.setData(mItem);
        items.addAll(mItem);
    }


    private class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

        WeakReference<MovieHelper> tvHelperWeakReference;
        WeakReference<DbCallbackMovie> loadFavCallbackWeakReference;

        public LoadMovieAsync(MovieHelper movieHelper, DbCallbackMovie context) {
            tvHelperWeakReference = new WeakReference<>(movieHelper);
            loadFavCallbackWeakReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadFavCallbackWeakReference.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> mItem) {
            super.onPostExecute(mItem);
            loadFavCallbackWeakReference.get().postExecute(mItem);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return tvHelperWeakReference.get().getAllMovie();
        }
    }

}
