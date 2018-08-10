package com.example.alvar.rxjava.model;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.example.alvar.rxjava.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

public class Movie {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("poster")
    @Expose
    private String poster;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


    @BindingAdapter({"android:poster"})
    public static void setImageUrl(ImageView view, String imageURL){
        Picasso.get()
                .load(imageURL)
                .fit()
                .error(R.drawable.ic_launcher_background)
                .into(view);
    }
}
