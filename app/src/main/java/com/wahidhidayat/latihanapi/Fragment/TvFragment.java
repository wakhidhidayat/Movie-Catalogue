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

import com.wahidhidayat.latihanapi.Adapter.TvAdapter;
import com.wahidhidayat.latihanapi.Model.Tv;
import com.wahidhidayat.latihanapi.R;
import com.wahidhidayat.latihanapi.ViewModel.TvViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {

    private TvAdapter adapter;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private TvViewModel tvViewModel;

    private Observer<ArrayList<Tv>> getTv = new Observer<ArrayList<Tv>>() {
        @Override
        public void onChanged(ArrayList<Tv> tv) {
            if (tv != null) {
                adapter.setData(tv);
            }
            showLoading(false);
        }
    };


    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new TvAdapter();

        View view = inflater.inflate(R.layout.fragment_tv, container, false);

        progressBar = view.findViewById(R.id.pb_tv);
        rv = view.findViewById(R.id.rv_tv);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(adapter);

        tvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);
        tvViewModel.getTv().observe(this, getTv);
        tvViewModel.setTv("EXTRA_TV");

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
