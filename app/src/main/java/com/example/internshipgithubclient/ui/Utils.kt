package com.example.internshipgithubclient.ui

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.core.domain.User

fun loadCircleImage(context: Context, uri:String, imageView:ImageView){
    Glide.with(context)
        .load(uri)
        .circleCrop()
        .into(imageView)
}

fun createDummyUser() : User = User(
    -1,
    "",
    "",
    "",
    "",
    -1,
    -1,
    "-1",
    "",
    "",
    "",
    -1,
    -1
)