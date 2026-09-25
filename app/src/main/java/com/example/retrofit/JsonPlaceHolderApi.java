package com.example.retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("pytania")
    public Call<ArrayList<Pytanie>> getpytania();

}
