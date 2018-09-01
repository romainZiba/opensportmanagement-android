package com.imminov.equisure.view.base

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseRecyclerViewAdapter<T, U>(
    private val mValues: List<T>,
    private val mListener: RecyclerViewClickListener
) : RecyclerView.Adapter<U>() where U : RecyclerView.ViewHolder {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { view ->
            val item = view.tag as T
            val position = mValues.indexOf(item)
            mListener.onSelected(view, position)
        }
    }

    private val mOnLongClickListener: View.OnLongClickListener

    init {
        mOnLongClickListener = View.OnLongClickListener { view ->
            val item = view.tag as T
            val position = mValues.indexOf(item)
            mListener.onLongSelected(view, position)
            return@OnLongClickListener true
        }
    }

    override fun onBindViewHolder(holder: U, position: Int) {
        val item = mValues[position]
        with(holder.itemView) {
            tag = item
            setOnClickListener(mOnClickListener)
            setOnLongClickListener(mOnLongClickListener)
        }
        mListener.refreshItemState(holder, position)
    }

    override fun getItemCount(): Int = mValues.size
}
