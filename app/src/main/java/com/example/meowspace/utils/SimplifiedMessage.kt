package com.example.meowspace.utils

import org.json.JSONException
import org.json.JSONObject

object SimplifiedMessage {
    fun get(stringMessage: String): HashMap<String, String> {
        val messages = HashMap<String, String>()
        val jsonObject = JSONObject(stringMessage)

        try {
            val jsonMessages = jsonObject.getJSONObject("messages")
            jsonMessages.keys().forEach { messages[it] = jsonMessages.getString(it) }
        } catch (e: JSONException) {
        }

        return messages
    }
}
