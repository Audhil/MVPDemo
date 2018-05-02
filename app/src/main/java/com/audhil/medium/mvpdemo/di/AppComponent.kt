package com.audhil.medium.mvpdemo.di

import com.audhil.medium.mvpdemo.ui.fragments.MovieFragment
import com.audhil.medium.mvpdemo.ui.scrollingscreen.ScrollingActivity
import com.audhil.medium.mvpdemo.ui.splash.view.SplashActivity
import dagger.Component
import javax.inject.Singleton

/*
 * Created by mohammed-2284 on 01/05/18.
 */

@Singleton
@Component(modules = [
    (SplashPresenterModule::class),
    (APIModule::class),
    (ApplicationModule::class),
    (RepoMapperModule::class),
    (DataBaseModule::class)
])
interface AppActivityComponent {
    fun inject(splashActivity: SplashActivity)
}

@Singleton
@Component(modules = [
    (FragPresenterModule::class),
    (APIModule::class),
    (ApplicationModule::class),
    (RepoMapperModule::class),
    (DataBaseModule::class)
])
interface AppFragComponent {
    fun inject(frag: MovieFragment)
}

@Singleton
@Component(modules = [
    (DetailPresenterModule::class),
    (APIModule::class),
    (ApplicationModule::class),
    (RepoMapperModule::class),
    (DataBaseModule::class)
])
interface DetailActivityComponent {
    fun inject(scrollingActivity: ScrollingActivity)
}