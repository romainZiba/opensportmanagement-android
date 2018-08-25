package com.zcorp.opensportmanagement.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.members.adapter.TeamMemberRecyclerAdapter.Companion.ABSENT_PLAYERS_HEADER_VIEW_TYPE
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.members.adapter.TeamMemberRecyclerAdapter.Companion.PRESENT_PLAYERS_HEADER_VIEW_TYPE

/**
 * Application of the material specification
 * https://material.io/guidelines/components/dividers.html#dividers-types-of-dividers
 */
class DividerDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val mPaint: Paint = Paint()
    private val mDivider: Drawable
    private val TAG = "DividerDecoration"
    private val ATTRS = intArrayOf(android.R.attr.listDivider)
    private val mBounds = Rect()
    private var mDensity: Float = 1F

    init {
        mPaint.style = Paint.Style.FILL
        mPaint.color = context.resources.getColor(R.color.black)
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        if (mDivider == null) {
            Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this " + "DividerItemDecoration. Please set that attribute all call setDrawable()")
        }
        val dm = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(dm)
        mDensity = dm.density
        a.recycle()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        var nextViewType = parent.adapter?.getItemViewType(position + 1)
        if (nextViewType == PRESENT_PLAYERS_HEADER_VIEW_TYPE || nextViewType == ABSENT_PLAYERS_HEADER_VIEW_TYPE) {
            outRect.set(0, 0, 0, mDivider.intrinsicHeight + Math.round(20 * mDensity))
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
            var nextViewType = parent.adapter?.getItemViewType(position + 1)
            if (nextViewType == PRESENT_PLAYERS_HEADER_VIEW_TYPE || nextViewType == ABSENT_PLAYERS_HEADER_VIEW_TYPE) {
                val paddingLeft = Math.round(60 * mDensity)
                val child = parent.getChildAt(i)
                parent.getDecoratedBoundsWithMargins(child, mBounds)
                val bottom = mBounds.bottom + Math.round(child.translationY)
                val top = bottom - mDivider.intrinsicHeight
                mDivider.setBounds(left + paddingLeft, top, right, bottom)
                mDivider.draw(canvas)
            }
        }
    }
}