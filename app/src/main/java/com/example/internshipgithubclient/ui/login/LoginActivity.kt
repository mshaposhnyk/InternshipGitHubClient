package com.example.internshipgithubclient.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.ActivityLoginBinding
import com.example.internshipgithubclient.ui.workspace.UserWorkSpaceActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.openid.appauth.AuthorizationException
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @VisibleForTesting
    lateinit var loginViewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loginViewModel = ViewModelProvider(this, viewModelFactory)
            .get(LoginViewModel::class.java)
        initActivity()
    }

    private fun initActivity(){
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            //Open browser for typing in credentials
            val authIntent = loginViewModel.provideAuthIntent()
            startActivityForResult(authIntent, REQUEST_AUTH)
        }
        loginViewModel.eventTokenExchanged
            .onEach {
                moveToUserWorkSpaceActivity(it)
            }
            .launchIn(lifecycleScope)
    }

    @VisibleForTesting
    fun moveToUserWorkSpaceActivity(tokenExchanged: Boolean) {
        if (tokenExchanged) {
            // Start activity
            startActivity(Intent(this, UserWorkSpaceActivity::class.java))

            // Animate the loading of new activity
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            // Close this activity
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val isAuthorized = loginViewModel.checkIfAuthorized()
        //If authorized then open RepoListFragment
        if (isAuthorized) {
            moveToUserWorkSpaceActivity(isAuthorized)
        } else {
            //Creating authorization request
            loginViewModel.prepareAuthorization()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_AUTH) {
            val response = data?.let {
                loginViewModel.provideAuthResponse(it)
            }
            val exception = AuthorizationException.fromIntent(data)
            //Passing response from browser to viewModel
            loginViewModel.handleAuthorizationRequest(response, exception)
        }
    }

    companion object {
        const val REQUEST_AUTH: Int = 100
    }
}