package com.example

import com.example.models.ConnectedUser
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.util.Collections

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        watchPaths = listOf("gradleproject"),
        module = Application::module,
        port = 8080,
    ).start(wait = true)
}

fun Application.module() {
    var connections: MutableMap<String, ConnectedUser> =
        Collections.synchronizedMap<String, ConnectedUser>(mutableMapOf<String, ConnectedUser>())
    configureSocket(connections)
    configureRouting(connections)
}
