package com.app.jetpacksubmissionthree.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.app.jetpacksubmissionthree.data.local.LocalDataSource
import com.app.jetpacksubmissionthree.data.local.room.FavoriteEntity
import com.app.jetpacksubmissionthree.data.remote.RemoteDataSource
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultTv
import kotlinx.coroutines.runBlocking

class DataRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {


    companion object {
        @Volatile
        private var instance: DataRepository? = null
        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource
        ): DataRepository =
            instance ?: synchronized(DataRepository::class.java) {
                DataRepository(remoteDataSource, localDataSource).apply { instance = this }
            }
    }

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
    fun addLocalDataFav(data: FavoriteEntity) = runBlocking { localDataSource.addData(data) }
    fun searchLocalDataFav(key: Int): LiveData<List<FavoriteEntity>> =
        runBlocking { localDataSource.searchData(key) }

    fun selectOne(key: Int): LiveData<FavoriteEntity> = localDataSource.selectOne(key)
}
