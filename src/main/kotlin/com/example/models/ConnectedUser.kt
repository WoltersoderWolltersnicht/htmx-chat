package com.example.models

import io.ktor.server.websocket.WebSocketServerSession

class ConnectedUser(
    var connection: WebSocketServerSession?,
    val color: String,
    val userName: String
)