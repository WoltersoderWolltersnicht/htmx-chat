package com.example.ui.components

import com.example.models.ConnectedUser
import com.example.models.MessageResponse
import kotlinx.html.DIV
import kotlinx.html.div
import kotlinx.html.p
import kotlinx.html.span
import kotlinx.html.style

fun DIV.message(message: MessageResponse, user: ConnectedUser) {
    div {
        span {
            style = "color : ${user.color};"
            +user.userName
        }
        span { text(" : ") }
        span { text(message.message) }
        p {}
    }
}