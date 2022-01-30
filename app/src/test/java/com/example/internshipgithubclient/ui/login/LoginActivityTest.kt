package com.example.internshipgithubclient.ui.login

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.internshipgithubclient.ui.workspace.UserWorkSpaceActivity
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.any
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    private val loginViewModel = mock(LoginViewModel::class.java)

    @get:Rule
    val activityScenario = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun `when user authorized proceed to next screen`(){
        whenever(loginViewModel.checkIfAuthorized()).thenReturn(true)
        activityScenario.scenario.onActivity {
            Intents.init()
            it.loginViewModel = loginViewModel
            activityScenario.scenario.moveToState(Lifecycle.State.STARTED)
            activityScenario.scenario.moveToState(Lifecycle.State.RESUMED)
            intended(hasComponent(UserWorkSpaceActivity::class.java.canonicalName))
            Intents.release()
        }
    }

    @Test
    fun `when user not authorized prepare authorization`(){
        var authorizationPrepHasStarted = false
        whenever(loginViewModel.checkIfAuthorized()).thenReturn(false)
        whenever(loginViewModel.prepareAuthorization()).then {
            authorizationPrepHasStarted = true
            it
        }
        activityScenario.scenario.onActivity {
            it.loginViewModel = loginViewModel
            activityScenario.scenario.moveToState(Lifecycle.State.STARTED)
            activityScenario.scenario.moveToState(Lifecycle.State.RESUMED)
            Assert.assertTrue(authorizationPrepHasStarted)
        }
    }
}