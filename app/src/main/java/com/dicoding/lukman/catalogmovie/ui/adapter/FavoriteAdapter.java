package com.dicoding.lukman.catalogmovie.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.lukman.catalogmovie.BuildConfig;
import com.dicoding.lukman.catalogmovie.R;
import com.dicoding.lukman.catalogmovie.ui.activiy.DetailMovie;
import com.dicoding.lukman.catalogmovie.data.models.Result;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by #PemimpinMuda on 11/25/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private Cursor listFavorite;
    private Context context;

    public FavoriteAdapter(Cursor items){
        replaceAll(items);
    }
    public void replaceAll(Cursor items){
        listFavorite = items;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);

        return new FavoriteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {

        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
//        return 0;
        if (listFavorite == null) return 0;
        return listFavorite.getCount();
    }

    private Result getItem(int posistion){
        if (!listFavorite.moveToPosition(posistion)){
            throw new IllegalStateException("Infalid postion");

        }
        return new Result(listFavorite);
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_movie)
        ImageView ivPoster;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.parent_layout)
        LinearLayout parentLayout;

        FavoriteViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        void bind(final Result item){

            String gambar = BuildConfig.IMAGE_URL + item.getPosterPath();

            Glide.with(context).load(gambar).into(ivPoster);
            tvName.setText(item.getTitle());
            tvDesc.setText(item.getOverview());
            tvDate.setText(item.getReleaseDate());
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DetailMovie.class);
                    i.putExtra(DetailMovie.EXTRA_RESULT, item);
                    v.getContext().startActivity(i);
                }
            });

        }
    }
}
