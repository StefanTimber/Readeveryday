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

public class HistoryArticleAdapter extends RecyclerView.Adapter<HistoryArticleAdapter.ViewHolder> {
    private List<Data> mlist;

    static class ViewHolder extends  RecyclerView.ViewHolder{
        View historyView;
        TextView historyTitle;
        TextView historyAuthor;

        public ViewHolder(View view) {
            super(view);
            historyView = view;
            historyTitle = (TextView) view.findViewById(R.id.history_title);
            historyAuthor = (TextView) view.findViewById(R.id.history_author);
        }
    }

    public HistoryArticleAdapter(List<Data> list) {
        mlist = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.history_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.historyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                int position = holder.getAdapterPosition();
                Data data = mlist.get(position);
                Data.DateChain dateChain = data.dateChain;
                String date = dateChain.curr;
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
        holder.historyTitle.setText(data.title);
        holder.historyAuthor.setText(data.author);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}

