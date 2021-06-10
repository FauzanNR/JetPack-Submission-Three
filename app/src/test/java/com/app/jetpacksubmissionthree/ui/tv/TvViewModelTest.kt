package com.app.jetpacksubmissionthree.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.app.jetpacksubmissionthree.data.remote.RemoteDataSource
import com.app.jetpacksubmissionthree.data.remote.network.ApiConf
import com.app.jetpacksubmissionthree.data.remote.network.ApiService
import com.app.jetpacksubmissionthree.data.remote.network.DataHelper
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultTv
import com.app.jetpacksubmissionthree.data.repository.DataRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@Suppress("DEPRECATION")
@RunWith(MockitoJUnitRunner::class)
class TvViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<PagedList<ResultTv>>

    @Mock
    lateinit var dataRepository: DataRepository

    lateinit var remoteDataSource: RemoteDataSource

    lateinit var viewModelTest: TvViewModel

    @Before
    fun setUp() {
        val retrofit = ApiConf.getApiServiceInstance().create(ApiService::class.java)
        remoteDataSource = RemoteDataSource.getInstance(DataHelper(), retrofit)
        viewModelTest = TvViewModel(dataRepository)
    }

    @Test
    fun testGetTvPopular() {
        val data = MutableLiveData<PagedList<ResultTv>>()
        data.value = remoteDataSource.getPopularTv().value

        Mockito.`when`(dataRepository.remoteDataTvPopular()).thenReturn(data)
        val test = viewModelTest.getTvPopular()
        Mockito.verify(dataRepository, Mockito.atLeastOnce()).remoteDataTvPopular()

        assertNotNull(test)
        assertEquals(data.value, test.value)

        viewModelTest.getTvPopular().observeForever(observer)
        Mockito.verify(observer).onChanged(remoteDataSource.getPopularTv().value)
    }
}