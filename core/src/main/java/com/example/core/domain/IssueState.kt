package com.example.core.domain

enum class IssueState {
    CLOSED, OPEN, ALL;

    override fun toString(): String = when (this) {
        CLOSED -> "closed"
        OPEN ->  "open"
        ALL ->  "all"
    }
}