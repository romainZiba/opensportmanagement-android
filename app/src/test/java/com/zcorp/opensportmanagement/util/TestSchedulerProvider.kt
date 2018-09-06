package com.zcorp.opensportmanagement.util

import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider : SchedulerProvider {
    override fun io() = Schedulers.trampoline()

    override fun ui() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()

    override fun newThread() = Schedulers.trampoline()
}