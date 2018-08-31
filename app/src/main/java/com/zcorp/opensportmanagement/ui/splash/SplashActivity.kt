package com.zcorp.opensportmanagement.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mHandler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500)
    }
}
