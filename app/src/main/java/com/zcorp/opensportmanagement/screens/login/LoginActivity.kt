package com.zcorp.opensportmanagement.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zcorp.opensportmanagement.screens.teams.TeamsActivity
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.api.UserApiImpl
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : Activity(), LoginView, View.OnClickListener {

    val TAG = LoginActivity::class.java.name

    private val presenter: LoginPresenterImpl = LoginPresenterImpl(this, LoginModelImpl(UserApiImpl()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sign_in_button.setOnClickListener(this)
    }

    override fun onDestroy() {
        presenter.finish()
        super.onDestroy()
    }

    override fun showProgress() {
        login_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        login_progress.visibility = View.GONE
    }

    override fun setUsernameError() {
        username.error = getString(R.string.username_error)
    }

    override fun setPasswordError() {
        password.error = getString(R.string.password_error)
    }

    override fun navigateToHome() {
        startActivity(Intent(this, TeamsActivity::class.java))
        finish()
    }

    override fun onClick(v: View) {
        presenter.validateCredentials(username.text.toString(), password.text.toString())
    }
}
