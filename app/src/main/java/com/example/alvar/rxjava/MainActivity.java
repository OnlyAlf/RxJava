package com.example.alvar.rxjava;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Display;

import com.example.alvar.rxjava.adapter.MovieAdapter;
import com.example.alvar.rxjava.databinding.ActivityMainBinding;
import com.example.alvar.rxjava.databinding.RvMovieItemBinding;
import com.example.alvar.rxjava.model.Movie;
import com.example.alvar.rxjava.util.Api;
import com.example.alvar.rxjava.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MovieAdapter adapter;
    private int cols;
    final List<String> search = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        cols = getScreenWidth() / (int)getResources().getDimension(R.dimen.item_width);
        setContentView(R.layout.activity_main);
        final ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        recyclerView = activityMainBinding.rvActivityMain;
        mainViewModel.init(this);

        mainViewModel.getMutableLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapter = new MovieAdapter(movies);
                recyclerView.setHasFixedSize(false);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, cols));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);

                activityMainBinding.svActivityMain.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        adapter.getFilter().filter(newText);

                        return false;
                    }
                });

            }
        });
    }

        public int getScreenWidth(){
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;

            return width;
        }
}




