package com.wahidhidayat.latihanapi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wahidhidayat.latihanapi.Activity.MovieDetailActivity;
import com.wahidhidayat.latihanapi.BuildConfig;
import com.wahidhidayat.latihanapi.Model.Movie;
import com.wahidhidayat.latihanapi.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> mData = new ArrayList<>();
    private Context context;

    public MovieAdapter() {

    }

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Movie> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgPoster;
        TextView tvJudul, tvOverview;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.txt_title);
            tvOverview = itemView.findViewById(R.id.txt_description);
            imgPoster = itemView.findViewById(R.id.img_photo);

            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            String urlImage = BuildConfig.IMG_URL + movie.getPoster();

            tvJudul.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            Glide.with(itemView.getContext())
                    .load(urlImage)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie movie = mData.get(position);

            movie.setTitle(movie.getTitle());
            movie.setPoster(movie.getPoster());
            movie.setOverview(movie.getOverview());

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), MovieDetailActivity.class);
            moveWithObjectIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }
    }
}
