package com.wahidhidayat.latihanapi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wahidhidayat.latihanapi.BuildConfig;
import com.wahidhidayat.latihanapi.Database.TvHelper;
import com.wahidhidayat.latihanapi.Model.Tv;
import com.wahidhidayat.latihanapi.R;

public class TvDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tv";

    TextView tvTitle, tvOverview;
    ImageView imgPhoto;
    FloatingActionButton fab;
    TvHelper tvHelper;
    Boolean act = true;
    Boolean insert = true;
    Boolean delete = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);
        setTitle(getString(R.string.tv_show_detail));

        tvTitle = findViewById(R.id.tv_title_tv);
        tvOverview = findViewById(R.id.tv_overview_tv);
        imgPhoto = findViewById(R.id.iv_photo_tv);
        fab = findViewById(R.id.btn_fav_tv);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Tv tv = getIntent().getParcelableExtra(EXTRA_TV);
        tvHelper = TvHelper.getINSTANCE(getApplicationContext());
        String tTitle = tv.getTitle();
        if (tvHelper.getOne(tTitle)) {
            //hapus
            act = false;
            delete = false;
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_red_24dp));
        } else if (!tvHelper.getOne(tTitle)) {
            //buat
            act = true;
            insert = true;
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_red_24dp));
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabClick();
            }
        });

        String url_image = BuildConfig.IMG_URL + tv.getPoster();
        tvTitle.setText(tv.getTitle());
        tvOverview.setText(tv.getOverview());
        Glide.with(TvDetailActivity.this)
                .load(url_image)
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(imgPhoto);
    }

    private void fabClick() {
        Tv item = getIntent().getParcelableExtra(EXTRA_TV);
        if (insert && act) {
            item.setTitle(item.getTitle());
            item.setOverview(item.getOverview());
            item.setPoster(item.getPoster());
            long result = tvHelper.insertTv(item);
            if (result > 0) {
                Toast.makeText(TvDetailActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_red_24dp));
            } else {
                Toast.makeText(TvDetailActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        } else if (!delete && !act) {
            long result = tvHelper.deleteTv(item.getTitle());
            if (result > 0) {
                Intent intent = new Intent(TvDetailActivity.this, FavoriteActivity.class);
                startActivity(intent);
                Toast.makeText(TvDetailActivity.this, R.string.success_delete, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TvDetailActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
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
