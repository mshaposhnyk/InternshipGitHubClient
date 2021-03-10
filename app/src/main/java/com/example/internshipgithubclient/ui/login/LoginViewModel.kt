package com.example.internshipgithubclient.ui.login

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.internshipgithubclient.network.*
import com.example.internshipgithubclient.network.user.UserApiService
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.openid.appauth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _eventTokenExchanged = MutableLiveData<Boolean>()
    val eventTokenExchanged: LiveData<Boolean>
        get() = _eventTokenExchanged
    private val _userEntity = MutableLiveData<UserNetworkEntity>()
    val userEntity: LiveData<UserNetworkEntity>
        get() = _userEntity
    //Auth request for creating OAuth login intent
    private lateinit var _authRequest: AuthorizationRequest
    //Auth service for performing OAuth login
    private var _authService: AuthorizationService = AuthorizationService(application)

    init {
        //Reading state of authorization
        runBlocking {
            AuthStateHelper.readState(application)
        }
        //If authorized then open RepoListFragment
        if(AuthStateHelper.currentAuthState.isAuthorized){
            _eventTokenExchanged.value = true
        }
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
            val clientSecret: ClientAuthentication = ClientSecretBasic("7abb75765d8a384399eb00b0c08d8bce80fb3be3")
            _authService = AuthorizationService(getApplication())
            //updating our authorization info
            AuthStateHelper.updateAfterAuthorization(response, exception)
            //Writing new state of authState to DataStore
            //Must be moved to AuthHelper somehow
            runBlocking {
                AuthStateHelper.writeState(getApplication())
            }
            //getting an access token
            _authService.performTokenRequest(
                    response.createTokenExchangeRequest(), clientSecret
            ) { response, exception ->
                if (response != null) {
                    AuthStateHelper.updateAfterTokenResponse(response, exception)
                    //Writing new state of authState to DataStore
                    //Must be moved to AuthHelper somehow
                    runBlocking {
                        AuthStateHelper.writeState(getApplication())
                    }
                    _eventTokenExchanged.value = true
                }
            }
        }
    }

    //Providing intent to start login activity
    fun provideAuthIntent() : Intent = _authService.getAuthorizationRequestIntent(_authRequest)
}