package com.app.jetpacksubmissionthree.data.remote.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultTv
import com.app.jetpacksubmissionthree.data.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response

@Suppress("DEPRECATION")
class DataHelper {
    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)
    private val config = PagedList.Config.Builder()
        .setPageSize(24)
        .setEnablePlaceholders(false)
        .build()

    //response to live data
    fun fromApiToMovie(responseApi: ApiService): LiveData<PagedList<ResultMovie>> = runBlocking {
        val dataResponseFactory = object : PageKeyedDataSource<Int, ResultMovie>() {
            override fun loadAfter(
                params: LoadParams<Int>,
                callback: LoadCallback<Int, ResultMovie>
            ) {
                EspressoIdlingResource.increment()
                val page = params.key + 1
                scope.launch {
                    try {
                        when {
                            responseApi.getPopularMovie(page = page).isSuccessful -> {
                                val list = responseApi.getPopularMovie(page = page).body()?.results
                                callback.onResult(list as List<ResultMovie>, page)
                            }
                        }
                    } catch (exception: Exception) {
                        Log.e("dataResponseFactory", "Failed to fetch data! -> $exception")
                    }
                    EspressoIdlingResource.decrement()
                }
            }

            override fun loadInitial(
                params: LoadInitialParams<Int>,
                callback: LoadInitialCallback<Int, ResultMovie>
            ) {
                EspressoIdlingResource.increment()
                scope.launch {
                    try {
                        when {
                            responseApi.getPopularMovie(page = 1).isSuccessful -> {
                                val list = responseApi.getPopularMovie(page = 1).body()?.results
                                callback.onResult(list as List<ResultMovie>, null, 2)
                            }
                        }
                    } catch (exception: Exception) {
                        Log.e("dataResponseFactory", "Failed to fetch data! -> $exception")
                    }
                    EspressoIdlingResource.decrement()
                }
            }

            override fun loadBefore(
                params: LoadParams<Int>,
                callback: LoadCallback<Int, ResultMovie>
            ) {
            }
        }

        val dataSourceFactory = object : DataSource.Factory<Int, ResultMovie>() {
            override fun create(): DataSource<Int, ResultMovie> {
                return dataResponseFactory
            }
        }
        LivePagedListBuilder<Int, ResultMovie>(dataSourceFactory, config).build()
    }


    fun fromApiToTv(responseApi: ApiService): LiveData<PagedList<ResultTv>> = runBlocking {
        val dataResponseFactory = object : PageKeyedDataSource<Int, ResultTv>() {
            override fun loadAfter(
                params: LoadParams<Int>,
                callback: LoadCallback<Int, ResultTv>
            ) {
                EspressoIdlingResource.increment()
                val page = params.key + 1
                scope.launch {
                    try {
                        when {
                            responseApi.getPopularMovie(page = page).isSuccessful -> {
                                val list = responseApi.getPopularTv(page = page).body()?.results
                                callback.onResult(list as List<ResultTv>, page)
                            }
                        }
                    } catch (exception: Exception) {
                        Log.e("dataResponseFactory", "Failed to fetch data! -> $exception")
                    }
                    EspressoIdlingResource.decrement()
                }
            }

            override fun loadInitial(
                params: LoadInitialParams<Int>,
                callback: LoadInitialCallback<Int, ResultTv>
            ) {
                EspressoIdlingResource.increment()
                scope.launch {
                    try {
                        when {
                            responseApi.getPopularMovie(page = 1).isSuccessful -> {
                                val list = responseApi.getPopularTv(page = 1).body()?.results
                                callback.onResult(list as List<ResultTv>, null, 2)
                            }
                        }
                    } catch (exception: Exception) {
                        Log.e("dataResponseFactory", "Failed to fetch data! -> $exception")
                    }
                    EspressoIdlingResource.decrement()
                }
            }

            override fun loadBefore(
                params: LoadParams<Int>,
                callback: LoadCallback<Int, ResultTv>
            ) {
            }
        }

        val dataSourceFactory = object : DataSource.Factory<Int, ResultTv>() {
            override fun create(): DataSource<Int, ResultTv> {
                return dataResponseFactory
            }
        }
        LivePagedListBuilder<Int, ResultTv>(dataSourceFactory, config).build()
    }

    fun fromApiToDetailMovie(responseApi: Response<ResultMovie>): LiveData<ResultMovie> {
        EspressoIdlingResource.increment()
        return liveData {
            if (responseApi.isSuccessful)
                emit(
                    responseApi.body() as ResultMovie
                )
            EspressoIdlingResource.decrement()
        }
    }

    fun fromApiToDetailTv(responseApi: Response<ResultTv>): LiveData<ResultTv> {
        EspressoIdlingResource.increment()
        return liveData {
            if (responseApi.isSuccessful)
                emit(
                    responseApi.body() as ResultTv
                )
            EspressoIdlingResource.decrement()
        }
    }
}