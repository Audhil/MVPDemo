package com.audhil.medium.mvpdemo.data.local.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.audhil.medium.mvpdemo.data.local.db.AppDBNames
import com.audhil.medium.mvpdemo.data.local.db.dao.base.BaseDao
import com.audhil.medium.mvpdemo.data.model.db.PopularMoviesEntity

/*
 * Created by mohammed-2284 on 02/05/18.
 */

@Dao
abstract class PopularMoviesDao : BaseDao() {
    @Query("select * from " + AppDBNames.POPULAR_MOVIES_TABLE_NAME)
    abstract fun getMovies(): LiveData<MutableList<PopularMoviesEntity?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(movie: PopularMoviesEntity)

    @Query("DELETE FROM " + AppDBNames.POPULAR_MOVIES_TABLE_NAME)
    abstract override fun deleteTable()
}