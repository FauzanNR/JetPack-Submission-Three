package com.app.jetpacksubmissionthree.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultTv
import com.app.jetpacksubmissionthree.data.repository.DataRepository

class TvViewModel(private val dataRepository: DataRepository) : ViewModel() {
    fun getTvPopular(): LiveData<PagedList<ResultTv>> = dataRepository.remoteDataTvPopular()
}