package com.example.internshipgithubclient.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.internshipgithubclient.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Hide the toolbar
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
    }
}