package com.app.jetpacksubmissionthree.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.app.jetpacksubmissionthree.data.remote.RemoteDataSource
import com.app.jetpacksubmissionthree.data.remote.network.ApiConf
import com.app.jetpacksubmissionthree.data.remote.network.ApiService
import com.app.jetpacksubmissionthree.data.remote.network.DataHelper
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissionthree.data.repository.DataRepository
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<PagedList<ResultMovie>>

    @Mock
    lateinit var dataRepository: DataRepository

    lateinit var remoteDataSource: RemoteDataSource

    lateinit var viewModelTest: MovieViewModel

    @Before
    fun setUp() {
        val retrofit = ApiConf.getApiServiceInstance().create(ApiService::class.java)
        remoteDataSource = RemoteDataSource.getInstance(DataHelper(), retrofit)
        viewModelTest = MovieViewModel(dataRepository)
    }

    @Test
    fun getMoviePopular() {
        val data = MutableLiveData<PagedList<ResultMovie>>()
        data.value = remoteDataSource.getPopularMovie().value

        Mockito.`when`(dataRepository.remoteDataMoviePopular()).thenReturn(data)
        val test = viewModelTest.getMoviePopular()
        verify(dataRepository, Mockito.atLeastOnce()).remoteDataMoviePopular()

        TestCase.assertNotNull(test)
        TestCase.assertEquals(data.value, test.value)

        viewModelTest.getMoviePopular().observeForever(observer)
        verify(observer).onChanged(data.value)
    }
}