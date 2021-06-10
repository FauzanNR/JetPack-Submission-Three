package com.app.jetpacksubmissionthree.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieFavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: FavoriteEntity)

    @Query("SELECT * FROM fav_data WHERE type = 1 ORDER BY id ASC")
    fun selectMovie(): DataSource.Factory<Int, FavoriteEntity>

    @Query("SELECT * FROM fav_data WHERE type = 2 ORDER BY id ASC")
    fun selectTv(): DataSource.Factory<Int, FavoriteEntity>

    @Query("DELETE FROM fav_data WHERE id =:keyword")
    suspend fun delete(keyword: Int)

    @Query("SELECT * FROM fav_data WHERE id LIKE :keyword  ORDER BY id ASC")
    fun search(keyword: Int): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM fav_data WHERE id LIKE :keyword")
    fun selectOne(keyword: Int): LiveData<FavoriteEntity>
}