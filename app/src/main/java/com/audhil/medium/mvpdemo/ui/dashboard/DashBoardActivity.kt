package com.audhil.medium.mvpdemo.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import com.audhil.medium.mvpdemo.AppDelegate
import com.audhil.medium.mvpdemo.R
import com.audhil.medium.mvpdemo.ui.base.BaseActivity
import com.audhil.medium.mvpdemo.ui.fragments.MovieFragment
import com.audhil.medium.mvpdemo.util.ConstantsUtil
import kotlinx.android.synthetic.main.dash_board_activity.*
import android.view.inputmethod.InputMethodManager
import com.audhil.medium.mvpdemo.util.BiCallBack


class DashBoardActivity : BaseActivity(), View.OnClickListener {

    override fun getLayoutId(): Int = R.layout.dash_board_activity

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    //  click pos
    private var clickPos = 0

    //  fragment communication call back
    var fragCommCallback: BiCallBack<String, Boolean>? = null

    //  input manager
    private val inputMethodManager: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    companion object {
        fun newIntent(): Intent = Intent(AppDelegate.INSTANCE, DashBoardActivity::class.java)
    }

    //  view click listener
    override fun onClick(v: View) {
        unClickIt(popular_btn)
        unClickIt(top_rated_btn)
        unClickIt(upcoming_btn)

        when (v.id) {
            R.id.popular_btn -> {
                clickIt(popular_btn)
                clickPos = ConstantsUtil.POPULAR_MOVIES
            }
            R.id.top_rated_btn -> {
                clickIt(top_rated_btn)
                clickPos = ConstantsUtil.TOP_RATED_MOVIES
            }

            R.id.upcoming_btn -> {
                clickIt(upcoming_btn)
                clickPos = ConstantsUtil.UPCOMING_MOVIES
            }
        }
        //  take away edittext focus
        movie_edit_text.setText(ConstantsUtil.EMPTY)
        inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
        supportFragmentManager?.beginTransaction()?.replace(R.id.container, MovieFragment.newInstance(clickPos))?.commit()
    }

    //  on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTags(ConstantsUtil.UN_CLICKED)
        setFocusListener()
        setClickListeners()
        setViewTextWatcher()
        top_rated_btn.performClick()
    }

    //  edit text focus listener
    private fun setFocusListener() {
        movie_edit_text.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                fragCommCallback?.invoke(ConstantsUtil.EMPTY, false)
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
                movie_edit_text.setText(ConstantsUtil.EMPTY)
            }
        }
    }

    //  set text watcher
    private fun setViewTextWatcher() {
        movie_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                fragCommCallback?.invoke(s.toString(), true)
            }
        })
    }

    //  view click listeners
    private fun setClickListeners() {
        popular_btn.setOnClickListener(this)
        top_rated_btn.setOnClickListener(this)
        upcoming_btn.setOnClickListener(this)
    }

    //  click this btn
    private fun clickIt(btn: Button) {
        btn.tag = ConstantsUtil.CLICKED
        btn.background = ContextCompat.getDrawable(applicationContext, R.drawable.btn_bg)
        btn.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.white))
        ViewCompat.setElevation(btn, 25f)
    }

    //  un click it
    private fun unClickIt(btn: Button) {
        btn.tag = ConstantsUtil.UN_CLICKED
        btn.setBackgroundResource(0)
        btn.setTextColor(ContextCompat.getColor(applicationContext, R.color.txt_gray))
        ViewCompat.setElevation(btn, 0f)
    }

    //  set tags
    private fun setTags(tag: String) {
        popular_btn.tag = tag
        top_rated_btn.tag = tag
        upcoming_btn.tag = tag
    }
}