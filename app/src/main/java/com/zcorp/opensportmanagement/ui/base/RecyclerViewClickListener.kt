package com.zcorp.opensportmanagement.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View

interface RecyclerViewClickListener {
    fun onSelected(view: View, position: Int)
    fun onLongSelected(view: View, position: Int): Boolean
    fun refreshItemState(holder: RecyclerView.ViewHolder, position: Int)
}