package com.example.messenger

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.adapters.MessagesAdapter
import com.example.messenger.db.AppDatabase
import com.example.messenger.db.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var contactName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        contactName = intent.getStringExtra("CONTACT_NAME") ?: "Rozmowa"
        supportActionBar?.title = contactName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        messagesRecyclerView = findViewById(R.id.messagesRecyclerView)
        val messageEditText: EditText = findViewById(R.id.messageEditText)
        val sendButton: Button = findViewById(R.id.sendButton)

        // Inicjalizacja adaptera
        messagesAdapter = MessagesAdapter(emptyList())
        messagesRecyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        messagesRecyclerView.adapter = messagesAdapter

        // Pobranie DAO
        val messageDao = AppDatabase.getDatabase(applicationContext).messageDao()
        val conversationDao = AppDatabase.getDatabase(applicationContext).conversationDao()

        // Obserwowanie wiadomości dla tej konwersacji
        lifecycleScope.launch {
            messageDao.getMessagesForConversation(contactName).collectLatest { messages ->
                messagesAdapter.updateData(messages)
                messagesRecyclerView.scrollToPosition(messages.size - 1)
            }
        }

        sendButton.setOnClickListener {
            val messageText = messageEditText.text.toString()
            if (messageText.isNotBlank()) {
                val newMessage = Message(
                    conversationId = contactName,
                    text = messageText,
                    timestamp = System.currentTimeMillis(),
                    senderName = "Ja",
                    isSentByUser = true
                )

                lifecycleScope.launch(Dispatchers.IO) {
                    // Zapisz nową wiadomość i zaktualizuj konwersację
                    messageDao.insertMessage(newMessage)
                    conversationDao.updateLastMessage(contactName, messageText, newMessage.timestamp)
                }
                messageEditText.text.clear()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}