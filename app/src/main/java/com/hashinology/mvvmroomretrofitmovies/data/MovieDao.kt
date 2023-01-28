package com.hashinology.mvvmroomretrofitmovies.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hashinology.mvvmroomretrofitmovies.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMove(movie: Movie)

    // 5
    // delete a specific movie because we don't know the id due to the auto generating
    @Query("Delete From movietable where imageUrl= :image")
    suspend fun deleteMovie(image: String)

    // to return if exisists movie only
    @Query("Select Exists(Select 1 From MovieTable Where name = :name) ")
    suspend fun isExisits(name: String): Boolean


    @Query("Select * From MovieTable")
    fun getLiveDataMovieRoom(): LiveData<List<Movie>>

}
