package com.app.jetpacksubmissionthree.data.remote

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.app.jetpacksubmissionthree.data.remote.network.ApiService
import com.app.jetpacksubmissionthree.data.remote.network.DataHelper
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultTv

class RemoteDataSource private constructor(
    private val dataHelper: DataHelper,
    private val service: ApiService
) {

    companion object {
        @Volatile
        private var instances: RemoteDataSource? = null
        fun getInstance(helper: DataHelper, service: ApiService): RemoteDataSource =
            instances ?: synchronized(this) {
                instances ?: RemoteDataSource(helper, service).apply { instances = this }
            }
    }

    fun getPopularMovie(): LiveData<PagedList<ResultMovie>> = dataHelper.fromApiToMovie(service)

    fun getPopularTv(): LiveData<PagedList<ResultTv>> = dataHelper.fromApiToTv(service)

    suspend fun getDetailMovie(id: Int): LiveData<ResultMovie> =
        dataHelper.fromApiToDetailMovie(service.getDetailMovie(movieId = id))

    suspend fun getDetailTv(id: Int): LiveData<ResultTv> =
        dataHelper.fromApiToDetailTv(service.getDetailTv(tvId = id))
}