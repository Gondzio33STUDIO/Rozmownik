package com.example.messenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.adapters.ConversationsAdapter
import com.example.messenger.db.AppDatabase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var conversationsAdapter: ConversationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val conversationsRecyclerView: RecyclerView = findViewById(R.id.conversationsRecyclerView)
        conversationsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Inicjalizacja adaptera z pustą listą
        conversationsAdapter = ConversationsAdapter(emptyList())
        conversationsRecyclerView.adapter = conversationsAdapter

        // Pobranie instancji DAO
        val conversationDao = AppDatabase.getDatabase(applicationContext).conversationDao()

        // Obserwowanie zmian w bazie danych
        lifecycleScope.launch {
            conversationDao.getAllConversations().collectLatest { conversations ->
                conversationsAdapter.updateData(conversations)
            }
        }
    }
}