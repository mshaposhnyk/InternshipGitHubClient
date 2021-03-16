package com.example.internshipgithubclient.ui

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun loadCircleImage(context: Context, uri:String, imageView:ImageView){
    Glide.with(context)
        .load(uri)
        .circleCrop()
        .into(imageView)
}