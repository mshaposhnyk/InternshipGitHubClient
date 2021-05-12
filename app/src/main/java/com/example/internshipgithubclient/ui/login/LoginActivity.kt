package com.example.internshipgithubclient.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.ActivityLoginBinding
import com.example.internshipgithubclient.ui.workspace.UserWorkSpaceActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {
    private val REQUEST_AUTH: Int = 100//вынести в константу
    lateinit var binding: ActivityLoginBinding//в онКриэйт

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val loginViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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

    private fun moveToUserWorkSpaceActivity(tokenExchanged: Boolean) {
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
                AuthorizationResponse.fromIntent(it)
            }
            val exception = AuthorizationException.fromIntent(data)
            //Passing response from browser to viewModel
            loginViewModel.handleAuthorizationRequest(response, exception)
        }
    }
}