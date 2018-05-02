package com.audhil.medium.mvpdemo.data.model.repomappers

import com.audhil.medium.mvpdemo.data.model.api.moviesdb.MoviesResponse
import com.audhil.medium.mvpdemo.data.model.api.moviesdb.PopularMoviesResponse
import com.audhil.medium.mvpdemo.data.model.api.moviesdb.UpcomingMoviesResponse
import com.audhil.medium.mvpdemo.data.model.db.MoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.PopularMoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.UpcomingMoviesEntity
import com.audhil.medium.mvpdemo.data.remote.MovieDBAppAPIEndPoints
import com.google.gson.Gson

/*
 * Created by mohammed-2284 on 12/04/18.
 */

class MovieRepoMapper : Mapper<Any?, Any?> {

    private val listOfMovies: MutableList<MoviesEntity> = mutableListOf()
    private val upcomingListOfMovies: MutableList<UpcomingMoviesEntity> = mutableListOf()
    private val popularListOfMovies: MutableList<PopularMoviesEntity> = mutableListOf()

    override fun map(input: Any?, tag: String): Any? {
        when (tag) {
        //  UPCOMING
            MovieDBAppAPIEndPoints.MOVIES_DB_API_UPCOMING -> {
                (input as? UpcomingMoviesResponse)?.upComingResults?.let {
                    upcomingListOfMovies.clear()
                    upcomingListOfMovies.addAll(it)
                    return upcomingListOfMovies
                }
            }
        //  POPULAR
            MovieDBAppAPIEndPoints.MOVIES_DB_API_POPULAR -> {
                (input as? PopularMoviesResponse)?.popularResults?.let {
                    popularListOfMovies.clear()
                    popularListOfMovies.addAll(it)
                    return popularListOfMovies
                }
            }

        //  TOP RATED
            MovieDBAppAPIEndPoints.MOVIES_DB_API_TOP_RATED -> {
                (input as? MoviesResponse)?.results?.let {
                    listOfMovies.clear()
                    listOfMovies.addAll(it)
                    return listOfMovies
                }
            }
        }
        return null
    }
}