package com.example.plugins

import com.example.models.ConnectedUser
import com.example.ui.index
import com.example.ui.pages.chat
import com.example.ui.pages.login
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.id
import java.util.Random
import java.util.UUID

fun Application.configureRouting(connections: MutableMap<String, ConnectedUser>) {
    routing {
        //staticResources("/assets", "assets")

        get("/") {
            call.respondHtml(HttpStatusCode.OK) {
                index()
            }
        }

        get("/login") {
            call.respondHtml(HttpStatusCode.OK) {
                body {
                    div {
                        id = "content"
                        login()
                    }
                }
            }
        }

        get("/chat") {
            val user = call.request.queryParameters["user"]
            var userId = UUID.randomUUID().toString()
            var color = generateRandomColor()
            connections[userId] = ConnectedUser(null, color, user.toString())
            call.respondHtml(HttpStatusCode.OK) {
                body {
                    div {
                        id = "content"
                        chat(userId)
                    }
                }
            }
        }
    }
}

fun generateRandomColor(): String {
    var random = Random()
    var nextInt = random.nextInt(0xffffff + 1)
    var colorCode: String = String.format("#%06x", nextInt)
    return colorCode
}