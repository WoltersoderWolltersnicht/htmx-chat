package com.example.ui

import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.script
import kotlinx.html.title

fun HTML.index() {
    head {
        title = "gm-companion"
        script(src = "https://unpkg.com/htmx.org@2.0.2") { type = "text/javascript" }
        script(src = "https://unpkg.com/htmx.org@1.9.12/dist/ext/ws.js") { type = "text/javascript" }
        script(src = "https://cdn.tailwindcss.com") {}
    }
    body {
        div {
            id = "content"
            div {
                attributes["hx-get"] = "/login"
                attributes["hx-target"] = "#content"
                attributes["hx-swap"] = "innerHTML"
                attributes["hx-trigger"] = "load"
            }
        }
    }
}