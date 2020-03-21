package com.wahidhidayat.latihanapi.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wahidhidayat.latihanapi.BuildConfig;
import com.wahidhidayat.latihanapi.Model.Movie;
import com.wahidhidayat.latihanapi.R;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private String temp;

    public ItemAdapter(Context mContext) {
        this.context = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ItemAdapter() {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_movie, null);
            holder.mTvJudul = convertView.findViewById(R.id.tv_title_tv);
            holder.mTvDesk = convertView.findViewById(R.id.tv_overview);
            holder.mTvGambar = convertView.findViewById(R.id.img_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvJudul.setText(movieArrayList.get(position).getTitle());
        String overview = movieArrayList.get(position).getOverview();
        if (TextUtils.isEmpty(overview)) {
            temp = "No data";
        } else {
            temp = overview;
        }
        holder.mTvDesk.setText(temp);

        Glide.with(context)
                .load(BuildConfig.IMG_URL + movieArrayList.get(position).getPoster())
                .placeholder(R.color.colorAccent)
                .into(holder.mTvGambar);

        return convertView;
    }

    public void setData(ArrayList<Movie> items) {
        movieArrayList = items;
        notifyDataSetChanged();
    }

    public void addItem(final Movie item) {
        movieArrayList.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        movieArrayList.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (movieArrayList == null) return 0;
        return movieArrayList.size();
    }

    @Override
    public Movie getItem(int position) {
        return movieArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView mTvJudul;
        TextView mTvDesk;
        ImageView mTvGambar;
    }
}
