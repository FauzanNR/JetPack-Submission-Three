@file:Suppress("DEPRECATION")

package com.app.jetpacksubmissionthree.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.app.jetpacksubmissionthree.data.local.LocalDataSource
import com.app.jetpacksubmissionthree.data.local.room.FavoriteEntity
import com.app.jetpacksubmissionthree.data.local.room.FavoriteRoomDB
import com.app.jetpacksubmissionthree.data.remote.RemoteDataSource
import com.app.jetpacksubmissionthree.data.remote.network.ApiConf
import com.app.jetpacksubmissionthree.data.remote.network.ApiService
import com.app.jetpacksubmissionthree.data.remote.network.DataHelper
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultTv
import com.app.jetpacksubmissionthree.getOrAwaitValue
import com.app.jetpacksubmissionthree.utilTest.FakeDataRepository
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.serialization.ExperimentalSerializationApi
import okio.IOException
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.AfterAll
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.Spy
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
@RunWith(RobolectricTestRunner::class)
class DataRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val id = 100

    private var remoteDataMock = mock(RemoteDataSource::class.java)
    private var localDataMock = mock(LocalDataSource::class.java)
    private var dataRepositoryTest: FakeDataRepository =
        FakeDataRepository(remoteDataMock, localDataMock)

    @Spy
    lateinit var localDataSource: LocalDataSource
    lateinit var remoteDataSource: RemoteDataSource
    private lateinit var dataRepositoryResponse: FakeDataRepository

    private lateinit var db: FavoriteRoomDB

    @Before
    fun setUp() {

        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val retrofit = ApiConf.getApiServiceInstance().create(ApiService::class.java)
        db = Room.inMemoryDatabaseBuilder(context, FavoriteRoomDB::class.java)
            .allowMainThreadQueries().build()

        remoteDataSource = RemoteDataSource.getInstance(DataHelper(), retrofit)
        localDataSource = spy(LocalDataSource.getInstance(db.dataDao()))

        dataRepositoryResponse = FakeDataRepository(remoteDataSource, localDataSource)
    }

    @AfterAll
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun testRemoteDataMoviePopular() {
        val data = MutableLiveData<PagedList<ResultMovie>>()
        data.value = dataRepositoryResponse.remoteDataMoviePopular().getOrAwaitValue()

        `when`(remoteDataMock.getPopularMovie()).thenReturn(data)

        val test = dataRepositoryTest.remoteDataMoviePopular()
        verify(remoteDataMock, atLeastOnce()).getPopularMovie()

        TestCase.assertNotNull(test)
        TestCase.assertEquals(
            data.getOrAwaitValue(),
            test.getOrAwaitValue()
        )
    }

    @Test
    fun testRemoteDataTvPopular() {
        val data = MutableLiveData<PagedList<ResultTv>>()
        data.value = dataRepositoryResponse.remoteDataTvPopular().getOrAwaitValue()

        `when`(remoteDataMock.getPopularTv()).thenReturn(data)

        val test = dataRepositoryTest.remoteDataTvPopular()
        verify(remoteDataMock, atLeastOnce()).getPopularTv()
        TestCase.assertNotNull(test)
        TestCase.assertEquals(
            data.getOrAwaitValue(),
            test.getOrAwaitValue()
        )
    }

    @Test
    fun testRemoteDataMovieDetail() = runBlockingTest {
        val data = MutableLiveData<ResultMovie>()
        data.value = dataRepositoryResponse.remoteDataMovieDetail(id).getOrAwaitValue()

        `when`(remoteDataMock.getDetailMovie(id)).thenReturn(data)

        val test = dataRepositoryTest.remoteDataMovieDetail(id)
        verify(remoteDataMock, atLeastOnce()).getDetailMovie(id)

        TestCase.assertNotNull(test)
        TestCase.assertEquals(data.getOrAwaitValue(), test.getOrAwaitValue())
    }

    @Test
    fun testRemoteDataTvDetail() = runBlockingTest {
        val data = MutableLiveData<ResultTv>()
        data.value = dataRepositoryResponse.remoteDataTvDetail(id).getOrAwaitValue()

        `when`(remoteDataMock.getDetailTv(id)).thenReturn(data)

        val test = dataRepositoryTest.remoteDataTvDetail(id)
        verify(remoteDataMock, atLeastOnce()).getDetailTv(id)

        TestCase.assertNotNull(test)
        TestCase.assertEquals(data.getOrAwaitValue(), test.getOrAwaitValue())
    }

    @Test
    fun testGetLocalDataMovieFav() {
        val expected = FavoriteEntity(
            id + 6,
            1,
            "Type 1 movie",
            "Type 1 digunakan untuk data movie favorite",
            10.0,
            "https://blablabla.com"
        )
        dataRepositoryResponse.addLocalDataFav(expected)

        val data = MutableLiveData<PagedList<FavoriteEntity>>()
        data.value = dataRepositoryResponse.getLocalDataMovieFav().getOrAwaitValue()

        `when`(localDataMock.readAllMovie).thenReturn(data)

        val test = dataRepositoryTest.getLocalDataMovieFav()
        verify(localDataMock, atLeastOnce()).readAllMovie

        TestCase.assertNotNull(test)
        TestCase.assertEquals(expected, test.getOrAwaitValue()[0])
    }

    @Test
    fun testGetLocalDataTvFav() {
        val expected = FavoriteEntity(
            id + 5,
            2,
            "Type 2 tv",
            "Type 2 digunakan untuk data tv favorite",
            10.0,
            "https://blablabla.com"
        )
        dataRepositoryResponse.addLocalDataFav(expected)

        val data = MutableLiveData<PagedList<FavoriteEntity>>()
        data.value = dataRepositoryResponse.getLocalDataTvFav().getOrAwaitValue()

        `when`(localDataMock.readAllTv).thenReturn(data)

        val test = dataRepositoryTest.getLocalDataTvFav()
        verify(localDataMock, atLeastOnce()).readAllTv

        TestCase.assertNotNull(test)
        TestCase.assertEquals(expected, test.getOrAwaitValue()[0])
    }

    @Test
    fun testDeleteLocalDataFav() = runBlockingTest {
        val idL = id + 4
        val expected = FavoriteEntity(
            idL,
            2,
            "Type 2 tv",
            "Type 2 digunakan untuk data tv favorite",
            10.0,
            "https://blablabla.com"
        )
        dataRepositoryResponse.addLocalDataFav(expected)
//        verify(localDataSource, times(1)).addData(expected)
        val test = dataRepositoryResponse.getLocalDataTvFav()
        TestCase.assertNotNull(test)
        TestCase.assertEquals(expected, test.getOrAwaitValue()[0])

        dataRepositoryResponse.deleteLocalDataFav(idL)
        verify(localDataSource, times(1)).deleteData(idL)
        TestCase.assertTrue(test.getOrAwaitValue().isNullOrEmpty())
    }

    @Test
    fun testAddLocalDataFav() = runBlockingTest {
        val idL = id + 3
        val expected = FavoriteEntity(
            idL,
            2,
            "Type 2 tv",
            "Type 2 digunakan untuk data tv favorite",
            10.0,
            "https://blablabla.com"
        )
        dataRepositoryResponse.addLocalDataFav(expected)

        verify(localDataSource, atLeastOnce()).addData(expected)
        val test = dataRepositoryResponse.getLocalDataTvFav()
        TestCase.assertNotNull(test)
        TestCase.assertEquals(expected, test.getOrAwaitValue()[0])
    }

    @Test
    fun testSearchLocalDataFav() {
        val expected = FavoriteEntity(
            id,
            2,
            "Type 2 tv",
            "Type 2 digunakan untuk data tv favorite",
            10.0,
            "https://blablabla.com"
        )

        dataRepositoryResponse.addLocalDataFav(expected)

        val test = dataRepositoryResponse.searchLocalDataFav(id)
        verify(localDataSource, atLeastOnce()).searchData(id)

        TestCase.assertNotNull(test)
        TestCase.assertEquals(expected, test.getOrAwaitValue()[0])
    }

    @Test
    fun testSelectOne() {
        val idL = id + 1
        val expected2 = FavoriteEntity(
            idL,
            2,
            "Type 1 Movie",
            "Type 2 digunakan untuk data movie favorite",
            10.0,
            "https://blablabla.com"
        )
        dataRepositoryResponse.addLocalDataFav(expected2)

        val test = dataRepositoryResponse.selectOne(idL)

        verify(localDataSource, atLeastOnce()).selectOne(idL)
        TestCase.assertNotNull(test)
        TestCase.assertEquals(expected2, test.getOrAwaitValue())
    }
}



