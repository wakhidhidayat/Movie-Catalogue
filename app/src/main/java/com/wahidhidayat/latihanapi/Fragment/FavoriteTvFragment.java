package com.wahidhidayat.latihanapi.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wahidhidayat.latihanapi.Activity.TvDetailActivity;
import com.wahidhidayat.latihanapi.Adapter.ItemClickSupport;
import com.wahidhidayat.latihanapi.Adapter.TvAdapter;
import com.wahidhidayat.latihanapi.Database.DbCallbackTv;
import com.wahidhidayat.latihanapi.Database.TvHelper;
import com.wahidhidayat.latihanapi.Model.Tv;
import com.wahidhidayat.latihanapi.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.wahidhidayat.latihanapi.Activity.TvDetailActivity.EXTRA_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment implements DbCallbackTv {

    private ProgressBar mProgressBar;
    private TvHelper mTvHelper;
    private TvAdapter tvAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Tv> items = new ArrayList<>();


    public FavoriteTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_tv, container, false);
        mProgressBar = view.findViewById(R.id.pb_fav_tv);
        recyclerView = view.findViewById(R.id.rv_fav_tv);

        mTvHelper = TvHelper.getINSTANCE(getContext());
        mTvHelper.open();

        showRecyclerView();
        recyclerView.addOnItemTouchListener(new ItemClickSupport(getContext(), recyclerView, new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Tv mItem = new Tv();
                mItem.setId(items.get(position).getId());
                mItem.setPoster(items.get(position).getPoster());
                mItem.setTitle(items.get(position).getTitle());
                mItem.setOverview(items.get(position).getOverview());
                Intent detail = new Intent(getContext(), TvDetailActivity.class);

                detail.putExtra(EXTRA_TV, mItem);
                startActivity(detail);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        new LoadTvAsync(mTvHelper, this).execute();
        return view;
    }

    private void showRecyclerView() {
        tvAdapter = new TvAdapter(getContext());
        tvAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(tvAdapter);
    }


    @Override
    public void preExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<Tv> mItem) {
        mProgressBar.setVisibility(View.GONE);
        tvAdapter.setData(mItem);
        items.addAll(mItem);
    }

    private class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<Tv>> {

        WeakReference<TvHelper> tvHelperWeakReference;
        WeakReference<DbCallbackTv> loadFavCallbackWeakReference;

        public LoadTvAsync(TvHelper tvHelper, DbCallbackTv context) {
            tvHelperWeakReference = new WeakReference<>(tvHelper);
            loadFavCallbackWeakReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadFavCallbackWeakReference.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Tv> mItem) {
            super.onPostExecute(mItem);
            loadFavCallbackWeakReference.get().postExecute(mItem);
        }

        @Override
        protected ArrayList<Tv> doInBackground(Void... voids) {
            Log.d("test", "test" + tvHelperWeakReference.get().getAllTv());
            return tvHelperWeakReference.get().getAllTv();
        }
    }

}
