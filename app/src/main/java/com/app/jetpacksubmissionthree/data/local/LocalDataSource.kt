package com.app.jetpacksubmissionthree.data.local

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.app.jetpacksubmissionthree.data.local.room.FavoriteEntity
import com.app.jetpacksubmissionthree.data.local.room.MovieFavDao
import kotlinx.coroutines.Dispatchers

@Suppress("DEPRECATION")
class LocalDataSource private constructor(private val dataBase: MovieFavDao) {
    companion object {
        private var INSTANCE: LocalDataSource? = null
        fun getInstance(dataDao: MovieFavDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(dataDao)
    }

    private val config = PagedList.Config.Builder()
        .setPageSize(10)
        .setEnablePlaceholders(false)
        .build()

    val readAllMovie: LiveData<PagedList<FavoriteEntity>> =
        LivePagedListBuilder(dataBase.selectMovie(), config).build()
    val readAllTv: LiveData<PagedList<FavoriteEntity>> =
        LivePagedListBuilder(dataBase.selectTv(), config).build()

    suspend fun addData(dataUser: FavoriteEntity) = dataBase.insert(dataUser)
    suspend fun deleteData(key: Int) = dataBase.delete(key)
    fun searchData(key: Int) = dataBase.search(key)
    fun selectOne(key: Int) = dataBase.selectOne(key)
}