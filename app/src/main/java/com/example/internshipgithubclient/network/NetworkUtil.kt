package com.example.internshipgithubclient.network

const val AUTHORIZATION_ENDPOINT = "https://github.com/login/oauth/authorize"
const val TOKEN_ENDPOINT = "https://github.com/login/oauth/access_token"

const val HEADER_AUTHORIZATION = "Authorization"
const val HEADER_APPLICATION = "Accept"
const val HEADER_APPLICATION_CONTENT = """" application/vnd.github.v3+json"""
const val API_URL = "https://api.github.com"

const val CLIENT_ID = "ba17b5f789c7b6f58958"
const val SCOPE_USER = "user"
const val SCOPE_REPO = "repo"
const val STATE_OPEN = "open"
const val STATE_CLOSED = "closed"