package com.app.jetpacksubmissionthree.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.app.jetpacksubmissionthree.data.local.room.FavoriteEntity
import com.app.jetpacksubmissionthree.utilTest.FakeDataRepository

@Suppress("DEPRECATION")
class FakeMovieFavoriteViewModelForTest(private val dataRepository: FakeDataRepository) : ViewModel() {
    val selectData: LiveData<PagedList<FavoriteEntity>> = dataRepository.getLocalDataMovieFav()
}