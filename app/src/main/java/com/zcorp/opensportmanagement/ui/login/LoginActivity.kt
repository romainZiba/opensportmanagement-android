package com.zcorp.opensportmanagement.ui.login

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.repository.FailedEvent
import com.zcorp.opensportmanagement.repository.LoadingEvent
import com.zcorp.opensportmanagement.repository.SuccessEvent
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.btn_server_login
import kotlinx.android.synthetic.main.activity_login.et_password
import kotlinx.android.synthetic.main.activity_login.et_username
import kotlinx.android.synthetic.main.activity_login.progressBar_login
import kotlinx.android.synthetic.main.activity_login.til_password
import org.koin.android.architecture.ext.viewModel

class LoginActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val TEAMS_KEY = "availableTeamsKey"
        private val TAG = LoginActivity::class.java.name
    }

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_server_login.setOnClickListener(this)
        viewModel.events.observe(this, Observer {
            when (it) {
                is FailedEvent -> {
                    hideProgress()
                    setPasswordError()
                }
                is LoadingEvent -> {
                    showProgress()
                }
                is SuccessEvent -> {
                    hideProgress()
                    navigateToHome()
                }
            }
        })
    }

    fun disableLoginButton() {
        btn_server_login.isEnabled = false
    }

    fun enableLoginButton() {
        btn_server_login.isEnabled = true
    }

    fun showProgress() {
        progressBar_login.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar_login.visibility = View.GONE
    }

    fun setUsernameError() {
        et_username.error = getString(R.string.username_error)
    }

    private fun setPasswordError() {
        til_password.error = getString(R.string.password_error)
        et_password.background.clearColorFilter()
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onClick(v: View) {
        viewModel.login(et_username.text.toString(), et_password.text.toString())
    }
}
