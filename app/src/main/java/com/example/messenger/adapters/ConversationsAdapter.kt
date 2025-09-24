package com.example.messenger.adapters

package com.example.messenger.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.ChatActivity
import com.example.messenger.R
import com.example.messenger.models.Conversation
import java.text.SimpleDateFormat
import java.util.*

class ConversationsAdapter(private val conversations: List<Conversation>) :
    RecyclerView.Adapter<ConversationsAdapter.ConversationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_conversation, parent, false)
        return ConversationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        val conversation = conversations[position]
        holder.bind(conversation)
    }

    override fun getItemCount(): Int = conversations.size

    inner class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contactNameTextView: TextView = itemView.findViewById(R.id.contactNameTextView)
        private val lastMessageTextView: TextView = itemView.findViewById(R.id.lastMessageTextView)
        private val lastMessageTimestampTextView: TextView = itemView.findViewById(R.id.lastMessageTimestampTextView)

        fun bind(conversation: Conversation) {
            contactNameTextView.text = conversation.contactName
            lastMessageTextView.text = conversation.lastMessage
            lastMessageTimestampTextView.text = SimpleDateFormat("HH:mm", Locale.getDefault())
                .format(Date(conversation.lastMessageTimestamp))

            itemView.setOnClickListener {
                val context = it.context
                val intent = Intent(context, ChatActivity::class.java).apply {
                    putExtra("CONTACT_NAME", conversation.contactName)
                }
                context.startActivity(intent)
            }
        }
    }
}