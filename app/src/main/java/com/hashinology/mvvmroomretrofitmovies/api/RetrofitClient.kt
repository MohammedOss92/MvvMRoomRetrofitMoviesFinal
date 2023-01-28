package com.hashinology.mvvmroomretrofitmovies.api

import com.hashinology.mvvmroomretrofitmovies.model.Movie
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitClient {
    @GET("movielist.json")
    suspend fun getMovieFromRetrofitClientAPI(): Response<List<Movie>>

    companion object{
        var instance: RetrofitClient? = null
        fun getInstanceValue(): RetrofitClient{
            if (instance == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://howtodoandroid.com/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
                instance = retrofit.create(RetrofitClient::class.java)
            }
            return instance!!
        }
    }
}