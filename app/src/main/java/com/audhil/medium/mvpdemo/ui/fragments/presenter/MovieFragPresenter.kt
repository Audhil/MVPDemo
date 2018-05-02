package com.audhil.medium.mvpdemo.ui.fragments.presenter

import com.audhil.medium.mvpdemo.AppExecutors
import com.audhil.medium.mvpdemo.data.local.db.dao.MovieDao
import com.audhil.medium.mvpdemo.data.local.db.dao.PopularMoviesDao
import com.audhil.medium.mvpdemo.data.local.db.dao.UpcomingMoviesDao
import com.audhil.medium.mvpdemo.data.model.db.MoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.PopularMoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.UpcomingMoviesEntity
import com.audhil.medium.mvpdemo.data.model.repomappers.MovieRepoMapper
import com.audhil.medium.mvpdemo.data.remote.MovieDBAppAPIEndPoints
import com.audhil.medium.mvpdemo.data.remote.MovieDBAppAPIs
import com.audhil.medium.mvpdemo.rx.IRxListeners
import com.audhil.medium.mvpdemo.rx.makeSingleRxConnection
import com.audhil.medium.mvpdemo.ui.fragments.contract.IFragContract
import com.audhil.medium.mvpdemo.util.ConstantsUtil
import javax.inject.Inject

/*
 * Created by mohammed-2284 on 02/05/18.
 */

class MovieFragPresenter
@Inject
constructor(private val view: IFragContract.IFragView) : IFragContract.IFragPresenter, IRxListeners<Any> {

    override fun onUnknownHostException(t: Throwable?, tag: String) {
        view.exception(t?.message!!)
    }

    override fun onIllegalArgumentException(t: Throwable?, tag: String) {
        view.exception(t?.message!!)
    }

    override fun onUnKnownException(t: Throwable?, tag: String) {
        view.exception(t?.message!!)
    }

    override fun onSocketTimeOutException(t: Throwable?, tag: String) {
        view.exception(t?.message!!)
    }

    override fun onComplete(tag: String) = Unit

    override fun onSuccess(obj: Any?, tag: String) {
        when (tag) {
            MovieDBAppAPIEndPoints.MOVIES_DB_API_TOP_RATED ->
                repoMapper.map(obj, MovieDBAppAPIEndPoints.MOVIES_DB_API_TOP_RATED)?.let {
                    (it as? MutableList<MoviesEntity>)?.let {
                        it.forEach {
                            appExecutors.diskIOThread().execute {
                                moviesDao.insert(it)
                            }
                        }
                    }
                    //  asking view to populate recycler view
                    view.loadRecyclerView()
                }

            MovieDBAppAPIEndPoints.MOVIES_DB_API_UPCOMING ->
                repoMapper.map(obj, MovieDBAppAPIEndPoints.MOVIES_DB_API_UPCOMING)?.let {
                    (it as? MutableList<UpcomingMoviesEntity>)?.let {
                        it.forEach {
                            appExecutors.diskIOThread().execute {
                                upcomingMoviesDao.insert(it)
                            }
                        }
                    }
                    //  asking view to populate recycler view
                    view.loadRecyclerView()
                }

            MovieDBAppAPIEndPoints.MOVIES_DB_API_POPULAR ->
                repoMapper.map(obj, MovieDBAppAPIEndPoints.MOVIES_DB_API_POPULAR)?.let {
                    (it as? MutableList<PopularMoviesEntity>)?.let {
                        it.forEach {
                            appExecutors.diskIOThread().execute {
                                popularMoviesDao.insert(it)
                            }
                        }
                    }
                    //  asking view to populate recycler view
                    view.loadRecyclerView()
                }
        }
    }

    @Inject
    lateinit var api: MovieDBAppAPIs

    @Inject
    lateinit var repoMapper: MovieRepoMapper

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var moviesDao: MovieDao

    @Inject
    lateinit var upcomingMoviesDao: UpcomingMoviesDao

    @Inject
    lateinit var popularMoviesDao: PopularMoviesDao

    //  page index
    private var page = 1

    //  fetch movies from server
    override fun fetchMovies(clickedPos: Int?) {
        when (clickedPos) {
            ConstantsUtil.POPULAR_MOVIES ->
                api.getPopularMovies(
                        apiKey = ConstantsUtil.MOVIES_DB_API_KEY,
                        page = page.toString()
                ).makeSingleRxConnection(this, MovieDBAppAPIEndPoints.MOVIES_DB_API_POPULAR)

            ConstantsUtil.TOP_RATED_MOVIES ->
                api.getTopRatedMovies(
                        apiKey = ConstantsUtil.MOVIES_DB_API_KEY,
                        page = page.toString()
                ).makeSingleRxConnection(this, MovieDBAppAPIEndPoints.MOVIES_DB_API_TOP_RATED)

            ConstantsUtil.UPCOMING_MOVIES ->
                api.getUpcomingMovies(
                        apiKey = ConstantsUtil.MOVIES_DB_API_KEY,
                        page = page.toString()
                ).makeSingleRxConnection(this, MovieDBAppAPIEndPoints.MOVIES_DB_API_UPCOMING)
        }
        page += 1
    }

    //  getting movies from db
    fun getTopRatedMoviesFromDB() = moviesDao.getMovies()

    fun deleteTopRatedMoviesFromDB() {
        appExecutors.diskIOThread().execute {
            moviesDao.deleteTable()
        }
    }

    //  returns live data
    fun getUpcomingMoviesFromDB() = upcomingMoviesDao.getMovies()

    fun deleteUpcomingMoviesFromDB() {
        appExecutors.diskIOThread().execute {
            upcomingMoviesDao.deleteTable()
        }
    }

    //  returns live data
    fun getPopularMoviesFromDB() = popularMoviesDao.getMovies()

    fun deletePopularMoviesFromDB() {
        appExecutors.diskIOThread().execute {
            popularMoviesDao.deleteTable()
        }
    }
}