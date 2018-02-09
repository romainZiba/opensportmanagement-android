package com.zcorp.opensportmanagement.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.zcorp.opensportmanagement.MyApplication
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.di.component.DaggerActivityComponent
import com.zcorp.opensportmanagement.di.module.ActivityModule
import com.zcorp.opensportmanagement.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginView, View.OnClickListener {

    val TAG = LoginActivity::class.java.name

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerActivityComponent.builder()
                .appComponent(MyApplication.appComponent)
                .activityModule(ActivityModule(this))
                .build()
                .inject(this)

        presenter.onAttach(this)
        setContentView(R.layout.activity_login)
        sign_in_button.setOnClickListener(this)
    }

    override fun onDestroy() {
        presenter.onDetach()
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
