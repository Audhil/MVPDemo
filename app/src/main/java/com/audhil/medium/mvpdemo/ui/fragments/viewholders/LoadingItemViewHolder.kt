package com.audhil.medium.mvpdemo.ui.fragments.viewholders

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.audhil.medium.mvpdemo.R

/*
 * Created by mohammed-2284 on 11/04/18.
 */

class LoadingItemViewHolder(
        parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.loading_item, parent, false))