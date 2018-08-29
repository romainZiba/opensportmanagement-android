package com.zcorp.opensportmanagement.ui.splash

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.view.View
import android.widget.EditText
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.di.offlineModule
import com.zcorp.opensportmanagement.di.onlineModule
import com.zcorp.opensportmanagement.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.btn_login
import kotlinx.android.synthetic.main.activity_splash.btn_test_offline
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.releaseContext
import org.koin.standalone.StandAloneContext.loadKoinModules

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()
    private val mPreferencesHelper: PreferencesHelper by inject()
    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mHandler.postDelayed({
            viewModel.getLoggedState()
        }, 1500)

        btn_login.setOnClickListener { showLoginDialog() }
        btn_test_offline.setOnClickListener { startMainActivity(false) }

        viewModel.loggedState.observe(this, Observer { logged ->
            when (logged) {
                true -> startMainActivity(online = true)
                false -> {
                    btn_login.visibility = View.VISIBLE
                    btn_test_offline.visibility = View.VISIBLE
                    // TODO: this does not clear the tables, or maybe it takes too long...
                    viewModel.clearTables()
                    mPreferencesHelper.clear()
                    releaseContext("repo")
                    MaterialDialog(this)
                            .title(R.string.not_logged)
                            .message(R.string.message_not_logged)
                            .positiveButton(R.string.ok, null) { messageDialog ->
                                messageDialog.dismiss()
                            }
                            .show()

                }
            }
        })
    }

    private fun showLoginDialog() {
        val loginDialog = MaterialDialog(this)
                .customView(R.layout.dialog_login)
        loginDialog.show()
        val usernameInput = loginDialog.findViewById<EditText>(R.id.et_dialog_login_username)
        val passwordInput = loginDialog.findViewById<EditText>(R.id.et_dialog_login_password)
        val loginBtn = loginDialog.findViewById<AppCompatButton>(R.id.btn_dialog_login_login)
        val cancelBtn = loginDialog.findViewById<AppCompatButton>(R.id.btn_dialog_login_cancel)
        loginBtn.setOnClickListener { _ ->
            loginDialog.dismiss()
            viewModel.login(username = usernameInput?.text.toString(),
                    password = passwordInput?.text.toString())
        }
        cancelBtn.setOnClickListener { _ -> loginDialog.dismiss() }
        loginDialog.show()
    }

    private fun startMainActivity(online: Boolean) {
        if (online) {
            loadKoinModules(onlineModule)
        } else {
            loadKoinModules(offlineModule)
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
