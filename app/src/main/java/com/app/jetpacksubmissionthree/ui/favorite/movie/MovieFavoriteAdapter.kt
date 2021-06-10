@file:Suppress("DEPRECATION")

package com.app.jetpacksubmissionthree.ui.favorite.movie

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
import com.app.jetpacksubmissionthree.data.local.room.FavoriteEntity
import com.app.jetpacksubmissionthree.databinding.ItemCardBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class MovieFavoriteAdapter : PagedListAdapter<FavoriteEntity, MovieFavoriteAdapter.MovieHolder>(COMPARATOR) {

    companion object {
        const val EXTRA_MOVIE_FAV = "EXTRA_MOVIE_FAV"
        private val COMPARATOR = object : DiffUtil.ItemCallback<FavoriteEntity>() {
            override fun areItemsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean =
                oldItem == newItem
        }
    }

    class MovieHolder(val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(FavoriteEntity: FavoriteEntity) {
            binding.apply {
                idItemRating.text = FavoriteEntity.vote_average.toString()
                idItemTitle.text = FavoriteEntity.title
                idItemDescription.text = FavoriteEntity.overview
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w400/${FavoriteEntity.backdrop_path}")
                    .apply(
                        RequestOptions()
                    )
                    .error(R.drawable.ic_broken_img)
                    .into(idItemImg)
                itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt(EXTRA_MOVIE_FAV, FavoriteEntity.id as Int)
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
        holder.bind(data as FavoriteEntity)
        holder.binding.idProgressMovie.isVisible = false
    }
}