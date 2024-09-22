package com.example.ui.pages

import kotlinx.html.ButtonType
import kotlinx.html.DIV
import kotlinx.html.InputType
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.script
import kotlinx.html.unsafe

fun DIV.chat(ID: String) {
    div {
        attributes["hx-ext"] = "ws"
        attributes["ws-connect"] = "/messages?userId=$ID"
        classes = setOf("flex", "flex-col", "h-screen")

        div {
            id = "chatRoom"
            classes = setOf("flex-1", "overflow-scroll")
        }

        form {
            id = "form"
            classes = setOf("bottom-0 w-full bg-white flex p-3 gap-2")
            attributes["ws-send"] = ""

            input(classes = "grow bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500") {
                id = "messageInput"
                type = InputType.text
                name = "message"
            }
            input()
            {
                id = "user"
                type = InputType.hidden
                name = "user"
                value = ID
            }
            button(
                classes = "text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800",
            ) {
                type = ButtonType.submit
                text("Send")
            }
        }

        script {
            unsafe {
                +"""
            document.addEventListener("htmx:wsAfterMessage", e => {
                const messagesDiv = document.getElementById("chatRoom");
                messagesDiv.scrollTop = messagesDiv.scrollHeight;
            })
            
            document.addEventListener("htmx:wsBeforeSend", e => {
                const messageInput = document.getElementById("messageInput");
                messageInput.value='';
            })
            """
            }
        }
    }
}