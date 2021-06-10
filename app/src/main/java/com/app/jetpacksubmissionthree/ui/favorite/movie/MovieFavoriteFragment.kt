package com.app.jetpacksubmissionthree.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.app.jetpacksubmissionthree.R
import com.app.jetpacksubmissionthree.databinding.MovieFavoriteFragmentBinding
import com.app.jetpacksubmissionthree.viewmodel.ViewModelFactory
import kotlinx.coroutines.runBlocking

class MovieFavoriteFragment : Fragment() {
    private lateinit var textViewInfo: TextView
    private lateinit var binding: MovieFavoriteFragmentBinding
    private val adapterFav: MovieFavoriteAdapter by lazy {
        MovieFavoriteAdapter().apply {
            notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val gridCount = resources.getInteger(R.integer.grid_column_count)
        binding = MovieFavoriteFragmentBinding.bind(
            inflater.inflate(
                R.layout.movie_favorite_fragment,
                container,
                false
            )
        )
        binding.idRecviewMovieFav.apply {
            layoutManager = GridLayoutManager(context, gridCount)
            adapter = adapterFav
        }
        textViewInfo = binding.idTextInfoMovieFav
        observeData()
        return binding.root
    }

    private fun observeData() {
        if (this.isAdded) {
            val factory = ViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(this, factory)[MovieFavoriteViewModel::class.java]
            viewModel.selectData.observe(viewLifecycleOwner, {
                if ((!it.isEmpty())) {

                    textViewInfo.visibility = View.INVISIBLE
                    runBlocking { adapterFav.submitList(it) }
                    binding.idRecviewMovieFav.visibility = View.VISIBLE
                } else {
                    binding.idRecviewMovieFav.visibility = View.INVISIBLE
                    textViewInfo.visibility = View.VISIBLE
                }

            })
        }
    }

}