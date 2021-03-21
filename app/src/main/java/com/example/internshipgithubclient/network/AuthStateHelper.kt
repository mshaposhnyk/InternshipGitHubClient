package com.example.internshipgithubclient.network

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "authState")
val STATE: Preferences.Key<String> = stringPreferencesKey("state")

@Singleton
class AuthStateHelper @Inject constructor() {
    //keeps accessToken, scopes
    var currentAuthState: AuthState = AuthState()

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

    //Reads AuthState object to DataStore
    //Must be async in future
    suspend fun readState(context: Context){
        val prevCurrentState = currentAuthState.jsonSerializeString()
        val authState : Flow<AuthState> = runBlocking {
            context.dataStore.data.map { authState ->
                val authStateSerialized = authState[STATE] ?: prevCurrentState
                AuthState.jsonDeserialize(authStateSerialized)
            }
        }
        currentAuthState = authState.first()
    }

    //Writes AuthState object to DataStore
    //Must be async in future
    suspend fun writeState(context: Context){
        context.dataStore.edit{
            authState ->
            authState[STATE] = currentAuthState.jsonSerializeString()
        }
    }

}