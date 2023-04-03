package com.anubhav.elochatsendbird.onetoone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anubhav.elochatsendbird.onetoone.databinding.ActivityMainBinding

const val FIRST_USER_ID = "123456"
const val SECOND_USER_ID = "789012"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.apply {
            user1Button.setOnClickListener {
                moveToChatScreen(FIRST_USER_ID)
            }

            user2Button.setOnClickListener {
                moveToChatScreen(SECOND_USER_ID)
            }
        }
    }

    private fun moveToChatScreen(firstUserId: String) {
        val intent = Intent(this,ChatActivity::class.java)

            .apply {
            val bundle = Bundle().apply {
                putString(USER_ID, firstUserId)
            }

            putExtras(bundle)
        }

        startActivity(intent)
    }
}