package com.audhil.medium.mvpdemo.ui.fragments.viewholders

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.audhil.medium.mvpdemo.R
import com.audhil.medium.mvpdemo.util.showVLog

/*
 * Created by mohammed-2284 on 14/04/18.
 */

class MoviesEmptyViewHolder(
        parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.empty_view_item, parent, false)) {

    fun bindTo(pos: Int) {
        showVLog("audhil pos : $pos")
    }
}