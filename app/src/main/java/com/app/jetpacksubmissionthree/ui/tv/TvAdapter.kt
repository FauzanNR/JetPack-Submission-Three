@file:Suppress("DEPRECATION")
package com.app.jetpacksubmissionthree.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.jetpacksubmissionthree.R
import com.app.jetpacksubmissionthree.data.model.TvModel
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultTv
import com.app.jetpacksubmissionthree.databinding.ItemCardBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvAdapter : PagedListAdapter<ResultTv, TvAdapter.TvHolder>(COMPARATOR) {

    companion object {
        const val EXTRA_TV = "EXTRA_TV"
        private val COMPARATOR = object : DiffUtil.ItemCallback<ResultTv>() {
            override fun areItemsTheSame(oldItem: ResultTv, newItem: ResultTv): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ResultTv, newItem: ResultTv): Boolean =
                oldItem == newItem
        }
    }

    private var dataTv = ArrayList<ResultTv>()

    fun setDataAdapter(data: TvModel) {
        dataTv.clear()
        data.resultModels.let { dataTv.addAll(it) }
        notifyDataSetChanged()
    }

    class TvHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resultTv: ResultTv) {
            binding.apply {
                idItemRating.text = resultTv.vote_average.toString()
                idItemTitle.text = resultTv.name
                idItemDescription.text = resultTv.overview
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w400/${resultTv.poster_path}")
                    .apply(
                        RequestOptions()
                    )
                    .error(R.drawable.ic_broken_img)
                    .into(idItemImg)
                itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt(EXTRA_TV, resultTv.id)
                    Navigation.findNavController(itemView)
                        .navigate(R.id.detailFragment, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvHolder(binding)
    }

    override fun onBindViewHolder(holder: TvHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data as ResultTv)

    }
//    override fun getItemCount(): Int = dataTv.size
}