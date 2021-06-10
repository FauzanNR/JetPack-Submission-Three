package com.app.jetpacksubmissionthree.ui.favorite.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.app.jetpacksubmissionthree.data.local.room.FavoriteEntity
import com.app.jetpacksubmissionthree.utilTest.FakeDataRepository

class FakeTvShowFavoriteViewModelForTest(private val dataRepository: FakeDataRepository) : ViewModel() {
    val selectData: LiveData<PagedList<FavoriteEntity>> = dataRepository.getLocalDataTvFav()
}