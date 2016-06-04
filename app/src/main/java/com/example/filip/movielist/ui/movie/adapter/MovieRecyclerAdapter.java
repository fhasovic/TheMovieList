package com.example.filip.movielist.ui.movie.adapter;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.filip.movielist.R;
import com.example.filip.movielist.constants.Constants;
import com.example.filip.movielist.pojo.MovieListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filip on 01/05/2016.
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {
    private final List<MovieListModel> mItemList;
    private final ItemListener mItemListener;
    private final OnLastItemReachedListener mOnLastItemReachedListener;

    public MovieRecyclerAdapter(ItemListener mItemListener, OnLastItemReachedListener mOnLastItemReachedListener) {
        mItemList = new ArrayList<>();
        this.mItemListener = mItemListener;
        this.mOnLastItemReachedListener = mOnLastItemReachedListener;
    }

    public void setItems(List<MovieListModel> mDataSource) {
        this.mItemList.addAll(mDataSource);
        notifyDataSetChanged();
    }

    private void checkIfLastItemHasBeenReached(int currentItemPosition) {
        if (currentItemPosition > 0 && (getItemCount() - 1) == currentItemPosition) { // if it's not empty, and has reached n-1 index
            mOnLastItemReachedListener.onLastItemReached();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieListModel currentItem = mItemList.get(position);
        holder.loadPosterImage(currentItem.getMoviePosterPath());
        holder.mTitleTextView.setText(currentItem.getMovieTitle());
        holder.mDetailsTextView.setText(currentItem.getMovieDetails());
        holder.mReleaseDateTextView.setText(currentItem.getMovieReleaseDate());
        ViewCompat.setTransitionName(holder.mPosterImageView, Constants.TRANSITION_NAME);
        checkIfLastItemHasBeenReached(position);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mPosterImageView;
        final TextView mTitleTextView;
        final TextView mDetailsTextView;
        final TextView mReleaseDateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mPosterImageView = (ImageView) itemView.findViewById(R.id.recycler_view_movie_item_poster_image_view);
            mTitleTextView = (TextView) itemView.findViewById(R.id.recycler_view_movie_item_title_text_view);
            mDetailsTextView = (TextView) itemView.findViewById(R.id.recycler_view_movie_item_description_text_view);
            mReleaseDateTextView = (TextView) itemView.findViewById(R.id.recycler_view_movie_item_release_date_text_view);
            mPosterImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == mPosterImageView) {
                MovieListModel currentItem = mItemList.get(getAdapterPosition());
                if (currentItem != null) {
                    int movieID = currentItem.getMovieID();
                    mItemListener.onItemClick(movieID, mPosterImageView);
                }
            }
        }

        public void loadPosterImage(String posterPath) {
            Glide.with(itemView.getContext()).load(Constants.IMAGE_URL + posterPath).into(mPosterImageView);
        }
    }
}
