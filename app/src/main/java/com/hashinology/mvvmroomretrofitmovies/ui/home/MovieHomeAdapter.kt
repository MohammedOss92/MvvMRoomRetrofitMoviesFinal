package com.hashinology.mvvmroomretrofitmovies.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hashinology.mvvmroomretrofitmovies.R
import com.hashinology.mvvmroomretrofitmovies.model.Movie

class MovieHomeAdapter(val clickedInterface: ClickedInterface): RecyclerView.Adapter<MovieHomeAdapter.ViewHolder>() {
    private val callback = object:DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val movieimage = itemView.findViewById<ImageView>(R.id.ivMovie)
        val movieCategory = itemView.findViewById<TextView>(R.id.tvMovieCategory)
        val movieName = itemView.findViewById<TextView>(R.id.tvMovieName)

        init {
            itemView.setOnClickListener{
                clickedInterface.clickedItem(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieClickedItem = differ.currentList[position]
        holder.movieName.text = movieClickedItem.name
        holder.movieCategory.text = movieClickedItem.category
        Glide.with(holder.movieimage)
            .load(movieClickedItem.imageUrl)
            .into(holder.movieimage)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}