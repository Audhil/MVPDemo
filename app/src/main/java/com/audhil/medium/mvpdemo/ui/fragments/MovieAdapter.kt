package com.audhil.medium.mvpdemo.ui.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.audhil.medium.mvpdemo.data.model.db.MoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.PopularMoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.UpcomingMoviesEntity
import com.audhil.medium.mvpdemo.databinding.MovieCardBinding
import com.audhil.medium.mvpdemo.ui.fragments.viewholders.LoadingItemViewHolder
import com.audhil.medium.mvpdemo.ui.fragments.viewholders.MoviesEmptyViewHolder
import com.audhil.medium.mvpdemo.util.BiCallBack
import com.audhil.medium.mvpdemo.util.CallBack
import com.audhil.medium.mvpdemo.util.ConstantsUtil

/*
 * Created by mohammed-2284 on 02/05/18.
 */

//  reusing same adapter
class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //  data list
    var topRatedMoviesList: MutableList<MoviesEntity?>? = mutableListOf()
    var upcomingMoviesList: MutableList<UpcomingMoviesEntity?>? = mutableListOf()
    var popularMoviesList: MutableList<PopularMoviesEntity?>? = mutableListOf()

    //  movie category
    private var movieCategory: Int? = 0

    //  item click listener
    var clickListener: BiCallBack<Int, Any?>? = null

    override fun getItemCount(): Int =
            when (movieCategory) {
                ConstantsUtil.TOP_RATED_MOVIES -> if (topRatedMoviesList?.size!! == 0)
                    0
                else
                    topRatedMoviesList?.size!!

                ConstantsUtil.UPCOMING_MOVIES -> if (upcomingMoviesList?.size!! == 0)
                    0
                else
                    upcomingMoviesList?.size!!

                ConstantsUtil.POPULAR_MOVIES -> if (popularMoviesList?.size!! == 0)
                    0
                else
                    popularMoviesList?.size!!

                else -> 1
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                topRatedMoviesList?.get(position)?.let {
                    holder.bindTopRatedMovie(position, it, clickListener)
                }
                upcomingMoviesList?.get(position)?.let {
                    holder.bindUpcomingMovie(position, it, clickListener)
                }
                popularMoviesList?.get(position)?.let {
                    holder.bindPopularMovie(position, it, clickListener)
                }
            }


            is MoviesEmptyViewHolder ->
                holder.bindTo(position)

            else ->
                Unit    //  do nothing
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                ConstantsUtil.VIEW_TYPE_LOADING ->
                    //  loading_item.xml
                    LoadingItemViewHolder(parent)

                ConstantsUtil.VIEW_TYPE_EMPTY -> {
                    //  empty_view_item.xml
                    MoviesEmptyViewHolder(parent)
                }

                else ->
                    //  movie_card.xml
                    MovieViewHolder(MovieCardBinding.inflate(LayoutInflater.from(parent.context)))
            }

    override fun getItemViewType(position: Int): Int {
        when (movieCategory) {
            ConstantsUtil.TOP_RATED_MOVIES -> {
                return when {
                    topRatedMoviesList?.get(position) == null -> ConstantsUtil.VIEW_TYPE_LOADING
                    topRatedMoviesList?.get(position) != null -> ConstantsUtil.VIEW_TYPE_ITEM
                    else -> ConstantsUtil.VIEW_TYPE_EMPTY
                }
            }

            ConstantsUtil.UPCOMING_MOVIES -> {
                return when {
                    upcomingMoviesList?.get(position) == null -> ConstantsUtil.VIEW_TYPE_LOADING
                    upcomingMoviesList?.get(position) != null -> ConstantsUtil.VIEW_TYPE_ITEM
                    else -> ConstantsUtil.VIEW_TYPE_EMPTY
                }
            }

            ConstantsUtil.POPULAR_MOVIES -> {
                return when {
                    popularMoviesList?.get(position) == null -> ConstantsUtil.VIEW_TYPE_LOADING
                    popularMoviesList?.get(position) != null -> ConstantsUtil.VIEW_TYPE_ITEM
                    else -> ConstantsUtil.VIEW_TYPE_EMPTY
                }
            }
        }
        return ConstantsUtil.VIEW_TYPE_LOADING
    }

    //  add PopularMovies
    fun addPopularMovies(posts: MutableList<PopularMoviesEntity?>?, chosenType: Int?, isToolbarSearchViewOpen: Boolean = false) {
        movieCategory = chosenType
        this.popularMoviesList?.clear()
        posts?.let {
            this.popularMoviesList?.addAll(it)
        }
        this.topRatedMoviesList = null
        this.upcomingMoviesList = null
        if (!isToolbarSearchViewOpen)
            this.popularMoviesList?.add(null)
        notifyDataSetChanged()
    }

    //  add UpcomingMovies
    fun addUpcomingMovies(posts: MutableList<UpcomingMoviesEntity?>?, chosenType: Int?, isToolbarSearchViewOpen: Boolean = false) {
        movieCategory = chosenType
        this.upcomingMoviesList?.clear()
        posts?.let {
            this.upcomingMoviesList?.addAll(it)
        }
        this.topRatedMoviesList = null
        this.popularMoviesList = null
        if (!isToolbarSearchViewOpen)
            this.upcomingMoviesList?.add(null)
        notifyDataSetChanged()
    }

    //  add TopRated Movies
    fun addTopRatedMovies(posts: MutableList<MoviesEntity?>?, chosenType: Int?, isToolbarSearchViewOpen: Boolean = false) {
        movieCategory = chosenType
        this.topRatedMoviesList?.clear()

        posts?.let {
            this.topRatedMoviesList?.addAll(it)
        }

        this.upcomingMoviesList = null
        this.popularMoviesList = null
        if (!isToolbarSearchViewOpen)
            this.topRatedMoviesList?.add(null)
        notifyDataSetChanged()
    }
}