package com.app.jetpacksubmissionthree.di

import android.content.Context
import com.app.jetpacksubmissionthree.data.local.LocalDataSource
import com.app.jetpacksubmissionthree.data.local.room.FavoriteRoomDB
import com.app.jetpacksubmissionthree.data.remote.RemoteDataSource
import com.app.jetpacksubmissionthree.data.remote.network.ApiConf
import com.app.jetpacksubmissionthree.data.remote.network.ApiService
import com.app.jetpacksubmissionthree.data.remote.network.DataHelper
import com.app.jetpacksubmissionthree.data.repository.DataRepository

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val retrofit = ApiConf.getApiServiceInstance().create(ApiService::class.java)
        val db = FavoriteRoomDB.getDb(context)
        val remoteDataSource = RemoteDataSource.getInstance(DataHelper(), retrofit)
        val localDataSource = LocalDataSource.getInstance(db.dataDao())
        return DataRepository.getInstance(remoteDataSource, localDataSource)
    }
}