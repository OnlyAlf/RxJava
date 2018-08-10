package com.example.alvar.rxjava.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.alvar.rxjava.model.Movie;
import com.example.alvar.rxjava.util.NetworkOperations;

import java.util.List;

public class MainViewModel extends ViewModel {

    NetworkOperations networkOperations;

    private MutableLiveData<List<Movie>> mutableLiveData;

    public void init(Context context){

        if(mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
            networkOperations = new NetworkOperations(context);
            networkOperations.setMutableLiveData(mutableLiveData);
        }
    }

    public MutableLiveData<List<Movie>> getMutableLiveData() {
        return mutableLiveData;
    }
}
