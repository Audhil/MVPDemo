package com.audhil.medium.mvpdemo.ui.base

import android.widget.Toast

/*
 * Created by mohammed-2284 on 01/05/18.
 */

interface IBaseView {
    fun showProgress()
    fun hideProgress()

    fun showSuccessMessage(message: String, toastDuration: Int = Toast.LENGTH_LONG)
    fun showErrorMessage(message: String, toastDuration: Int = Toast.LENGTH_LONG)
}