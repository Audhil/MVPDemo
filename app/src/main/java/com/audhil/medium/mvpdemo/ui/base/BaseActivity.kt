package com.audhil.medium.mvpdemo.ui.base

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.audhil.medium.mvpdemo.R

/*
 * Created by mohammed-2284 on 01/05/18.
 */

abstract class BaseActivity : AppCompatActivity(), IBaseView, BaseFragment.CallBack {

    //  alert dialog dialogBuilder
    private val dialogBuilder: AlertDialog.Builder? by lazy {
        AlertDialog.Builder(this)
    }

    //  progress dialog
    private val progressDialog: AlertDialog? by lazy {
        dialogBuilder?.create()
    }

    //  toast
    private val toast: Toast? by lazy {
        Toast(this)
    }

    private val toastSuccessTxtView by lazy {
        toast?.view?.findViewById<TextView>(R.id.success_message)
    }

    private val toastFailureTxtView by lazy {
        toast?.view?.findViewById<TextView>(R.id.failure_message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialogBuilder?.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.progress_dialog)
            setCancelable(false)
        }
        setContentView(getLayoutId())
    }

    //  hide progress dialog
    override fun hideProgress() {
        progressDialog?.let {
            if (it.isShowing)
                it.cancel()
        }
    }

    //  show progress dialog
    override fun showProgress() {
        hideProgress()
        progressDialog?.show()
    }

    //  showing success message
    override fun showSuccessMessage(message: String, toastDuration: Int) {
        toast?.view = LayoutInflater.from(applicationContext).inflate(R.layout.toast_success, null)
        toastSuccessTxtView?.text = message
        toast?.apply {
            setGravity(Gravity.CENTER, 0, 0)
            duration = toastDuration
            show()
        }
    }

    //  showing error message
    override fun showErrorMessage(message: String, toastDuration: Int) {
        toast?.view = LayoutInflater.from(applicationContext).inflate(R.layout.toast_failure, null)
        toastFailureTxtView?.text = message
        toast?.apply {
            setGravity(Gravity.CENTER, 0, 0)
            duration = toastDuration
            show()
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
}