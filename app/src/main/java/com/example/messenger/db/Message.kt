package com.example.messenger.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "messages",
        foreignKeys = [ForeignKey(entity = Conversation::class,
                                  parentColumns = ["contactName"],
                                  childColumns = ["conversationId"],
                                  onDelete = ForeignKey.CASCADE)])
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val conversationId: String, // Klucz obcy
    val text: String,
    val timestamp: Long,
    val senderName: String,
    val isSentByUser: Boolean
)