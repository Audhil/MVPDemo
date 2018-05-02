package com.audhil.medium.mvpdemo.ui.splash.view

import android.os.Bundle
import com.audhil.medium.mvpdemo.AppDelegate
import com.audhil.medium.mvpdemo.R
import com.audhil.medium.mvpdemo.di.ApplicationModule
import com.audhil.medium.mvpdemo.di.DaggerAppActivityComponent
import com.audhil.medium.mvpdemo.di.SplashPresenterModule
import com.audhil.medium.mvpdemo.ui.splash.contract.ISplashContract
import com.audhil.medium.mvpdemo.ui.base.BaseActivity
import com.audhil.medium.mvpdemo.ui.dashboard.DashBoardActivity
import com.audhil.medium.mvpdemo.ui.splash.presenter.SplashPresenter
import kotlinx.android.synthetic.main.splash_activity.*
import javax.inject.Inject

/*
 * Created by mohammed-2284 on 01/05/18.
 */

class SplashActivity : BaseActivity(), ISplashContract.ISplashView {

    @Inject
    lateinit var splashPresenter: SplashPresenter

    override fun getLayoutId(): Int = R.layout.splash_activity

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppActivityComponent.builder()
                .applicationModule(ApplicationModule(AppDelegate.INSTANCE))
                .splashPresenterModule(SplashPresenterModule(this))
                .build()
                .inject(this)

        //  making a fake delay
        txt_view.postDelayed({
            splashPresenter.afterSomeDelay()
        }, 1000)
    }

    //  order from Presenter
    override fun launchDashBoardActivity() {
        startActivity(DashBoardActivity.newIntent())
        finish()    //  finishing this activity
    }
}