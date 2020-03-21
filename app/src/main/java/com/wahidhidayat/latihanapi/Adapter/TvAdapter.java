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
import com.wahidhidayat.latihanapi.Activity.TvDetailActivity;
import com.wahidhidayat.latihanapi.BuildConfig;
import com.wahidhidayat.latihanapi.Model.Tv;
import com.wahidhidayat.latihanapi.R;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

    private ArrayList<Tv> mData = new ArrayList<>();

    private Context context;

    public TvAdapter(Context context) {
        this.context = context;
    }

    public TvAdapter() {

    }

    public void setData(ArrayList<Tv> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv, parent, false);
        return new TvViewHolder(tView);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgPoster;
        TextView tvTitle, tvOverview;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.txt_title_tv);
            tvOverview = itemView.findViewById(R.id.txt_description_tv);
            imgPoster = itemView.findViewById(R.id.img_photo_tv);

            itemView.setOnClickListener(this);
        }

        void bind(Tv tv) {
            String urlImage = BuildConfig.IMG_URL + tv.getPoster();

            tvTitle.setText(tv.getTitle());
            tvOverview.setText(tv.getOverview());

            Glide.with(itemView.getContext())
                    .load(urlImage)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Tv tv = mData.get(position);

            tv.setTitle(tv.getTitle());
            tv.setPoster(tv.getPoster());
            tv.setOverview(tv.getOverview());

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), TvDetailActivity.class);
            moveWithObjectIntent.putExtra(TvDetailActivity.EXTRA_TV, tv);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }
    }
}
