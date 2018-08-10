package com.example.alvar.rxjava.util;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.alvar.rxjava.model.Data;
import com.example.alvar.rxjava.model.Movie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.alvar.rxjava.util.GlobalConstants.CACHE_CONTROL;
import static com.example.alvar.rxjava.util.GlobalConstants.CACHE_SIZE;
import static com.example.alvar.rxjava.util.GlobalConstants.MAX_AGE;

public class NetworkOperations {

    private Api api;
    private Disposable disposable;
    private CompositeDisposable compositeDisposable;
    Context context;

    public NetworkOperations(Context context) {
        this.context = context;

    }

    public void setMutableLiveData(final MutableLiveData<List<Movie>> mutableLiveData){

        int cacheSize = CACHE_SIZE;
        Cache cache = new Cache(context.getCacheDir(),cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new ForceCacheInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        api = retrofit.create(Api.class);
        final List<Movie> movieList = new ArrayList<>();

        disposable = api.getMovies().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Data>() {
                            @Override
                            public void accept(Data data) throws Exception {

                                for(Movie movie : data.getData()){
                                    movieList.add(movie);
                                }
                                mutableLiveData.postValue(movieList);
                            }

                        });


    }

    public class ForceCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder().addHeader(CACHE_CONTROL, MAX_AGE);
            if (!isOnline()) {
                builder.cacheControl(CacheControl.FORCE_CACHE);
            }

            return chain.proceed(builder.build());
        }

        public boolean isOnline() {
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
            return false;
        }
    }

}
