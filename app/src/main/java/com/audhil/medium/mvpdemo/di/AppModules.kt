package com.audhil.medium.mvpdemo.di

import android.arch.persistence.room.Room
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.audhil.medium.mvpdemo.AppDelegate
import com.audhil.medium.mvpdemo.data.local.db.AppDataBase
import com.audhil.medium.mvpdemo.data.local.db.dao.MovieDao
import com.audhil.medium.mvpdemo.data.local.db.dao.PopularMoviesDao
import com.audhil.medium.mvpdemo.data.local.db.dao.UpcomingMoviesDao
import com.audhil.medium.mvpdemo.data.model.repomappers.MovieRepoMapper
import com.audhil.medium.mvpdemo.data.remote.MovieDBAppAPIEndPoints
import com.audhil.medium.mvpdemo.data.remote.MovieDBAppAPIs
import com.audhil.medium.mvpdemo.ui.fragments.contract.IFragContract
import com.audhil.medium.mvpdemo.ui.scrollingscreen.IDetailContract
import com.audhil.medium.mvpdemo.ui.splash.contract.ISplashContract
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/*
 * Created by mohammed-2284 on 02/05/18.
 */

//  Presenters modules
@Module
class SplashPresenterModule(private val view: ISplashContract.ISplashView) {

    @Singleton
    @Provides
    internal fun giveSplashContractView(): ISplashContract.ISplashView = view
}

@Module
class FragPresenterModule(private val view: IFragContract.IFragView) {

    @Singleton
    @Provides
    internal fun giveFragContractView(): IFragContract.IFragView = view
}

@Module
class DetailPresenterModule(private val view: IDetailContract.IDetailView) {

    @Singleton
    @Provides
    internal fun giveDetailContractView(): IDetailContract.IDetailView = view
}

//  application module
@Module
class ApplicationModule(private val application: AppDelegate) {
    @Provides
    @Singleton
    internal fun giveContext(): Context {
        return this.application
    }

    @Provides
    @Singleton
    internal fun givePackageManager(): PackageManager {
        return application.packageManager
    }
}

//  API module
@Module(includes = [(ApplicationModule::class)])
class APIModule {
    val cacheSize: Long = 10 * 1024 * 1024
    val cacheTimeSec = 30

    //  interceptor - 1
    private val cacheInterceptor: Interceptor
        get() = Interceptor {
            val response = it.proceed(it.request())
            val cacheControl = CacheControl.Builder()
                    .maxAge(cacheTimeSec, TimeUnit.SECONDS)
                    .build()

            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
        }


    //  interceptor - 4
    @Provides
    @Singleton
    fun giveLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("MovieDBAppRetrofit", message) })
        interceptor.level = if (true)   //  make it false for production ready code
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    //  okHttpClient
    @Provides
    @Singleton
    fun giveOkHttpClient(
            context: Context,
            loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val cache = Cache(File(context.cacheDir, "http-cache"), cacheSize)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(cacheInterceptor)
                .addInterceptor(loggingInterceptor)
        httpClient.cache(cache)
        return httpClient.build()
    }

    //  retrofit instance
    @Provides
    @Singleton
    fun giveRetrofitAPIService(okHttpClient: OkHttpClient): MovieDBAppAPIs = Retrofit.Builder()
            .baseUrl(MovieDBAppAPIEndPoints.MOVIES_DB_API_END_POINT)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(MovieDBAppAPIs::class.java)

    //  gson instance
    @Provides
    @Singleton
    fun giveGSONInstance(): Gson = Gson()

    @Provides
    @Singleton
    fun giveCompositeDisposableInstance(): CompositeDisposable = CompositeDisposable()
}


//  Database module
@Module
class DataBaseModule {
    @Provides
    @Singleton
    fun giveUseInMemoryDB(): Boolean = false    /*  make "useInMemory = true" in TestCases */

    @Provides
    @Singleton
    fun giveMovieDBAppDataBase(context: Context, useInMemory: Boolean): AppDataBase {
        val databaseBuilder = if (useInMemory) {
            Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
        } else {
            Room.databaseBuilder(context, AppDataBase::class.java, AppDataBase.dbName)
        }
        return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun giveMoviesDao(db: AppDataBase): MovieDao = db.moviesDao()

    @Provides
    @Singleton
    fun giveUpcomingMoviesDao(db: AppDataBase): UpcomingMoviesDao = db.upcomingDao()

    @Provides
    @Singleton
    fun givePopularMoviesDao(db: AppDataBase): PopularMoviesDao = db.popularDao()
}

//  repo mapper module
@Module
class RepoMapperModule {

    @Provides
    @Singleton
    fun giveRepoMapper(): MovieRepoMapper = MovieRepoMapper()
}