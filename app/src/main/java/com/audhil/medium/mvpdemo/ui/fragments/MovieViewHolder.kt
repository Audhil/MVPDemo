package com.audhil.medium.mvpdemo.ui.fragments

import android.support.v7.widget.RecyclerView
import com.audhil.medium.mvpdemo.R
import com.audhil.medium.mvpdemo.data.model.db.MoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.PopularMoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.UpcomingMoviesEntity
import com.audhil.medium.mvpdemo.data.remote.MovieDBAppAPIEndPoints
import com.audhil.medium.mvpdemo.databinding.MovieCardBinding
import com.audhil.medium.mvpdemo.glide.GlideApp
import com.audhil.medium.mvpdemo.util.BiCallBack
import com.audhil.medium.mvpdemo.util.CallBack
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/*
 * Created by mohammed-2284 on 02/05/18.
 */

//  movie_card.xml
class MovieViewHolder(
        val binding: MovieCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    //  top rated
    fun bindTopRatedMovie(position: Int, moviesEntity: MoviesEntity?, clickListener: BiCallBack<Int, Any?>?) {
        binding.movieName.text = moviesEntity?.title
        GlideApp.with(itemView.context)
                .load(MovieDBAppAPIEndPoints.IMAGE_END_POINT_W_500 +
                        moviesEntity?.posterPath)
                .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.moviePoster)
        binding.baseCardView.setOnClickListener {
            clickListener?.invoke(adapterPosition, moviesEntity)
        }
    }

    //  upcoming
    fun bindUpcomingMovie(position: Int, upcomingMoviesEntity: UpcomingMoviesEntity?, clickListener: BiCallBack<Int, Any?>?) {
        binding.movieName.text = upcomingMoviesEntity?.title
        GlideApp.with(itemView.context)
                .load(MovieDBAppAPIEndPoints.IMAGE_END_POINT_W_500 +
                        upcomingMoviesEntity?.posterPath)
                .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.moviePoster)
        binding.baseCardView.setOnClickListener {
            clickListener?.invoke(adapterPosition, upcomingMoviesEntity)
        }
    }

    //  popular
    fun bindPopularMovie(position: Int, popularMoviesEntity: PopularMoviesEntity?, clickListener: BiCallBack<Int, Any?>?) {
        binding.movieName.text = popularMoviesEntity?.title
        GlideApp.with(itemView.context)
                .load(MovieDBAppAPIEndPoints.IMAGE_END_POINT_W_500 +
                        popularMoviesEntity?.posterPath)
                .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.moviePoster)
        binding.baseCardView.setOnClickListener {
            clickListener?.invoke(adapterPosition, popularMoviesEntity)
        }
    }
}