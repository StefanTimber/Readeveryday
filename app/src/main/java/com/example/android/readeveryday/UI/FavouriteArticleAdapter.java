package com.example.android.readeveryday.UI;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.readeveryday.DetailActivity;
import com.example.android.readeveryday.R;
import com.example.android.readeveryday.model.Data;

import java.util.List;

/**
 * Created by XF on 2017/5/27.
 */

public class FavouriteArticleAdapter extends RecyclerView.Adapter<FavouriteArticleAdapter.ViewHolder> {
    private List<Data> mlist;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View favouriteView;
        TextView favouriteTitle;
        TextView FavouriteAuthor;

        private ViewHolder(View view) {
            super(view);
            favouriteView = view;
            favouriteTitle = (TextView) view.findViewById(R.id.favourite_title);
            FavouriteAuthor = (TextView) view.findViewById(R.id.favourite_author);
        }
    }

    public FavouriteArticleAdapter(List<Data> list) {
        mlist = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.favourite_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.favouriteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                int position = holder.getAdapterPosition();
                Data data = mlist.get(position);
                String date = data.dateChain.curr;
                Intent intent = new Intent(V.getContext(), DetailActivity.class);
                intent.putExtra("date", date);
                V.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data data = mlist.get(position);
        holder.favouriteTitle.setText(data.title);
        holder.FavouriteAuthor.setText(data.author);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}

