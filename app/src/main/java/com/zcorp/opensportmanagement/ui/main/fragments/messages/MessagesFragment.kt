package com.zcorp.opensportmanagement.ui.main.fragments.messages


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import javax.inject.Inject


/**
 * A fragment with messages
 */
class MessagesFragment : BaseFragment() {

    @Inject
    lateinit var dataManager: IDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mFragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_messages, container, false)
    }

}
