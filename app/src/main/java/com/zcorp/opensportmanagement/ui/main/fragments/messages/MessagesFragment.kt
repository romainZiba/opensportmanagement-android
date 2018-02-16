package com.zcorp.opensportmanagement.ui.main.fragments.messages


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import kotlinx.android.synthetic.main.fragment_messages.*
import javax.inject.Inject


/**
 * A fragment with messages
 */
class MessagesFragment : BaseFragment() {

    @Inject
    lateinit var dataManager: IDataManager

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mFragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onResume() {
        super.onResume()
        dataManager.getMessages().subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    tv_messages.text = it.map { inAppMessage -> inAppMessage.message }.reduce { a, b -> a + "\n" + b }
                }, {
                })

    }
}
