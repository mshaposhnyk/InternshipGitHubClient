package com.example.internshipgithubclient.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.ui.workspace.UserWorkSpaceActivity
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

class LoginActivity : AppCompatActivity() {
    private val REQUEST_AUTH: Int = 100
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            //Open browser for typing in credentials
            val authIntent = loginViewModel.provideAuthIntent()
            startActivityForResult(authIntent, REQUEST_AUTH)
        }
        loginViewModel.eventTokenExchanged.observe(this, Observer<Boolean> {
            if(it == true){
                // Start activity
                startActivity(Intent(this, UserWorkSpaceActivity::class.java))

                // Animate the loading of new activity
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

                // Close this activity
                finish()
            }
        } )
    }

    override fun onResume() {
        super.onResume()
        //Creating authorization request
        loginViewModel.prepareAuthorization()
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