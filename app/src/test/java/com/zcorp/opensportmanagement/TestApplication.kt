package com.zcorp.opensportmanagement

import android.content.Context

class TestApplication : MyApplication() {
    override fun attachBaseContext(base: Context) {
        try {
            super.attachBaseContext(base)
        } catch (ignored: RuntimeException) {
            // Multidex support doesn't play well with Robolectric yet
        }

    }
}