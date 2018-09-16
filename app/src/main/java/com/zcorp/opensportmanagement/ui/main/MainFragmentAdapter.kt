package com.zcorp.opensportmanagement.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.zcorp.opensportmanagement.ui.utils.SmartFragmentStatePagerAdapter

class MainFragmentAdapter(fragmentManager: FragmentManager) : SmartFragmentStatePagerAdapter(fragmentManager) {
    private val fragments = mutableMapOf<Int, Fragment>()

    fun addFragment(tag: Int, fragment: Fragment) {
        fragments[tag] = fragment
    }

    override fun getItem(position: Int): Fragment? {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}