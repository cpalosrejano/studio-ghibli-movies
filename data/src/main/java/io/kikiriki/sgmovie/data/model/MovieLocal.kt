package io.kikiriki.sgmovie.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieLocal(
    @PrimaryKey val id: String,
    val title: String,
    @ColumnInfo("title_romanised")
    val titleRomanised: String,
    @ColumnInfo("image_cartel")
    val imageCartel: String,
    @ColumnInfo("image_banner")
    val imageBanner: String,
    val description: String,
    val director: String,
    val producer: String,
    val soundtrack: String,
    @ColumnInfo("release_date")
    val releaseDate: Int,
    @ColumnInfo("running_time")
    val runningTime: Int,
    @ColumnInfo("rt_score")
    val rtScore: Int,
    val coproduction: Boolean,
    var like: Boolean = false,
)