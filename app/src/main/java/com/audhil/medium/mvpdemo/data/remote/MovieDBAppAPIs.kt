package com.audhil.medium.mvpdemo.data.remote

import com.audhil.medium.mvpdemo.data.model.api.moviesdb.MoviesResponse
import com.audhil.medium.mvpdemo.data.model.api.moviesdb.PopularMoviesResponse
import com.audhil.medium.mvpdemo.data.model.api.moviesdb.UpcomingMoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * Created by mohammed-2284 on 02/05/18.
 */

interface MovieDBAppAPIs {

    @GET(MovieDBAppAPIEndPoints.MOVIES_DB_API_TOP_RATED)
    fun getTopRatedMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String = "en-US",
            @Query("page") page: String,
            @Query("limit") limit: String = "20"    //  limit is optional parameter
    ): Single<MoviesResponse>

    @GET(MovieDBAppAPIEndPoints.MOVIES_DB_API_POPULAR)
    fun getPopularMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String = "en-US",
            @Query("page") page: String,
            @Query("limit") limit: String = "20"    //  limit is optional parameter
    ): Single<PopularMoviesResponse>

    @GET(MovieDBAppAPIEndPoints.MOVIES_DB_API_UPCOMING)
    fun getUpcomingMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String = "en-US",
            @Query("page") page: String,
            @Query("limit") limit: String = "20"    //  limit is optional parameter
    ): Single<UpcomingMoviesResponse>
}