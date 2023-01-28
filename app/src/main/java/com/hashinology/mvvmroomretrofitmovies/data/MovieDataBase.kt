package com.hashinology.mvvmroomretrofitmovies.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hashinology.mvvmroomretrofitmovies.model.Movie

@Database(
    entities = arrayOf(Movie::class),
    version = 1
)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun getDao():MovieDao

    companion object{
        private var instance: MovieDataBase? = null

        fun getInstanceDatabase(context: Context): MovieDataBase{
            if (instance == null){
                synchronized(MovieDataBase::class){
                    if (instance == null){

                        instance = setUpRoom(context)
                    }
                }
            }
            return instance!!
        }

        private fun setUpRoom(context: Context): MovieDataBase? = Room.databaseBuilder(
            context.applicationContext,
            MovieDataBase::class.java,
            "Movie-Database"
        ).build()
    }
}