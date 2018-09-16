package com.zcorp.opensportmanagement.ui.base

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.zcorp.opensportmanagement.R

abstract class BaseListFragment<T> : BaseFragment(), RecyclerViewClickListener {

    private var mActionMode: ActionMode? = null
    private val selectedItems: SparseBooleanArray = SparseBooleanArray()
    protected var mItems: List<T> = listOf()
    abstract val recyclerView: RecyclerView?
    private val mActionModeCallback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val inflater = mode.menuInflater
            inflater.inflate(R.menu.context_menu_edit_delete, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.delete_item -> {
                    val itemsToDelete: MutableList<T> = mutableListOf()
                    for (index in 0 until selectedItems.size()) {
                        val position = selectedItems.keyAt(index)
                        itemsToDelete.add(mItems[position])
                    }
                    deleteSelectedItems(itemsToDelete)
                    selectedItems.clear()
                    closeActionMode()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            for (index in 0 until selectedItems.size()) {
                recyclerView?.layoutManager?.findViewByPosition(selectedItems.keyAt(index))?.isSelected = false
            }
            selectedItems.clear()
            mActionMode = null
        }
    }

    private fun closeActionMode() {
        mActionMode?.finish()
        mActionMode = null
    }

    override fun onSelected(view: View, position: Int) {
        if (mActionMode != null) {
            when (view.isSelected) {
                true -> {
                    view.isSelected = false
                    selectedItems.delete(position)
                }
                false -> {
                    view.isSelected = true
                    selectedItems.put(position, true)
                }
            }
            if (selectedItems.size() == 0) {
                closeActionMode()
            }
        } else {
            startActivity(buildIntent(position))
        }
    }

    override fun onLongSelected(view: View, position: Int): Boolean {
        if (mActionMode != null) {
            return false
        }
        mActionMode = activity?.startActionMode(mActionModeCallback)
        view.isSelected = true
        selectedItems.put(position, true)
        return true
    }

    override fun refreshItemState(holder: RecyclerView.ViewHolder, position: Int) {
        val isItemSelected = selectedItems.get(position)
        val view: View? = holder.itemView
        view?.isSelected = isItemSelected
    }

    abstract fun buildIntent(selectedItem: Int): Intent

    abstract fun deleteSelectedItems(items: List<T>)
}