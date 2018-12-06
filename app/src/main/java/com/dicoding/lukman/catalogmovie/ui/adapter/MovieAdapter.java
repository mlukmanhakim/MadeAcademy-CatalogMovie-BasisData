package com.dicoding.lukman.catalogmovie.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviewViewHolder> {

private List<Result> mList = new ArrayList<>();
private Context context;


    public MovieAdapter(ArrayList<Result> list){
        mList = list;
    }

    @NonNull
    @Override
    public MoviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
      View v = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
      return  new MoviewViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MoviewViewHolder holder, final int position) {

        final Result currResult = mList.get(position);
        String title = currResult.getTitle();
        String deskripsi = currResult.getOverview();
        String tanggal = currResult.getReleaseDate();
        String gambar = BuildConfig.IMAGE_URL + currResult.getPosterPath();
        Glide.with(context).load(gambar)
                .into(holder.imageView);

        holder.tvName.setText(title);
        holder.tvDesc.setText(deskripsi);
        holder.tvDate.setText(tanggal);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("title", currResult.getTitle());
                Intent i = new Intent(v.getContext(), DetailMovie.class);
                i.putExtra(DetailMovie.EXTRA_RESULT, currResult);
            v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class MoviewViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView tvName, tvDesc, tvDate;
        private LinearLayout parentLayout;
        private MoviewViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_movie);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvDate = itemView.findViewById(R.id.tv_date);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }

    }








}
