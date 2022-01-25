package com.example.internshipgithubclient.ui.login

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.example.internshipgithubclient.network.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import net.openid.appauth.*
import javax.inject.Inject

open class LoginViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    private val _eventTokenExchanged = MutableStateFlow<Boolean>(false)
    val eventTokenExchanged: StateFlow<Boolean>
        get() = _eventTokenExchanged

    //Auth request for creating OAuth login intent
    private lateinit var _authRequest: AuthorizationRequest

    @Inject
    lateinit var authStateHelper: AuthStateHelper

    //Auth service for performing OAuth login
    private var _authService: AuthorizationService = AuthorizationService(application)

    private val app:Application = application


    fun checkIfAuthorized():Boolean{
        //Reading state of authorization
        runBlocking {
            authStateHelper.readState(app)
        }
        return authStateHelper.currentAuthState.isAuthorized
    }

    fun prepareAuthorization() {
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse(AUTHORIZATION_ENDPOINT),
            Uri.parse(TOKEN_ENDPOINT)
        )

        val clientId =
            CLIENT_ID
        //Callback URI, also present in manifest as an intent-filter for appAuth activity
        val redirectUri = Uri.parse("githubclient://login")
        val builder = AuthorizationRequest.Builder(
            serviceConfig,
            clientId,
            ResponseTypeValues.ID_TOKEN,
            redirectUri
        )
        builder.setScopes(SCOPE_USER, SCOPE_REPO)
        _authRequest = builder.build()
    }

    fun handleAuthorizationRequest(
        response: AuthorizationResponse?,
        exception: AuthorizationException?
    ) {
        if (response != null) {
            //I can't perform token exchange without a secret.
            // I didn't find a way to get this secret dynamically.
            // It isn't secure to store it here.
            val clientSecret: ClientAuthentication =
                ClientSecretBasic("7abb75765d8a384399eb00b0c08d8bce80fb3be3")
            _authService = AuthorizationService(getApplication())
            //updating our authorization info
            authStateHelper.updateAfterAuthorization(response, exception)
            //Writing new state of authState to DataStore
            //Must be moved to AuthHelper somehow
            runBlocking {
                authStateHelper.writeState(getApplication())
            }
            //getting an access token
            _authService.performTokenRequest(
                response.createTokenExchangeRequest(), clientSecret
            ) { resp, ex ->
                if (resp != null) {
                    authStateHelper.updateAfterTokenResponse(resp, ex)
                    //Writing new state of authState to DataStore
                    //Must be moved to AuthHelper somehow
                    runBlocking {
                        authStateHelper.writeState(getApplication())
                    }
                    _eventTokenExchanged.value = true
                }
            }
        }
    }

    //Providing intent to start login activity
    fun provideAuthIntent(): Intent = _authService.getAuthorizationRequestIntent(_authRequest)
}