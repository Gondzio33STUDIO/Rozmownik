package com.example.messenger.models

data class Conversation(
    val contactName: String,
    val lastMessage: String,
    val lastMessageTimestamp: Long
)