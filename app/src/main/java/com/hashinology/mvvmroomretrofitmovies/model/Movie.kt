package com.hashinology.mvvmroomretrofitmovies.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "MovieTable")
data class Movie(
    val name: String,
    val imageUrl: String,
    val category: String,
    val desc: String

): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}