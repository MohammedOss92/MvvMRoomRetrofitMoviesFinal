package com.hashinology.mvvmroomretrofitmovies.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hashinology.mvvmroomretrofitmovies.data.MovieDataBase
import com.hashinology.mvvmroomretrofitmovies.data.MovieRepo
import com.hashinology.mvvmroomretrofitmovies.model.Movie
import com.hashinology.mvvmroomretrofitmovies.util.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MovieviewModel(context: Application): AndroidViewModel(context) {
    private val _getMovieMutable: MutableLiveData<Resources<List<Movie>>> = MutableLiveData()
    val getMovieLiveData: LiveData<Resources<List<Movie>>> = _getMovieMutable

    private val _isExistsMovie: MutableLiveData<Boolean> = MutableLiveData()
    val isExistsMovieLiveData: LiveData<Boolean> = _isExistsMovie


    val getMovie: LiveData<List<Movie>>
    val repo: MovieRepo

    init{
        val movieDao = MovieDataBase.getInstanceDatabase(context.applicationContext).getDao()
        repo = MovieRepo(movieDao)
        // Livedata coming from database
        getMovie = repo.movieLivedataRepoRoomList
    }

    suspend fun insertMovie(movie: Movie) {
        repo.insertMovie(movie)
    }

    suspend fun deletMovie(image: String){
        repo.deleteMovie(image)
    }

    suspend fun isExists(imageUrl: String) {
        _isExistsMovie.postValue(repo.isExists(imageUrl))
    }

    suspend fun getMovieFromRetrofitclientAPI(){
        _getMovieMutable.postValue(Resources.Loading())
        try {
            var response = repo.getMovieLiveData()

            if (response.isSuccessful){
                _getMovieMutable.postValue(Resources.Success(response.body()!!))
            }else{
                _getMovieMutable.postValue(Resources.Error(response.message()))
            }
        }catch (t: Throwable){
            when (t){
                is IOException -> _getMovieMutable.postValue(Resources.Error("Network Failure"))
                else -> _getMovieMutable.postValue(Resources.Error(t.message.toString()))
            }
        }
    }
}