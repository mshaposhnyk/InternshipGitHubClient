package com.example.core.domain

import java.io.Serializable

enum class IssueState : Serializable {
    CLOSED, OPEN, ALL;

    override fun toString(): String = when (this) {
        CLOSED -> "closed"
        OPEN ->  "open"
        ALL ->  "all"
    }
}