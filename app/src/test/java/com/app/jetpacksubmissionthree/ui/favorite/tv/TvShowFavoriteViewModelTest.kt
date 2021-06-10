package com.app.jetpacksubmissionthree.ui.favorite.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.app.jetpacksubmissionthree.data.local.LocalDataSource
import com.app.jetpacksubmissionthree.data.local.room.FavoriteEntity
import com.app.jetpacksubmissionthree.data.local.room.FavoriteRoomDB
import com.app.jetpacksubmissionthree.data.remote.RemoteDataSource
import com.app.jetpacksubmissionthree.data.remote.network.ApiConf
import com.app.jetpacksubmissionthree.data.remote.network.ApiService
import com.app.jetpacksubmissionthree.data.remote.network.DataHelper
import com.app.jetpacksubmissionthree.getOrAwaitValue
import com.app.jetpacksubmissionthree.utilTest.FakeDataRepository
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.AfterAll
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Spy
import org.robolectric.RobolectricTestRunner

@Suppress("DEPRECATION")
@RunWith(RobolectricTestRunner::class)
class TvShowFavoriteViewModelTest {

    val id = 100

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Spy
    lateinit var dataRepository: FakeDataRepository

    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var localDataSource: LocalDataSource

    lateinit var viewModelTest: FakeTvShowFavoriteViewModelForTest
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
        dataRepository = Mockito.spy(FakeDataRepository(remoteDataSource, localDataSource))

        viewModelTest = FakeTvShowFavoriteViewModelForTest(dataRepository)
    }

    @AfterAll
    fun tearDown() {
        db.close()
    }

    @Test
    fun selectData() {
        val expected = FavoriteEntity(
            id + 6,
            2,
            "Type 2 Tv show",
            "Type 2 digunakan untuk data tv show favorite",
            10.0,
            "https://blablabla.com"
        )
        dataRepository.addLocalDataFav(expected)

        val test = viewModelTest.selectData
        Mockito.verify(dataRepository, Mockito.atLeastOnce()).getLocalDataTvFav()

        TestCase.assertNotNull(test)
        TestCase.assertEquals(expected, test.getOrAwaitValue()[0])
    }
}