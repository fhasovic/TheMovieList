package com.example.filip.movielist.ui.favorite.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.filip.movielist.R;
import com.example.filip.movielist.api.database.RealmDatabaseHelper;
import com.example.filip.movielist.constants.Constants;
import com.example.filip.movielist.pojo.ListMovieItem;
import com.example.filip.movielist.singleton.App;
import com.example.filip.movielist.ui.favorite.presenter.FavoriteMoviesPresenter;
import com.example.filip.movielist.ui.favorite.presenter.FavoriteMoviesPresenterImpl;
import com.example.filip.movielist.ui.movie.adapter.ItemListener;
import com.example.filip.movielist.ui.movie.adapter.MovieRecyclerAdapter;
import com.example.filip.movielist.ui.movie.view.DisplayMovieActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Filip on 03/05/2016.
 */
public class FavoriteMoviesActivity extends AppCompatActivity implements FavoriteMoviesView, ItemListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.activity_favorite_movies_recycler_view)
    RecyclerView mFavoriteMoviesRecyclerView;

    private MovieRecyclerAdapter mAdapter;

    private FavoriteMoviesPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);
        ButterKnife.bind(this);
        initToolbar();
        initAdapter();
        initRecyclerView();
        initPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.requestMoviesForView();
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.activity_favorite_movies_title);
        setSupportActionBar(mToolbar);
    }

    private void initAdapter() {
        mAdapter = new MovieRecyclerAdapter(this);
    }

    private void initRecyclerView() {
        mFavoriteMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFavoriteMoviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFavoriteMoviesRecyclerView.setHasFixedSize(true);
        mFavoriteMoviesRecyclerView.setAdapter(mAdapter);
    }

    private void initPresenter() {
        RealmDatabaseHelper databaseHelper = App.get().getRealmDatabaseHelper();
        presenter = new FavoriteMoviesPresenterImpl(this, databaseHelper);
    }

    @Override
    public void loadFavoriteMoviesIntoAdapter(List<ListMovieItem> moviesToLoad) {
        mAdapter.setItems(moviesToLoad);
    }

    @Override
    public void onFailure() {
        Toast.makeText(App.get(), R.string.favorite_movies_toast_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(long movieID, View viewToTransit) {
        Intent i = new Intent(this, DisplayMovieActivity.class);
        i.putExtra(Constants.MOVIE_ID_KEY, movieID);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, viewToTransit, getString(R.string.image_view_transition));
        startActivity(i, optionsCompat.toBundle());
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}