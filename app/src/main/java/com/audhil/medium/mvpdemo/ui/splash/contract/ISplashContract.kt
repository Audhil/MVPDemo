package com.audhil.medium.mvpdemo.ui.splash.contract

import com.audhil.medium.mvpdemo.ui.base.IBaseView

/*
 * Created by mohammed-2284 on 01/05/18.
 */

interface ISplashContract {

    //  View
    interface ISplashView : IBaseView {
        fun launchDashBoardActivity()
    }

    //  Presenter
    interface ISplashPresenter {
        fun afterSomeDelay()
    }
}