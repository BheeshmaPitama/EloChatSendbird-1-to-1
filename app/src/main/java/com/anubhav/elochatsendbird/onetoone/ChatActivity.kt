package com.anubhav.elochatsendbird.onetoone

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anubhav.elochatsendbird.onetoone.databinding.ActivityChatBinding
import com.anubhav.elochatsendbird.onetoone.message_adapter.MessageAdapter
import com.anubhav.elochatsendbird.onetoone.sendbird.SendbirdUtils
import com.sendbird.android.SendbirdChat
import com.sendbird.android.channel.BaseChannel
import com.sendbird.android.channel.GroupChannel
import com.sendbird.android.handler.GroupChannelHandler
import com.sendbird.android.message.AdminMessage
import com.sendbird.android.message.BaseMessage
import com.sendbird.android.message.UserMessage


const val USER_ID = "USER_ID"
private const val TAG = "ChatActivity"

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private var currentUserId: String? = null
    private val messageAdapter: MessageAdapter by lazy { MessageAdapter() }

    //private val messageList = mutableListOf<BaseMessage>()
    private var currentChannel: GroupChannel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {
            currentUserId = bundle.getString(USER_ID)
        }

        //messageAdapter = MessageAdapter()

        binding.messageRecyclerView.apply {
            adapter = messageAdapter
            setHasFixedSize(true)
        }

        startSendbirdFlow()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.apply {
            sendButton.setOnClickListener {
                if (messageEditText.text.isNullOrEmpty()) {
                    Toast.makeText(this@ChatActivity, "Please Write Something!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val chatMessage = messageEditText.text.toString()
                    SendbirdUtils.sendMessageOnChannel(this@ChatActivity, chatMessage)
                }
            }
        }
    }

    private fun startSendbirdFlow() {
        with(SendbirdUtils) {
            currentUserId?.let {
                connectToServer(it)
            }
        }
        addSendbirdChatHandler()
    }


    private fun addSendbirdChatHandler() {
        SendbirdChat.addChannelHandler(
            "696969",
            object : GroupChannelHandler() {
                override fun onMessageReceived(channel: BaseChannel, message: BaseMessage) {
                    when (message) {
                        is UserMessage -> {
                            Toast.makeText(this@ChatActivity, message.message, Toast.LENGTH_SHORT)
                                .show()

                            addMessage(message)
                        }

                        is AdminMessage -> {
                            Toast.makeText(this@ChatActivity, message.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        )
    }

    private fun addMessage(message: BaseMessage) {
        messageAdapter.addMessage(message)
    }

}