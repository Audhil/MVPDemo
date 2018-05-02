package com.audhil.medium.mvpdemo.ui.fragments

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import com.audhil.medium.mvpdemo.AppDelegate
import com.audhil.medium.mvpdemo.R
import com.audhil.medium.mvpdemo.data.model.db.MoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.PopularMoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.UpcomingMoviesEntity
import com.audhil.medium.mvpdemo.di.ApplicationModule
import com.audhil.medium.mvpdemo.di.DaggerAppFragComponent
import com.audhil.medium.mvpdemo.di.FragPresenterModule
import com.audhil.medium.mvpdemo.ui.base.BaseFragment
import com.audhil.medium.mvpdemo.ui.customlayoutmanager.CenterZoomLayoutManager
import com.audhil.medium.mvpdemo.ui.dashboard.DashBoardActivity
import com.audhil.medium.mvpdemo.ui.fragments.contract.IFragContract
import com.audhil.medium.mvpdemo.ui.fragments.presenter.MovieFragPresenter
import com.audhil.medium.mvpdemo.ui.scrollingscreen.ScrollingActivity
import com.audhil.medium.mvpdemo.util.ConstantsUtil
import com.audhil.medium.mvpdemo.util.showVLog
import kotlinx.android.synthetic.main.movie_frag.*
import javax.inject.Inject

/*
 * Created by mohammed-2284 on 02/05/18.
 */

//  reusing same fragment
class MovieFragment : BaseFragment(), IFragContract.IFragView {

    override fun exception(exceptionName: String) {
        showVLog("EXCEPTION happened exceptionName : $exceptionName")
    }

    override fun getLayoutId(): Int = R.layout.movie_frag

    companion object {
        fun newInstance(pos: Int = 0): MovieFragment =
                MovieFragment().apply {
                    val bundle = Bundle()
                    bundle.putInt(ConstantsUtil.CLICKED_POS, pos)
                    arguments = bundle
                }
    }

    //  clicked pos
    private val clickedPos by lazy {
        arguments?.getInt(ConstantsUtil.CLICKED_POS)
    }

    //  recyclerview adapter
    private val recyclerAdapter by lazy {
        MovieAdapter()
    }

    //  scroll listener helpers
    var isLoading = false
    var visibleThreshold = 5
    var totalItemCount = 0
    var lastVisibleItem = 0
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var presenter: MovieFragPresenter

    //  movies back up
    private var topRatedMoviesListBackup: MutableList<MoviesEntity?>? = mutableListOf()
    private var popularMoviesListBackup: MutableList<PopularMoviesEntity?>? = mutableListOf()
    private var upcomingMoviesListBackup: MutableList<UpcomingMoviesEntity?>? = mutableListOf()
    private var isToolbarSearchViewOpen = false

