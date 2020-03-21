package com.wahidhidayat.latihanapi.Activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wahidhidayat.latihanapi.Adapter.TvAdapter;
import com.wahidhidayat.latihanapi.BuildConfig;
import com.wahidhidayat.latihanapi.Model.Tv;
import com.wahidhidayat.latihanapi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvSearchActivity extends AppCompatActivity {

    private EditText editTitle;
    private Button btnSearch;
    private RecyclerView rvResult;
    private TvAdapter adapter;
    private ProgressBar progressBar;

    private Observer<ArrayList<Tv>> getTv = new Observer<ArrayList<Tv>>() {
        @Override
        public void onChanged(ArrayList<Tv> tvs) {
            if (tvs != null) {
                adapter.setData(tvs);
            }
            showLoading(false);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_search);
        setTitle(getString(R.string.tv_search));

        editTitle = findViewById(R.id.et_title_tv);
        btnSearch = findViewById(R.id.btn_search_tv);
        rvResult = findViewById(R.id.rv_result_tv);
        progressBar = findViewById(R.id.pb_search_tv);

        adapter = new TvAdapter();
        rvResult.setLayoutManager(new LinearLayoutManager(this));
        rvResult.setAdapter(adapter);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                if (TextUtils.isEmpty(title)) return;
                showLoading(true);
                search(title);
            }
        });

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void search(final String title) {
        final ArrayList<Tv> listItems = new ArrayList<>();

        AsyncHttpClient client = new AsyncHttpClient();

        String url = BuildConfig.TV_SEARCH_URL + BuildConfig.API_KEY + "&language=en-US&query=" + title;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    Log.d("Response", result);
                    Log.d("List", list.toString());

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject item = list.getJSONObject(i);
                        Tv tvItems = new Tv(item);

                        tvItems.setTitle(item.getString("name"));
                        tvItems.setPoster(item.getString("poster_path"));
                        tvItems.setOverview(item.getString("overview"));
                        listItems.add(tvItems);
                    }

                    adapter.setData(listItems);
                    showLoading(false);

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("on Failure", error.getMessage());
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
