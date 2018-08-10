package com.example.alvar.rxjava.util;

import com.example.alvar.rxjava.model.Data;
import com.example.alvar.rxjava.model.Movie;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

import static com.example.alvar.rxjava.util.GlobalConstants.URL;


public interface Api {

    String BASE_URL = URL;

    @GET("movies")
    Observable<Data> getMovies();
}
