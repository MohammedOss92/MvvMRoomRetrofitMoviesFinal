package com.hashinology.mvvmroomretrofitmovies.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hashinology.mvvmroomretrofitmovies.R
import com.hashinology.mvvmroomretrofitmovies.databinding.FragmentHomeBinding
import com.hashinology.mvvmroomretrofitmovies.ui.MainActivity
import com.hashinology.mvvmroomretrofitmovies.ui.MovieviewModel
import com.hashinology.mvvmroomretrofitmovies.util.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), ClickedInterface {
    lateinit var binding: FragmentHomeBinding
    lateinit var myadapter: MovieHomeAdapter
    lateinit var movieviewModel: MovieviewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieviewModel = ( activity as MainActivity ).movieviewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 1
        // we should use inflater not layoutInflater because we need the inflater of this fragment not the fragments' activity
        // this line was edited in each fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // 2
        // we didn't call this fun and we observed the livedata without calling it so no data was fetched from api
        lifecycleScope.launch(Dispatchers.IO){
            movieviewModel.getMovieFromRetrofitclientAPI()
        }

        movieviewModel.getMovieLiveData.observe(viewLifecycleOwner, Observer { resource ->
            when(resource){
                is Resources.Success ->{
                    hideProgressBar()
                    hideErrorMsg()
                    resource.data.let {
                        myadapter.differ.submitList(it)
//                        myadapter.differ.submitList(response.data!!)
                    }

                }

                is Resources.Loading -> {
                    showProgressBar()
                    hideErrorMsg()
                }

                is Resources.Error ->{
                    hideProgressBar()
                    showErrorMsg(resource.message)
                }
            }
        })
    }

    private fun showErrorMsg(message: String?) {
        binding.tvError.apply {
            visibility = View.VISIBLE
            text = message
        }
    }

    private fun showProgressBar() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideErrorMsg() {
        binding.tvError.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        binding.pbLoading.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView() {
        myadapter = MovieHomeAdapter(this)
        binding.rvMovies.apply {
            adapter = myadapter
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, true)
        }
    }

    override fun clickedItem(position: Int) {
        val bundle = Bundle().apply {
            putSerializable("movie",myadapter.differ.currentList.get(position))
        }

        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }

}