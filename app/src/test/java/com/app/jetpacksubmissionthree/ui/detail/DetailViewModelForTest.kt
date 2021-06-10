package com.app.jetpacksubmissionthree.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.jetpacksubmissionthree.data.local.room.FavoriteEntity
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultTv
import com.app.jetpacksubmissionthree.utilTest.FakeDataRepository

class DetailViewModelForTest(private val dataRepository: FakeDataRepository) : ViewModel() {

    fun getMovieDetail(id: Int): LiveData<ResultMovie> =
        dataRepository.remoteDataMovieDetail(id) as MutableLiveData<ResultMovie>

    fun getTvDetail(id: Int): MutableLiveData<ResultTv> =
        dataRepository.remoteDataTvDetail(id) as MutableLiveData<ResultTv>

    fun addDataLocal(data: FavoriteEntity) = dataRepository.addLocalDataFav(data)

    fun searchDataLocal(key: Int) = dataRepository.searchLocalDataFav(key)

    fun selectOne(key: Int) = dataRepository.selectOne(key)

    fun deleteLocalDataFav(key: Int) = dataRepository.deleteLocalDataFav(key)
}