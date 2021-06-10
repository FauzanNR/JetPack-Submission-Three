package com.app.jetpacksubmissionthree.ui.detail

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.jetpacksubmissionthree.R
import com.app.jetpacksubmissionthree.data.local.room.FavoriteEntity
import com.app.jetpacksubmissionthree.databinding.DetailFragmentBinding
import com.app.jetpacksubmissionthree.ui.favorite.movie.MovieFavoriteAdapter.Companion.EXTRA_MOVIE_FAV
import com.app.jetpacksubmissionthree.ui.movie.MovieAdapter.Companion.EXTRA_MOVIE
import com.app.jetpacksubmissionthree.ui.tv.TvAdapter.Companion.EXTRA_TV
import com.app.jetpacksubmissionthree.viewmodel.FragmentModel
import com.app.jetpacksubmissionthree.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailFragment : FragmentModel(), View.OnClickListener {

    private lateinit var binding: DetailFragmentBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var bottomNavigationView: BottomNavigationView

    companion object {
        var isEmpty = true
        private var idData = 0
        private lateinit var entity: FavoriteEntity
    }

    private fun setType() {
        try {
            if (this.isAdded and this.isVisible)
                if (arguments != null)
                    arguments?.let {
                        when {
                            it.containsKey(EXTRA_MOVIE) -> {
                                Log.d("CONNECTED", EXTRA_MOVIE)
                                idData = it.getInt(EXTRA_MOVIE)
//                                changeIcon()
                                viewModel.getMovieDetail(idData)
                                    .observe(viewLifecycleOwner, {

                                        binding.idDetailCollap.title = it.original_title
                                        binding.idDetailDescription.text = it.overview
                                        binding.idDetailRating.text = it.vote_average.toString()
                                        this.context?.let { it1 ->
                                            Glide.with(it1)
                                                .load("https://image.tmdb.org/t/p/w500/${it.poster_path}")
                                                .apply(
                                                    RequestOptions()
                                                )
                                                .error(R.drawable.ic_broken_img)
                                                .into(binding.idDetailImg)
                                        }
                                        entity = FavoriteEntity(
                                            it.id,
                                            1,
                                            it.original_title,
                                            it.overview,
                                            it.vote_average,
                                            it.poster_path
                                        )
                                    })
                            }
                            it.containsKey(EXTRA_TV) -> {
                                idData = it.getInt(EXTRA_TV)
                                Log.d("CONNECTED", EXTRA_TV + idData)
                                viewModel.getTvDetail(idData)
                                    .observe(viewLifecycleOwner, {
                                        binding.idDetailCollap.title = it.original_name
                                        binding.idDetailDescription.text = it.overview
                                        binding.idDetailRating.text = it.vote_average.toString()
                                        this.context?.let { it1 ->
                                            Glide.with(it1)
                                                .load("https://image.tmdb.org/t/p/w500/${it.poster_path}")
                                                .apply(
                                                    RequestOptions()
                                                )
                                                .error(R.drawable.ic_broken_img)
                                                .into(binding.idDetailImg)
                                        }

                                        entity = FavoriteEntity(
                                            it.id,
                                            2,
                                            it.original_name,
                                            it.overview,
                                            it.vote_average,
                                            it.poster_path
                                        )
                                    })
                            }

                            it.containsKey(EXTRA_MOVIE_FAV) -> {
                                idData = it.getInt(EXTRA_MOVIE_FAV)
                                Log.d("CONNECTED", EXTRA_MOVIE_FAV + idData)
                                viewModel.selectOne(
                                    it.getInt(EXTRA_MOVIE_FAV)
                                )
                                    .observe(viewLifecycleOwner, {
                                        if (it != null) {
                                            binding.idDetailDescription.text = it.overview
                                            binding.idDetailCollap.title = it.title
                                            binding.idDetailRating.text = it.vote_average.toString()
                                            this.context?.let { it1 ->
                                                Glide.with(it1)
                                                    .load("https://image.tmdb.org/t/p/w500/${it.backdrop_path}")
                                                    .apply(
                                                        RequestOptions()
                                                    )
                                                    .error(R.drawable.ic_broken_img)
                                                    .into(binding.idDetailImg)
                                            }
                                            entity = FavoriteEntity(
                                                it.id,
                                                it.type,
                                                it.title,
                                                it.overview,
                                                it.vote_average,
                                                it.backdrop_path
                                            )
                                        }
                                    })
                            }
                            else -> {
                                Log.d("Log Detail", idData.toString())
                            }
                        }
                    }
        } catch (ex: Exception) {
            Log.d("Log Detail", ex.message.toString())
        }
    }


    override fun onDisconnected() {}

    override fun onConnected() {
        setType()
    }

    override fun queryApi(q: String) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.detail_fragment, container, false)
        binding = DetailFragmentBinding.bind(view)
        binding.idFabDetail.setOnClickListener(this@DetailFragment)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setType()
        checkIfItEmptyOnDataBase(idData)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bottomNavigationView =
            requireActivity().findViewById<View>(R.id.id_bottom_naview) as BottomNavigationView
        bottomNavigationView.visibility = View.GONE
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().invalidateOptionsMenu()
        inflater.inflate(R.menu.share, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.id_share_btn -> {
            val intent: Intent = Intent(Intent.ACTION_SEND).setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, "https://github.com/FauzanNR/Apps")
            startActivity(Intent.createChooser(intent, "Share Via"))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNavigationView.visibility = View.VISIBLE
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.idFabDetail -> {
                addDelete()
            }
        }
    }

    private fun addDelete() {
        if (!TextUtils.isEmpty(entity.toString())) {
            if (checkIfItEmptyOnDataBase(idData)) {
                viewModel.addDataLocal(entity)
                Toast.makeText(this.context, "Added to favorite", Toast.LENGTH_SHORT).show()
                binding.idFabDetail.setImageResource(R.drawable.ic_favorited)
            } else {
                viewModel.deleteLocalDataFav(idData)
                Toast.makeText(this.context, "Delete from favorite", Toast.LENGTH_SHORT).show()
                binding.idFabDetail.setImageResource(R.drawable.ic_favorite_add)
            }
        } else {
            Toast.makeText(this.context, "Failed to add!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkIfItEmptyOnDataBase(s: Int): Boolean {
        viewModel.searchDataLocal(s).observe(viewLifecycleOwner, {
            cekEmptiness(it.isEmpty())
            changeIcon(it.isEmpty())
        })
        return isEmpty
    }

    fun cekEmptiness(boolean: Boolean) {
        isEmpty = boolean
    }

    private fun changeIcon(cek: Boolean) {
        if (cek) {
            binding.idFabDetail.setImageResource(R.drawable.ic_favorite_add)
        } else {
            binding.idFabDetail.setImageResource(R.drawable.ic_favorited)
        }
    }
}