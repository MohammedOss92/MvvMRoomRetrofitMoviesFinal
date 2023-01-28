package com.hashinology.mvvmroomretrofitmovies.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.hashinology.mvvmroomretrofitmovies.R
import com.hashinology.mvvmroomretrofitmovies.databinding.FragmentDetailsBinding
import com.hashinology.mvvmroomretrofitmovies.ui.MainActivity
import com.hashinology.mvvmroomretrofitmovies.ui.MovieviewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailsFragment : Fragment() {
    lateinit var movieviewModel: MovieviewModel
    lateinit var binding: FragmentDetailsBinding

    val args: DetailsFragmentArgs by navArgs()

    var isExists = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieviewModel = (activity as MainActivity).movieviewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = args.movie

        Glide.with(requireContext())
            .load(movie.imageUrl)
            .into(binding.ivMovieDetail)
        binding.tvMovieDescDetail.text = "● " + movie.desc
        binding.tvMovieCategoryDetail.text = "● " + movie.category
        binding.tvMovieNameDetail.text = movie.name


        lifecycleScope.launch(Dispatchers.IO) {
            movieviewModel.isExists(movie.name)
        }
        movieviewModel.isExistsMovieLiveData.observe(viewLifecycleOwner, Observer {
            isExists = it
            binding.tbFav.isChecked = it
        })

        binding.tbFav.setOnClickListener {
            if (!isExists) {
                lifecycleScope.launch(Dispatchers.IO) {
                    movieviewModel.insertMovie(movie)
                }
                Toast.makeText(requireContext(), "Movie is added successfully ", Toast.LENGTH_SHORT)
                    .show()
                //3
                // change the status of the boolean after we add it to prevent adding the same movie after destroying the fragment
                isExists = true
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    movieviewModel.deletMovie(movie.imageUrl)
                }
                Toast.makeText(requireContext(), "Movie deleted", Toast.LENGTH_SHORT).show()
                isExists = false
            }
        }
    }
}
