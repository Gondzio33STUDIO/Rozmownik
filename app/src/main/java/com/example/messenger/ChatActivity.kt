package com.example.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.adapters.MessagesAdapter
import com.example.messenger.models.Message

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val contactName = intent.getStringExtra("CONTACT_NAME") ?: "Rozmowa"
        supportActionBar?.title = contactName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val messagesRecyclerView: RecyclerView = findViewById(R.id.messagesRecyclerView)
        messagesRecyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }

        val sampleMessages = createSampleMessages(contactName)
        val adapter = MessagesAdapter(sampleMessages)
        messagesRecyclerView.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun createSampleMessages(contactName: String): List<Message> {
        return listOf(
            Message("Cześć!", System.currentTimeMillis() - 1000 * 60 * 10, contactName, false),
            Message("Hej, co tam u Ciebie, $contactName?", System.currentTimeMillis() - 1000 * 60 * 9, "Ja", true),
            Message("Wszystko dobrze, a u Ciebie?", System.currentTimeMillis() - 1000 * 60 * 8, contactName, false),
            Message("Też ok. Dzięki za wczoraj!", System.currentTimeMillis() - 1000 * 60 * 7, "Ja", true),
            Message("Nie ma sprawy :)", System.currentTimeMillis() - 1000 * 60 * 6, contactName, false)
        )
    }
}