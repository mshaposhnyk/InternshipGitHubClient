package com.example.internshipgithubclient.network

import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse

class AuthStateHelper {
    //keeps accessToken, scopes
    val currentAuthState: AuthState = AuthState()

    fun updateAfterAuthorization(response: AuthorizationResponse,
                                 exception: AuthorizationException?): AuthState {
        currentAuthState.update(response, exception)
        return currentAuthState
    }

    fun updateAfterTokenResponse(response: TokenResponse,
                                 exception: AuthorizationException?): AuthState {
        currentAuthState.update(response, exception)
        return currentAuthState
    }

}