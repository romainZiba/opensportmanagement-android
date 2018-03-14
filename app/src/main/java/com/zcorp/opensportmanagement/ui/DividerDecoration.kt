package com.zcorp.opensportmanagement.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.TypedValue
import android.view.View
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.eventdetails.ParticipantRecyclerAdapter.Companion.ABSENT_PLAYERS_HEADER_VIEW_TYPE
import com.zcorp.opensportmanagement.ui.eventdetails.ParticipantRecyclerAdapter.Companion.PRESENT_PLAYERS_HEADER_VIEW_TYPE


/**
 * Created by romainz on 14/03/18.
 */
class DividerDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val mPaint: Paint = Paint()
    private val mDivider: Drawable
    private val TAG = "DividerItem"
    private val ATTRS = intArrayOf(android.R.attr.listDivider)
    private val mBounds = Rect()

    init {
        mPaint.style = Paint.Style.FILL
        mPaint.color = context.resources.getColor(R.color.black)
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        if (mDivider == null) {
            Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this " + "DividerItemDecoration. Please set that attribute all call setDrawable()")
        }
        a.recycle()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        var viewType = parent.adapter.getItemViewType(position)
        if (viewType == PRESENT_PLAYERS_HEADER_VIEW_TYPE || viewType == ABSENT_PLAYERS_HEADER_VIEW_TYPE) {
            outRect.set(0, 0, 0, mDivider.intrinsicHeight)
        } else {
            outRect.setEmpty()
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right,
                    parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }

        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            var viewType = parent.adapter.getItemViewType(position)
            if (viewType == PRESENT_PLAYERS_HEADER_VIEW_TYPE || viewType == ABSENT_PLAYERS_HEADER_VIEW_TYPE) {
                val child = parent.getChildAt(i)
                parent.getDecoratedBoundsWithMargins(child, mBounds)
                val bottom = mBounds.bottom + Math.round(child.translationY)
                val top = bottom - mDivider.intrinsicHeight
                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(canvas)
            }
        }
    }
}