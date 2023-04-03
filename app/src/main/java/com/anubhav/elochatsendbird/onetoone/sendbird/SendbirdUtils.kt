package com.anubhav.elochatsendbird.onetoone.sendbird

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.sendbird.android.SendbirdChat
import com.sendbird.android.channel.GroupChannel
import com.sendbird.android.channel.MessageTypeFilter
import com.sendbird.android.message.BaseMessage
import com.sendbird.android.params.GroupChannelCreateParams
import com.sendbird.android.params.MessageListParams
import com.sendbird.android.params.OpenChannelCreateParams
import com.sendbird.android.params.UserMessageCreateParams

private const val TAG = "SendbirdUtils"

object SendbirdUtils {

    var myChannel: GroupChannel? = null
    var messageList: List<BaseMessage>? = null

    const val CHANNEL_URL =
        "sendbird_group_channel_386574313_8def16f8c12a4d127a89682dab8cfe92986fcc2e"

    fun connectToServer(userId: String) {
        SendbirdChat.connect(userId) { user, e ->
            if (user != null) {
                if (e != null) {
                    Log.d(TAG, "connectToServer: ${e.message}")
                    // Proceed in offline mode with the data stored in the local database.
                    // Later, connection is made automatically.
                    // and can be notified through ConnectionHandler.onReconnectSucceeded().
                } else {
                    Log.d(TAG, "Connected to Server Successfully!")
                    // Proceed in online mode.
                }
            } else {
                Log.d(TAG, "userNotFound! : ${e?.message}")
                // Handle error.
            }
        }
    }

    private fun findChannel(): GroupChannel? {
        GroupChannel.getChannel(CHANNEL_URL) { channel, exception ->
            exception?.let {
                Log.d(TAG, "findChannelException: ${exception.message} ")
            } ?: Log.d(TAG, "No Exception. Channel Found!")
            myChannel = channel
        }
        return myChannel
    }

    fun sendMessageOnChannel(context: Context, chatMessage: String) {
        val channel = findChannel()
        channel?.sendUserMessage(UserMessageCreateParams(chatMessage)) { message, exception ->
            exception?.let {
                Log.d(TAG, "Exception occurred During Sending a Message: ${exception.message} ")
            } ?: Log.d(TAG, "Message Sent Successfully! : $message ")
            Toast.makeText(context, "Message Sent Successfully!", Toast.LENGTH_SHORT).show()
        } ?: Log.d(TAG, "No Channel Found! ")
    }


    fun joinChannel(groupChannel: GroupChannel) {
        groupChannel.join { exception ->
            exception?.let {
                Log.d(TAG, "joinChannelException: ${exception.message} ")
            } ?: Log.d(TAG, "No Exception. Channel Joined Successfully!")
        }
    }

    fun getMessagesFromChannel(groupChannel: GroupChannel) : List<BaseMessage>{

        val params = MessageListParams().apply {
            previousResultSize = 20
            reverse = false
            messageTypeFilter = MessageTypeFilter.ALL
        }

        groupChannel.getMessagesByTimestamp(Long.MAX_VALUE,params){ list , exception->
            exception?.let {
                Log.d(TAG, "retrieveMessageException: ${exception.message} ")
            } ?: Log.d(TAG, "No Exception. Messages Retrieved Successfully!")
            messageList = list
        }

        return messageList
            ?: emptyList()
    }

}