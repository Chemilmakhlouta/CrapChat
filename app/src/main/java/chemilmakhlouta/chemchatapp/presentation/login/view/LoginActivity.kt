package chemilmakhlouta.chemchatapp.presentation.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import chemilmakhlouta.chemchatapp.R
import chemilmakhlouta.chemchatapp.application.BaseActivity
import chemilmakhlouta.chemchatapp.application.injection.component.ActivityComponent
import chemilmakhlouta.chemchatapp.presentation.chats.view.LatestChatsActivity
import chemilmakhlouta.chemchatapp.presentation.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * Created by Chemil Makhlouta on 15/01/19.
 */
class LoginActivity : BaseActivity(), LoginPresenter.Display, LoginPresenter.Router {

    companion object {
        fun makeIntent(context: Context): Intent =
                Intent(context, LoginActivity::class.java)
    }
    @Inject
    override lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener { presenter.onLoginButtonClicked(email.text.toString(), password.text.toString()) }
        backToRegistrationButton.setOnClickListener { presenter.onBackToRegistrationClicked() }
    }

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
        presenter.inject(this, this)
    }

    override fun navigateToLatestChats() = startActivity(Intent(LatestChatsActivity.makeIntent(this)))

    override fun showError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun hideLoading() {
    }

    override fun showLoading() {
    }

    override fun backToRegistration() = onBackPressed()
}