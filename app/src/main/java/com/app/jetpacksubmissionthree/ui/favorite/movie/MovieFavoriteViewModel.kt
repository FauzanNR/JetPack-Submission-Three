package com.app.jetpacksubmissionthree.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.app.jetpacksubmissionthree.data.local.room.FavoriteEntity
import com.app.jetpacksubmissionthree.data.repository.DataRepository

class MovieFavoriteViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val selectData: LiveData<PagedList<FavoriteEntity>> = dataRepository.getLocalDataMovieFav()
}