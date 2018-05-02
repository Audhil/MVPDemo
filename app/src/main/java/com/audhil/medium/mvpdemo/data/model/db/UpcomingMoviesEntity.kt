package com.audhil.medium.mvpdemo.data.model.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.audhil.medium.mvpdemo.data.local.db.AppDBNames
import com.google.gson.annotations.SerializedName

/*
 * Created by mohammed-2284 on 02/05/18.
 */

@Entity(
        tableName = AppDBNames.UPCOMING_MOVIES_TABLE_NAME,
        indices = [(Index(value = ["title"], unique = true))]
)
data class UpcomingMoviesEntity(
        @PrimaryKey(autoGenerate = true)
        var index: Int = 0,
        @SerializedName("total_results")
        var voteAverage: Float = 0f,
        @SerializedName("backdrop_path")
        var backdropPath: String? = null,
        @SerializedName("adult")
        var adult: Boolean = false,
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("title")
        var title: String? = null,
        @SerializedName("overview")
        var overview: String? = null,
        @SerializedName("original_language")
        var originalLanguage: String? = null,
        @SerializedName("release_date")
        var releaseDate: String? = null,
        @SerializedName("original_title")
        var originalTitle: String? = null,
        @SerializedName("vote_count")
        var voteCount: Int = 0,
        @SerializedName("poster_path")
        var posterPath: String? = null,
        @SerializedName("video")
        var video: Boolean = false,
        @SerializedName("popularity")
        var popularity: Float = 0f) : Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UpcomingMoviesEntity> = object : Parcelable.Creator<UpcomingMoviesEntity> {
            override fun createFromParcel(source: Parcel): UpcomingMoviesEntity = UpcomingMoviesEntity(source)
            override fun newArray(size: Int): Array<UpcomingMoviesEntity?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readFloat(),
            source.readString(),
            1 == source.readInt(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString(),
            1 == source.readInt(),
            source.readFloat()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(index)
        writeFloat(voteAverage)
        writeString(backdropPath)
        writeInt((if (adult) 1 else 0))
        writeInt(id)
        writeString(title)
        writeString(overview)
        writeString(originalLanguage)
        writeString(releaseDate)
        writeString(originalTitle)
        writeInt(voteCount)
        writeString(posterPath)
        writeInt((if (video) 1 else 0))
        writeFloat(popularity)
    }
}