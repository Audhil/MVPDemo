package com.audhil.medium.mvpdemo.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import com.audhil.medium.mvpdemo.AppDelegate

/*
 * Created by mohammed-2284 on 02/05/18.
 */

//  show logs
fun Any.showVLog(log: String) = Log.v(this::class.java.simpleName, log)

fun Any.showELog(log: String) = Log.e(this::class.java.simpleName, log)

fun Any.showDLog(log: String) = Log.d(this::class.java.simpleName, log)

fun Any.showILog(log: String) = Log.i(this::class.java.simpleName, log)

fun Any.showWLog(log: String) = Log.w(this::class.java.simpleName, log)

//  show toast
fun Any.showToast(context: Context? = AppDelegate.INSTANCE, duration: Int = Toast.LENGTH_SHORT) =
        when {
            this is String ->
                Toast.makeText(context, this, duration).show()
            this is Int ->
                Toast.makeText(context, this, duration).show()
            else ->
                Toast.makeText(context, "INVALID_INPUT can't show Toast, sorry!", duration).show()
        }

//  is internet available
fun Context.isNetworkConnected(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNet: NetworkInfo?
    return if (cm != null) {
        activeNet = cm.activeNetworkInfo
        activeNet != null && activeNet.isConnected
    } else
        false
}