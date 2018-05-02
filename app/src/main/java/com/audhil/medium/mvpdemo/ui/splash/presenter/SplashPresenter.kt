package com.audhil.medium.mvpdemo.ui.splash.presenter

import com.audhil.medium.mvpdemo.ui.splash.contract.ISplashContract
import javax.inject.Inject

/*
 * Created by mohammed-2284 on 01/05/18.
 */

class SplashPresenter
@Inject
constructor(private val view: ISplashContract.ISplashView) : ISplashContract.ISplashPresenter {
    override fun afterSomeDelay() = view.launchDashBoardActivity()
}