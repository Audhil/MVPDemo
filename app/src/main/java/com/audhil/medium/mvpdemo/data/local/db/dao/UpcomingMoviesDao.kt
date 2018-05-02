package com.audhil.medium.mvpdemo.data.local.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.audhil.medium.mvpdemo.data.local.db.AppDBNames
import com.audhil.medium.mvpdemo.data.local.db.dao.base.BaseDao
import com.audhil.medium.mvpdemo.data.model.db.UpcomingMoviesEntity

/*
 * Created by mohammed-2284 on 02/05/18.
 */

@Dao
abstract class UpcomingMoviesDao : BaseDao() {
    @Query("select * from " + AppDBNames.UPCOMING_MOVIES_TABLE_NAME)
    abstract fun getMovies(): LiveData<MutableList<UpcomingMoviesEntity?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(movie: UpcomingMoviesEntity)

    @Query("DELETE FROM " + AppDBNames.UPCOMING_MOVIES_TABLE_NAME)
    abstract override fun deleteTable()
}