    //  onAttach
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //  search query from activity
        if (context is DashBoardActivity) {
            context.fragCommCallback = { query, isEditTextFocused ->
                isToolbarSearchViewOpen = isEditTextFocused
                if (query.isEmpty()) {
                    when (clickedPos) {
                        ConstantsUtil.POPULAR_MOVIES -> {
                            recyclerAdapter.addPopularMovies(
                                    popularMoviesListBackup,
                                    chosenType = clickedPos,
                                    isToolbarSearchViewOpen = isToolbarSearchViewOpen
                            )
                        }
                        ConstantsUtil.TOP_RATED_MOVIES -> {
                            recyclerAdapter.addTopRatedMovies(
                                    topRatedMoviesListBackup,
                                    chosenType = clickedPos,
                                    isToolbarSearchViewOpen = isToolbarSearchViewOpen
                            )
                        }
                        ConstantsUtil.UPCOMING_MOVIES -> {
                            recyclerAdapter.addUpcomingMovies(
                                    upcomingMoviesListBackup,
                                    chosenType = clickedPos,
                                    isToolbarSearchViewOpen = isToolbarSearchViewOpen
                            )
                        }
                    }
                } else {
                    when (clickedPos) {
                        ConstantsUtil.POPULAR_MOVIES -> {
                            recyclerAdapter.addPopularMovies(
                                    posts = popularMoviesListBackup?.asSequence()
                                            ?.filter {
                                                //  filtering with movie title or with release year (1995-10-20)
                                                it?.title?.contains(query, true)!! || it.releaseDate?.substring(0, 4)?.contains(query, true)!!
                                            }
                                            ?.toMutableList(),
                                    chosenType = clickedPos,
                                    isToolbarSearchViewOpen = isToolbarSearchViewOpen
                            )
                        }
                        ConstantsUtil.TOP_RATED_MOVIES -> {
                            recyclerAdapter.addTopRatedMovies(
                                    posts = topRatedMoviesListBackup?.asSequence()
                                            ?.filter {
                                                //  filtering with movie title or with release year (1995-10-20)
                                                it?.title?.contains(query, true)!! || it.releaseDate?.substring(0, 4)?.contains(query, true)!!
                                            }
                                            ?.toMutableList(),
                                    chosenType = clickedPos,
                                    isToolbarSearchViewOpen = isToolbarSearchViewOpen
                            )
                        }
                        ConstantsUtil.UPCOMING_MOVIES -> {
                            recyclerAdapter.addUpcomingMovies(
                                    posts = upcomingMoviesListBackup?.asSequence()
                                            ?.filter {
                                                //  filtering with movie title or with release year (1995-10-20)
                                                it?.title?.contains(query, true)!! || it.releaseDate?.substring(0, 4)?.contains(query, true)!!

                                            }
                                            ?.toMutableList(),
                                    chosenType = clickedPos,
                                    isToolbarSearchViewOpen = isToolbarSearchViewOpen
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerAppFragComponent
                .builder()
                .applicationModule(ApplicationModule(AppDelegate.INSTANCE))
                .fragPresenterModule(FragPresenterModule(this))
                .build()
                .inject(this)

        recycler_view.apply {
            layoutManager = CenterZoomLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recyclerAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    linearLayoutManager = LinearLayoutManager::class.java.cast(recyclerView!!.layoutManager)
                    totalItemCount = linearLayoutManager.itemCount
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        onLoadMore()
                        isLoading = true;
                    }
                }
            })
        }
        //  attaching snap helper - it'll bring item to center
        LinearSnapHelper().attachToRecyclerView(recycler_view)

        //  initialize logic - add empty data to adapter
        when (clickedPos) {
            ConstantsUtil.POPULAR_MOVIES -> {
                presenter.deletePopularMoviesFromDB()
                recyclerAdapter.addPopularMovies(popularMoviesListBackup, clickedPos)
            }
            ConstantsUtil.TOP_RATED_MOVIES -> {
                presenter.deleteTopRatedMoviesFromDB()
                recyclerAdapter.addTopRatedMovies(topRatedMoviesListBackup, clickedPos)

            }
            ConstantsUtil.UPCOMING_MOVIES -> {
                presenter.deleteUpcomingMoviesFromDB()
                recyclerAdapter.addUpcomingMovies(upcomingMoviesListBackup, clickedPos)
            }
        }

        //  item click listener
        recyclerAdapter.clickListener = { pos, any ->
            startActivity(ScrollingActivity.newIntent(any as Parcelable, clickedPos as Int))
        }
    }

    //  load more data
    private fun onLoadMore() {
        showVLog("onLoadMore loading movies from server")
        if (!isToolbarSearchViewOpen) //  to prevent unnecessary api calls, when searching is in progress
            presenter.fetchMovies(clickedPos)
    }

    //  action from presenter
    override fun loadRecyclerView() {
        when (clickedPos) {
        //  popular movies from DB
            ConstantsUtil.POPULAR_MOVIES -> {
                presenter.getPopularMoviesFromDB().observe(this, Observer {
                    isLoading = false
                    recyclerAdapter.addPopularMovies(it, chosenType = clickedPos)
                    popularMoviesListBackup?.clear()
                    recyclerAdapter.popularMoviesList?.let {
                        it.forEach {
                            popularMoviesListBackup?.add(it)
                        }
                        //  removing last null item
                        if (popularMoviesListBackup?.contains(null)!!)
                            popularMoviesListBackup?.remove(null)
                    }
                })
            }

        //  top rated movies from DB
            ConstantsUtil.TOP_RATED_MOVIES -> {
                presenter.getTopRatedMoviesFromDB().observe(this, Observer {
                    isLoading = false
                    recyclerAdapter.addTopRatedMovies(it, clickedPos)
                    topRatedMoviesListBackup?.clear()
                    recyclerAdapter.topRatedMoviesList?.let {
                        it.forEach {
                            topRatedMoviesListBackup?.add(it)
                        }
                        //  removing last null item
                        if (topRatedMoviesListBackup?.contains(null)!!)
                            topRatedMoviesListBackup?.remove(null)
                    }
                })
            }

        //  upcoming movies from DB
            ConstantsUtil.UPCOMING_MOVIES -> {
                presenter.getUpcomingMoviesFromDB().observe(this, Observer {
                    isLoading = false
                    recyclerAdapter.addUpcomingMovies(it, clickedPos)
                    upcomingMoviesListBackup?.clear()
                    recyclerAdapter.upcomingMoviesList?.let {
                        it.forEach {
                            upcomingMoviesListBackup?.add(it)
                        }
                        //  removing last null item
                        if (upcomingMoviesListBackup?.contains(null)!!)
                            upcomingMoviesListBackup?.remove(null)
                    }
                })
            }
        }
    }
}