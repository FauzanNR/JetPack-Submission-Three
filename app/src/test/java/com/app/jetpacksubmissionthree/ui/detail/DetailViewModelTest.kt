@file:Suppress("DEPRECATION")

package com.app.jetpacksubmissionthree.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
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
import junit.framework.Assert.assertEquals
import junit.framework.TestCase
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
class DetailViewModelTest {
    val id = 100

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Spy
    lateinit var dataRepository: FakeDataRepository

    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var localDataSource: LocalDataSource

    lateinit var viewModelTest: DetailViewModelForTest
    lateinit var db: FavoriteRoomDB

    @Before
    fun setUp() {

        val context =
            InstrumentationRegistry.getInstrumentation().targetContext
        val db = Room.inMemoryDatabaseBuilder(context, FavoriteRoomDB::class.java)
            .allowMainThreadQueries().build()
        val retrofit = ApiConf.getApiServiceInstance().create(ApiService::class.java)

        remoteDataSource = (RemoteDataSource.getInstance(DataHelper(), retrofit))
        localDataSource = (LocalDataSource.getInstance(db.dataDao()))
        dataRepository = spy(FakeDataRepository(remoteDataSource, localDataSource))


        viewModelTest = DetailViewModelForTest(dataRepository)

    }

    @AfterAll
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun testGetMovieDetail() = runBlocking {
        val data = MutableLiveData<ResultMovie>()
        data.value = remoteDataSource.getDetailMovie(id).value

        `when`(dataRepository.remoteDataMovieDetail(id)).thenReturn(data)
        val test = viewModelTest.getMovieDetail(id)
        verify(dataRepository, atLeastOnce()).remoteDataMovieDetail(id)

        assertNotNull(test)
        assertEquals(data.value, test.value)
    }

    @Test
    fun testGetTvDetail() = runBlocking {

        val data = MutableLiveData<ResultTv>()
        data.value = remoteDataSource.getDetailTv(id).value

        `when`(dataRepository.remoteDataTvDetail(id)).thenReturn(data)
        val test = viewModelTest.getTvDetail(id)
        verify(dataRepository, atLeastOnce()).remoteDataTvDetail(id)

        assertNotNull(test)
        assertEquals(data.value, test.value)
    }

    @Test
    fun testAddLocalDataFav() {
        val idL = id + 3
        val expected = FavoriteEntity(
            idL,
            2,
            "Type 2 tv",
            "Type 2 digunakan untuk data tv favorite",
            10.0,
            "https://blablabla.com"
        )

        viewModelTest.addDataLocal(expected)
        verify(dataRepository, atLeastOnce()).addLocalDataFav(expected)
        val test = viewModelTest.selectOne(idL)

        assertNotNull(test)
        assertEquals(expected, test.getOrAwaitValue())
    }


    @Test
    fun testSearchDataLocal() {
        val idL = id + 2
        val expected = FavoriteEntity(
            idL,
            2,
            "Type 2 tv",
            "Type 2 digunakan untuk data tv favorite",
            10.0,
            "https://blablabla.com"
        )

        viewModelTest.addDataLocal(expected)

        val test = viewModelTest.searchDataLocal(idL)
        verify(dataRepository, atLeastOnce()).searchLocalDataFav(idL)

        assertNotNull(test)
        assertEquals(expected, test.getOrAwaitValue()[0])
    }

    @Test
    fun testSelectOne() {
        val idL = id + 4
        val expected = FavoriteEntity(
            idL,
            2,
            "Type 2 tv",
            "Type 2 digunakan untuk data tv favorite",
            10.0,
            "https://blablabla.com"
        )
        viewModelTest.addDataLocal(expected)

        val test = viewModelTest.selectOne(idL)
        verify(dataRepository, atLeastOnce()).selectOne(idL)


        assertNotNull(test)
        assertEquals(expected, test.getOrAwaitValue())
    }

    @Test
    fun testDeleteLocalDataFav() {
        val idL = id + 5
        val expected = FavoriteEntity(
            idL,
            2,
            "Type 2 tv",
            "Type 2 digunakan untuk data tv favorite",
            10.0,
            "https://blablabla.com"
        )
        viewModelTest.addDataLocal(expected)
        val test = viewModelTest.selectOne(idL)
        assertNotNull(test)
        assertEquals(expected, test.getOrAwaitValue())

        viewModelTest.deleteLocalDataFav(idL)
        verify(dataRepository, times(1)).deleteLocalDataFav(idL)
        val testDel = viewModelTest.selectOne(idL)
        TestCase.assertEquals(null, testDel.getOrAwaitValue())
    }
}