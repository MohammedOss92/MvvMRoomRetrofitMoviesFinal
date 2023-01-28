package com.example.mvvmretrofitcoroutines.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hashinology.mvvmroomretrofitmovies.R
import com.hashinology.mvvmroomretrofitmovies.model.Movie

class FavoritesAdapter (private val deleteItemClick: DeleteItemClick): RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.favorites_list_item,parent,false)
        return FavoritesViewHolder(view)
    }
    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val movie = differ.currentList[position]

        holder.tvMovieNameFv.text = movie.name
        holder.tvMovieCategoryFv.text = movie.category
        Glide.with(holder.itemView.context).load(movie.imageUrl).into(holder.ivMovieFv)

        holder.ibDelete.setOnClickListener{
            deleteItemClick.onClickDelete(holder.adapterPosition)
        }

        }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class FavoritesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivMovieFv = itemView.findViewById<ImageView>(R.id.ivMovieFv)
        val tvMovieNameFv = itemView.findViewById<TextView>(R.id.tvMovieNameFv)
        val tvMovieCategoryFv = itemView.findViewById<TextView>(R.id.tvMovieCategoryFv)
        val ibDelete = itemView.findViewById<ImageButton>(R.id.ibDelete)

    }
}