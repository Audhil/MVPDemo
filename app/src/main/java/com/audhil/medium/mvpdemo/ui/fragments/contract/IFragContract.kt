package com.audhil.medium.mvpdemo.ui.fragments.contract

import com.audhil.medium.mvpdemo.ui.base.IBaseView

/*
 * Created by mohammed-2284 on 02/05/18.
 */

interface IFragContract {

    //  View
    interface IFragView : IBaseView {
        fun loadRecyclerView()
        fun exception(exceptionName: String)
    }

    //  Presenter
    interface IFragPresenter {
        fun fetchMovies(clickedPos: Int?)
    }
}