package com.zcorp.opensportmanagement.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), ILoginView, View.OnClickListener {

    val TAG = LoginActivity::class.java.name

    @Inject
    lateinit var presenter: ILoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mActivityComponent.inject(this)
        setContentView(R.layout.activity_login)
        presenter.onAttach(this)
        btn_server_login.setOnClickListener(this)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun showProgress() {
        progressBar_login.visibility = View.VISIBLE
        cardView_login.alpha = 0.3F
    }

    override fun hideProgress() {
        progressBar_login.visibility = View.GONE
        cardView_login.alpha = 1F
    }

    override fun setUsernameError() {
        et_username.error = getString(R.string.username_error)
    }

    override fun setPasswordError() {
        et_password.error = getString(R.string.password_error)
    }

    override fun navigateToHome(availableTeams: List<String>) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(TEAMS_KEY, availableTeams.toTypedArray())
        startActivity(intent)
        finish()
    }

    override fun onClick(v: View) {
        presenter.validateCredentials(et_username.text.toString(), et_password.text.toString())
    }
    companion object {
        const val TEAMS_KEY = "availableTeamsKey"
    }
}
