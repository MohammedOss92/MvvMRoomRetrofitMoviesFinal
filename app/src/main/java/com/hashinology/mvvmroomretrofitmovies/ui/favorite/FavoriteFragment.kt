package com.hashinology.mvvmroomretrofitmovies.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmretrofitcoroutines.ui.favorites.DeleteItemClick
import com.example.mvvmretrofitcoroutines.ui.favorites.FavoritesAdapter
import com.hashinology.mvvmroomretrofitmovies.R
import com.hashinology.mvvmroomretrofitmovies.databinding.FragmentFavoriteBinding
import com.hashinology.mvvmroomretrofitmovies.ui.MainActivity
import com.hashinology.mvvmroomretrofitmovies.ui.MovieviewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment(), DeleteItemClick {
    lateinit var movieviewModel: MovieviewModel
    private lateinit var binding: FragmentFavoriteBinding
    lateinit var myadapter:FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieviewModel = (activity as MainActivity).movieviewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        movieviewModel.getMovie.observe(viewLifecycleOwner, Observer {
            myadapter.differ.submitList(it)
        })
    }

    private fun setupRecyclerView() {
        myadapter = FavoritesAdapter(this)
        binding.rvFavorites.apply {
            adapter = myadapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onClickDelete(position: Int) {
        lifecycleScope.launch(Dispatchers.IO){
            movieviewModel.deletMovie(myadapter.differ.currentList[position].imageUrl)
        }
    }
}