package com.app.jetpacksubmissionthree.data.local.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "fav_data")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var type: Int?,
    var title: String?,
    var overview: String?,
    var vote_average: Double?,
    var backdrop_path: String?,
) : Parcelable
