package com.app.jetpacksubmissionthree.utilTest

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.app.jetpacksubmissionthree.data.local.LocalDataSource
import com.app.jetpacksubmissionthree.data.local.room.FavoriteEntity
import com.app.jetpacksubmissionthree.data.remote.RemoteDataSource
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultTv
import kotlinx.coroutines.runBlocking

open class FakeDataRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun remoteDataMoviePopular(): LiveData<PagedList<ResultMovie>> =
        remoteDataSource.getPopularMovie()

    fun remoteDataTvPopular(): LiveData<PagedList<ResultTv>> = remoteDataSource.getPopularTv()
    fun remoteDataMovieDetail(id: Int): LiveData<ResultMovie> =
        runBlocking { remoteDataSource.getDetailMovie(id) }

    fun remoteDataTvDetail(id: Int): LiveData<ResultTv> =
        runBlocking { remoteDataSource.getDetailTv(id) }

    fun getLocalDataMovieFav(): LiveData<PagedList<FavoriteEntity>> = localDataSource.readAllMovie
    fun getLocalDataTvFav(): LiveData<PagedList<FavoriteEntity>> = localDataSource.readAllTv
    fun deleteLocalDataFav(key: Int) = runBlocking { localDataSource.deleteData(key) }
    fun addLocalDataFav(data: FavoriteEntity) = runBlocking {
        localDataSource.addData(data)
    }

    fun searchLocalDataFav(key: Int) = localDataSource.searchData(key)
    fun selectOne(key: Int) = localDataSource.selectOne(key)
}
