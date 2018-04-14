package com.zcorp.opensportmanagement.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.repository.FailedEvent
import com.zcorp.opensportmanagement.repository.LoadingEvent
import com.zcorp.opensportmanagement.repository.SuccessEvent
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val TEAMS_KEY = "availableTeamsKey"
        private val TAG = LoginActivity::class.java.name
    }

    private lateinit var mLoginViewModel: LoginViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mActivityComponent.inject(this)
        setContentView(R.layout.activity_login)
        btn_server_login.setOnClickListener(this)
        mLoginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        mLoginViewModel.events.observe(this, Observer {
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
        mLoginViewModel.login(et_username.text.toString(), et_password.text.toString())
    }
}
