package com.example.messenger.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Conversation::class, Message::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun conversationDao(): ConversationDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "messenger_database"
                )
                .addCallback(DatabaseCallback(context))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.conversationDao(), database.messageDao())
                    }
                }
            }

            suspend fun populateDatabase(conversationDao: ConversationDao, messageDao: MessageDao) {
                // Dodaj przykładowe rozmowy i wiadomości
                val conversations = listOf(
                    Conversation("Anna Nowak", "Cześć, co u Ciebie?", System.currentTimeMillis() - 1000 * 60 * 5),
                    Conversation("Jan Kowalski", "Dzięki za wczoraj!", System.currentTimeMillis() - 1000 * 60 * 60 * 2),
                    Conversation("Ewa Wiśniewska", "OK, będę o 17:00.", System.currentTimeMillis() - 1000 * 60 * 60 * 24)
                )
                conversations.forEach { conversationDao.upsertConversation(it) }

                messageDao.insertMessage(Message(conversationId = "Anna Nowak", text = "Cześć, co u Ciebie?", timestamp = System.currentTimeMillis() - 1000 * 60 * 5, senderName = "Anna Nowak", isSentByUser = false))
                messageDao.insertMessage(Message(conversationId = "Jan Kowalski", text = "Dzięki za wczoraj!", timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 2, senderName = "Jan Kowalski", isSentByUser = false))
                messageDao.insertMessage(Message(conversationId = "Jan Kowalski", text = "Nie ma za co!", timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 1, senderName = "Ja", isSentByUser = true))
            }
        }
    }
}