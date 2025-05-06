package com.example.testapplication

data class SendMessageBody(
    val email: String,
    val from: String,
    val message: String,
    val telegram: String
)