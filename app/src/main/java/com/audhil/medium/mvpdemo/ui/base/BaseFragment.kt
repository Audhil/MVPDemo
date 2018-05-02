package com.audhil.medium.mvpdemo.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/*
 * Created by mohammed-2284 on 01/05/18.
 */

abstract class BaseFragment : Fragment(), IBaseView {

    private var parentActivity: BaseActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.parentActivity = activity
            activity?.onFragmentAttached()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(getLayoutId(), container, false)

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun hideProgress() {
        parentActivity?.hideProgress()
    }

    override fun showProgress() {
        parentActivity?.showProgress()
    }

    override fun showErrorMessage(message: String, toastDuration: Int) {
        parentActivity?.showErrorMessage(message, toastDuration)
    }

    override fun showSuccessMessage(message: String, toastDuration: Int) {
        parentActivity?.showSuccessMessage(message, toastDuration)
    }

    //  fragment call back
    interface CallBack {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}