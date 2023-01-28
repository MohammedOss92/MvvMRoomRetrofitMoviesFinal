package com.hashinology.mvvmroomretrofitmovies.data

import androidx.lifecycle.LiveData
import com.hashinology.mvvmroomretrofitmovies.api.RetrofitClient
import com.hashinology.mvvmroomretrofitmovies.model.Movie

class MovieRepo(val movieDao: MovieDao) {

    val movieLivedataRepoRoomList: LiveData<List<Movie>> = movieDao.getLiveDataMovieRoom()

    suspend fun insertMovie(movie: Movie){
        movieDao.insertMove(movie)
    }

    // 5
    suspend fun deleteMovie(image: String){
        movieDao.deleteMovie(image)
    }

    suspend fun isExists(name: String): Boolean{
        return movieDao.isExisits(name)
    }

    suspend fun getMovieLiveData() = RetrofitClient.getInstanceValue().getMovieFromRetrofitClientAPI()
}