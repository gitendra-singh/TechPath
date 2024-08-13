package com.example.techpath.gemini

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig

suspend fun generativeModel(prompt: String) : String {
    val model = GenerativeModel(
        "gemini-1.5-flash",
        "API_key",
        generationConfig = generationConfig {
            temperature = 1f
            topK = 0
            topP = 0.95f
            maxOutputTokens = 8192
        },
        safetySettings = listOf(
            SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE),
        ),
    )

    val chatHistory: List<Content> = listOf(
    )

    val chat = model.startChat(chatHistory)


    val response = chat.sendMessage(prompt)

    return response.text ?: ""

}