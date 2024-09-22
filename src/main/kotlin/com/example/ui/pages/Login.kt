package com.example.ui.pages

import kotlinx.html.ButtonType
import kotlinx.html.DIV
import kotlinx.html.InputType
import kotlinx.html.button
import kotlinx.html.form
import kotlinx.html.id
import kotlinx.html.input

fun DIV.login() {

    form {
        attributes["hx-get"] = "/chat"
        attributes["hx-target"] = "#content"
        attributes["hx-push-url"] = "/chat"
        attributes["hx-swap"] = "innerHTML"

        input {
            id = "userInput"
            type = InputType.text
            name = "user"
        }
        button {
            type = ButtonType.submit
            text("Login")
        }
    }
}
