@file:Suppress("DEPRECATION")
package com.app.jetpacksubmissionthree.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.jetpacksubmissionthree.R
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissionthree.databinding.ItemCardBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class MovieAdapter : PagedListAdapter<ResultMovie, MovieAdapter.MovieHolder>(COMPARATOR) {

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        private val COMPARATOR = object : DiffUtil.ItemCallback<ResultMovie>() {
            override fun areItemsTheSame(oldItem: ResultMovie, newItem: ResultMovie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ResultMovie, newItem: ResultMovie): Boolean =
                oldItem == newItem
        }
    }

    class MovieHolder(val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(resultMovie: ResultMovie) {
            binding.apply {
                idItemRating.text = resultMovie.vote_average.toString()
                idItemTitle.text = resultMovie.title
                idItemDescription.text = resultMovie.overview
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w400/${resultMovie.poster_path}")
                    .apply(
                        RequestOptions()
                    )
                    .error(R.drawable.ic_broken_img)
                    .into(idItemImg)
                itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt(EXTRA_MOVIE, resultMovie.id)
                    Navigation.findNavController(itemView)
                        .navigate(R.id.detailFragment, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val bindingData =
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(bindingData)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val data = getItem(position)
        holder.binding.idProgressMovie.isVisible = true
        holder.bind(data as ResultMovie)
        holder.binding.idProgressMovie.isVisible = false

    }
}