package com.audhil.medium.mvpdemo.data.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.audhil.medium.mvpdemo.data.local.db.AppDBNames.DB_NAME
import com.audhil.medium.mvpdemo.data.local.db.dao.MovieDao
import com.audhil.medium.mvpdemo.data.local.db.dao.PopularMoviesDao
import com.audhil.medium.mvpdemo.data.local.db.dao.UpcomingMoviesDao
import com.audhil.medium.mvpdemo.data.model.db.MoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.PopularMoviesEntity
import com.audhil.medium.mvpdemo.data.model.db.UpcomingMoviesEntity

/*
 * Created by mohammed-2284 on 01/05/18.
 */

@Database(
        entities = [(MoviesEntity::class),
            (UpcomingMoviesEntity::class),
            (PopularMoviesEntity::class)],
        version = 1,
        exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        const val dbName = DB_NAME
    }

    abstract fun moviesDao(): MovieDao

    abstract fun popularDao(): PopularMoviesDao

    abstract fun upcomingDao(): UpcomingMoviesDao
}

//  DB & TableNames
object AppDBNames {
    const val DB_NAME = "AppDataBase.db"

    const val TOP_RATED_MOVIES_TABLE_NAME = "TopRatedMoviesTable"
    const val POPULAR_MOVIES_TABLE_NAME = "PopularMoviesTable"
    const val UPCOMING_MOVIES_TABLE_NAME = "UpcomingMoviesTable"
}