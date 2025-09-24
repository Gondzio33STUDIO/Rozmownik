package com.example.messenger.models

data class Message(
    val text: String,
    val timestamp: Long,
    val senderName: String,
    val isSentByUser: Boolean
)