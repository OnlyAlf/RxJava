package com.example.alvar.rxjava.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.alvar.rxjava.BR;
import com.example.alvar.rxjava.R;
import com.example.alvar.rxjava.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.BindingHolder> implements Filterable {

    private List<Movie> movieList;
    private List<Movie> filteredMovieList;
    private List<Movie> savedMovieList;

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
        savedMovieList = movieList;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_movie_item,parent,false);
        BindingHolder bindingHolder = new BindingHolder(v);

        return bindingHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.getBinding().setVariable(BR.Movie,movie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if(charString.isEmpty() ||charString.equals("")){
                    filteredMovieList = savedMovieList;
                } else {
                    List<Movie> fMovie =new ArrayList<>();
                    for(Movie movie : savedMovieList){
                        if(movie.getTitle().toLowerCase().contains(charString) || movie.getGenre().toLowerCase().contains(charString)){
                            fMovie.add(movie);
                        }
                        //filteredMovieList = fMovie;
                    }
                    filteredMovieList = fMovie;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredMovieList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if(filterResults.values != null) {
                    movieList = (ArrayList<Movie>) filterResults.values;
                }else
                    movieList = savedMovieList;

                notifyDataSetChanged();
            }
        };
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public BindingHolder(View rowView) {

            super(rowView);

            binding = DataBindingUtil.bind(rowView);

        }

        public ViewDataBinding getBinding() {

            return binding;

        }

    }
}
