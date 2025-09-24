package com.example.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.adapters.ConversationsAdapter
import com.example.messenger.models.Conversation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val conversationsRecyclerView: RecyclerView = findViewById(R.id.conversationsRecyclerView)
        conversationsRecyclerView.layoutManager = LinearLayoutManager(this)

        val sampleConversations = createSampleData()
        val adapter = ConversationsAdapter(sampleConversations)
        conversationsRecyclerView.adapter = adapter
    }

    private fun createSampleData(): List<Conversation> {
        return listOf(
            Conversation("Anna Nowak", "Cześć, co u Ciebie?", System.currentTimeMillis() - 1000 * 60 * 5),
            Conversation("Jan Kowalski", "Dzięki za wczoraj!", System.currentTimeMillis() - 1000 * 60 * 60 * 2),
            Conversation("Ewa Wiśniewska", "OK, będę o 17:00.", System.currentTimeMillis() - 1000 * 60 * 60 * 24),
            Conversation("Piotr Zając", "Widziałeś ten nowy film?", System.currentTimeMillis() - 1000 * 60 * 60 * 48),
            Conversation("Mama", "Zadzwoń jak będziesz mógł.", System.currentTimeMillis() - 1000 * 60 * 60 * 72)
        )
    }
}