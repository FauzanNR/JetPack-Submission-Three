package com.app.jetpacksubmissionthree.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteRoomDB : RoomDatabase() {
    abstract fun dataDao(): MovieFavDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteRoomDB? = null

        @JvmStatic
        fun getDb(context: Context): FavoriteRoomDB {
            if (INSTANCE == null) {
                synchronized(FavoriteRoomDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteRoomDB::class.java,
                        "fav_db"
                    ).build()
                }
            }
            return INSTANCE as FavoriteRoomDB
        }
    }
}