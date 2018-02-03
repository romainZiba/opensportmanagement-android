package com.zcorp.opensportmanagement.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.application.MyApplication
import com.zcorp.opensportmanagement.screens.login.dagger.DaggerLoginComponent
import com.zcorp.opensportmanagement.screens.login.dagger.LoginContextModule
import com.zcorp.opensportmanagement.screens.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : Activity(), LoginView, View.OnClickListener {

    val TAG = LoginActivity::class.java.name

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerLoginComponent.builder()
                .appComponent(MyApplication.appComponent)
                .loginContextModule(LoginContextModule(this))
                .build()
                .inject(this)

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
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onClick(v: View) {
        presenter.validateCredentials(username.text.toString(), password.text.toString())
    }
}
