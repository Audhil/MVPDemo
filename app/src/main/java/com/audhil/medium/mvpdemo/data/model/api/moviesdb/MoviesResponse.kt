package com.audhil.medium.mvpdemo.data.model.api.moviesdb

import com.google.gson.annotations.SerializedName
import com.audhil.medium.mvpdemo.data.model.db.MoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.PopularMoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.UpcomingMoviesEntity
import java.util.*

/*
 * Created by mohammed-2284 on 01/05/18.
 */

data class MoviesResponse(
        @SerializedName("results")
        var results: Array<MoviesEntity>? = null,
        @SerializedName("page")
        var page: Int,
        @SerializedName("total_pages")
        var totalPages: Int,
        @SerializedName("total_results")
        var totalResults: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MoviesResponse

        if (!Arrays.equals(results, other.results)) return false
        if (page != other.page) return false
        if (totalPages != other.totalPages) return false
        if (totalResults != other.totalResults) return false

        return true
    }

    override fun hashCode(): Int {
        var result = results?.let { Arrays.hashCode(it) } ?: 0
        result = 31 * result + page
        result = 31 * result + totalPages
        result = 31 * result + totalResults
        return result
    }
}

//  PopularMovies response
data class PopularMoviesResponse(
        @SerializedName("results")
        var popularResults: Array<PopularMoviesEntity>? = null,
        @SerializedName("page")
        var page: Int,
        @SerializedName("total_pages")
        var totalPages: Int,
        @SerializedName("total_results")
        var totalResults: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PopularMoviesResponse

        if (!Arrays.equals(popularResults, other.popularResults)) return false
        if (page != other.page) return false
        if (totalPages != other.totalPages) return false
        if (totalResults != other.totalResults) return false

        return true
    }

    override fun hashCode(): Int {
        var result = popularResults?.let { Arrays.hashCode(it) } ?: 0
        result = 31 * result + page
        result = 31 * result + totalPages
        result = 31 * result + totalResults
        return result
    }
}

//  UpcomingMovies response
data class UpcomingMoviesResponse(
        @SerializedName("results")
        var upComingResults: Array<UpcomingMoviesEntity>? = null,
        @SerializedName("page")
        var page: Int,
        @SerializedName("total_pages")
        var totalPages: Int,
        @SerializedName("total_results")
        var totalResults: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UpcomingMoviesResponse

        if (!Arrays.equals(upComingResults, other.upComingResults)) return false
        if (page != other.page) return false
        if (totalPages != other.totalPages) return false
        if (totalResults != other.totalResults) return false

        return true
    }

    override fun hashCode(): Int {
        var result = upComingResults?.let { Arrays.hashCode(it) } ?: 0
        result = 31 * result + page
        result = 31 * result + totalPages
        result = 31 * result + totalResults
        return result
    }
}