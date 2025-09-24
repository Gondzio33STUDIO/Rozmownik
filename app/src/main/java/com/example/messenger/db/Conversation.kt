package com.example.messenger.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")
data class Conversation(
    @PrimaryKey
    val contactName: String,
    var lastMessage: String,
    var lastMessageTimestamp: Long
)