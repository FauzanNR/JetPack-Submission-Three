package com.app.jetpacksubmissionthree.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissionthree.data.repository.DataRepository

class MovieViewModel(private val dataRepository: DataRepository) : ViewModel() {
    fun getMoviePopular(): LiveData<PagedList<ResultMovie>> =
        dataRepository.remoteDataMoviePopular()
}