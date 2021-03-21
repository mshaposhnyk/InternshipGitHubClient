package com.example.core.domain

enum class IssueState {
    CLOSED, OPEN, ALL;

    override fun toString(): String {
        var stringState = ""
        when (this) {
            CLOSED -> stringState = "closed"
            OPEN -> stringState = "open"
            ALL -> stringState = "all"
        }
        return stringState
    }
}