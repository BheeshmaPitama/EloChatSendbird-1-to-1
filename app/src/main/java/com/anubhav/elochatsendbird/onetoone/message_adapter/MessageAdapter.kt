package com.anubhav.elochatsendbird.onetoone.message_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.anubhav.elochatsendbird.onetoone.databinding.User1LayoutBinding
import com.anubhav.elochatsendbird.onetoone.databinding.User2LayoutBinding
import com.sendbird.android.message.BaseMessage
import com.sendbird.android.message.UserMessage

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    companion object{
        const val USER_1_MESSAGE = 1
        const val USER_2_MESSAGE = 2
    }

    private val messageList = mutableListOf<BaseMessage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = when(viewType){
            USER_1_MESSAGE -> User1LayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

            USER_2_MESSAGE -> User2LayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

            else -> User1LayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        }

        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val userMessage = messageList[position]
        holder.bind(userMessage.message)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val userMessage = messageList[position]
        userMessage.let {
            if(it is UserMessage){
                when(it.sender?.userId){
                    "123456" -> return USER_1_MESSAGE
                    "789012" -> return USER_2_MESSAGE
                }
            }
        }
        return USER_1_MESSAGE
    }

    fun addMessage(message:BaseMessage){
        this.messageList.apply {
            add(message)
        }
        notifyItemInserted(messageList.size -1)
    }

    class MessageViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: String) {

            when(binding){
                is User1LayoutBinding -> {
                    binding.user1MessageText.text = message
                }

                is User2LayoutBinding -> {
                    binding.user2MessageText.text = message
                }
            }

        }
    }
}