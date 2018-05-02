package com.audhil.medium.mvpdemo.ui.scrollingscreen

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.audhil.medium.mvpdemo.AppDelegate
import com.audhil.medium.mvpdemo.BR
import com.audhil.medium.mvpdemo.R
import com.audhil.medium.mvpdemo.data.model.db.MoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.PopularMoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.UpcomingMoviesEntity
import com.audhil.medium.mvpdemo.data.remote.MovieDBAppAPIEndPoints
import com.audhil.medium.mvpdemo.databinding.ScrollingActivityBinding
import com.audhil.medium.mvpdemo.di.DaggerDetailActivityComponent
import com.audhil.medium.mvpdemo.di.DetailPresenterModule
import com.audhil.medium.mvpdemo.glide.GlideApp
import com.audhil.medium.mvpdemo.util.ConstantsUtil
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject

/*
 * Created by mohammed-2284 on 02/05/18.
 */

class ScrollingActivity : AppCompatActivity(), IDetailContract.IDetailView {

    companion object {
        fun newIntent(parcelable: Parcelable? = null, clickedPos: Int = 0): Intent =
                Intent(AppDelegate.INSTANCE, ScrollingActivity::class.java).apply {
                    parcelable?.let {
                        val bundle = Bundle()
                        bundle.putInt(ConstantsUtil.PARCELABLE_INT, clickedPos)
                        bundle.putParcelable(ConstantsUtil.PARCELABLE, it)
                        putExtras(bundle)
                    }
                }
    }

    @Inject
    lateinit var presenter: DetailScreenPresenter

    //  binding
    private var binding: ScrollingActivityBinding? = null

    //  back drop path
    private var backDropPath = ConstantsUtil.EMPTY
    private var toolBarTitle = ConstantsUtil.EMPTY

    //  variables
    private var upComingMovie: UpcomingMoviesEntity? = null
    private var popularMovie: PopularMoviesEntity? = null
    private var topRatedMovie: MoviesEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDI()
        intent?.extras?.getParcelable<Parcelable>(ConstantsUtil.PARCELABLE)?.let {
            when (it) {
                is UpcomingMoviesEntity -> upComingMovie = it
                is PopularMoviesEntity -> popularMovie = it
                is MoviesEntity -> topRatedMovie = it
            }
        }
        binding = DataBindingUtil.setContentView<ScrollingActivityBinding>(this, R.layout.scrolling_activity)
        setSupportActionBar(binding?.toolbar)
        upComingMovie?.let {
            binding?.scrollContentUpcomingLayout?.setVariable(BR.upcoming_movie, it)
            binding?.scrollContentUpcomingLayout?.executePendingBindings()
            binding?.scrollContentUpcomingLayout?.root?.visibility = View.VISIBLE
            backDropPath = it.backdropPath!!
            toolBarTitle = it.title!!
        }
        popularMovie?.let {
            binding?.scrollContentPopularLayout?.setVariable(BR.popular_movie, it)
            binding?.scrollContentPopularLayout?.executePendingBindings()
            binding?.scrollContentPopularLayout?.root?.visibility = View.VISIBLE
            backDropPath = it.backdropPath!!
            toolBarTitle = it.title!!
        }
        topRatedMovie?.let {
            binding?.scrollContentLayout?.setVariable(BR.top_rated_movie, it)
            binding?.scrollContentLayout?.executePendingBindings()
            binding?.scrollContentLayout?.root?.visibility = View.VISIBLE
            backDropPath = it.backdropPath!!
            toolBarTitle = it.title!!
        }

        //  tool bar title
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = toolBarTitle
        }

        //  load image
        GlideApp.with(applicationContext)
                .load(MovieDBAppAPIEndPoints.IMAGE_END_POINT_W_500 + backDropPath)
                .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding?.expandedImage!!)
    }

    //  performing DI
    private fun performDI() =
            DaggerDetailActivityComponent
                    .builder()
                    .detailPresenterModule(DetailPresenterModule(this))
                    .build()
                    .inject(this)

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home ->
                finish()
        }
        return super.onOptionsItemSelected(item)
    }
}