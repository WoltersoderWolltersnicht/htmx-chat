package com.example.plugins

import com.example.models.ConnectedUser
import com.example.models.MessageResponse
import com.example.ui.components.message
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.channels.consumeEach
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.stream.createHTML
import kotlinx.serialization.json.Json
import java.time.Duration
import kotlin.collections.set

fun Application.configureSocket(connections: MutableMap<String, ConnectedUser>) {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Json { ignoreUnknownKeys = true })
        pingPeriod = Duration.ofSeconds(30)
        timeout = Duration.ofSeconds(30)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    routing {
        webSocket("/messages") {
            var userId = call.request.queryParameters["userId"]
            var connection = connections[userId]
            if (connection == null) return@webSocket

            connection?.connection = this
            var json = Json { ignoreUnknownKeys = true }

            runCatching {
                incoming.consumeEach { frame ->
                    if (frame is Frame.Text) {
                        val receivedText = frame.readText()
                        val messageResponse = json.decodeFromString<MessageResponse>(receivedText)
                        var message = createHTML().div {
                            id = "chatRoom"
                            attributes["hx-swap-oob"] = "beforeend"
                            message(messageResponse, connection)
                        }

                        for (session in connections) {
                            session.value.connection?.send(message)
                        }
                    }
                }
            }.onFailure { exception ->
                println("WebSocket exception: ${exception.localizedMessage}")
            }.also {
                connection.connection?.close()
                connections.remove(userId)
            }
        }
    }
}