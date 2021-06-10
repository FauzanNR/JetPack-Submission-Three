@file:Suppress("DEPRECATION")
package com.app.jetpacksubmissionthree.ui.favorite.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.app.jetpacksubmissionthree.R
import com.app.jetpacksubmissionthree.databinding.TvShowFavoriteFragmentBinding
import com.app.jetpacksubmissionthree.viewmodel.ViewModelFactory
import kotlinx.coroutines.runBlocking

class TvShowFavoriteFragment : Fragment() {
    private lateinit var textViewInfo: TextView
    private lateinit var binding: TvShowFavoriteFragmentBinding
    private val adapterFav: TvShowFavoriteAdapter by lazy {
        TvShowFavoriteAdapter().apply {
            notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val gridCount = resources.getInteger((R.integer.grid_column_count))
        binding = TvShowFavoriteFragmentBinding.bind(
            inflater.inflate(
                R.layout.tv_show_favorite_fragment,
                container,
                false
            )
        )
        binding.idRecviewTvFav.apply {
            layoutManager = GridLayoutManager(context, gridCount)
            adapter = adapterFav
        }
        textViewInfo = binding.idTextInfoTvFav
        observeData()
        return binding.root
    }

    private fun observeData() {
        if (this.isAdded) {
            val factory = ViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(this, factory)[TvShowFavoriteViewModel::class.java]
            viewModel.selectData.observe(viewLifecycleOwner, {
                if ((!it.isEmpty())) {
                    textViewInfo.visibility = View.INVISIBLE
                    runBlocking { adapterFav.submitList(it) }
                    binding.idRecviewTvFav.visibility = View.VISIBLE
                } else {
                    textViewInfo.visibility = View.VISIBLE
                    binding.idRecviewTvFav.visibility = View.INVISIBLE
                }

            })
        }
    }

}