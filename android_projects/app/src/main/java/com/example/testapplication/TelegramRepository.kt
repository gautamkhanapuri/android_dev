package com.example.testapplication

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response

data class ValidTokenUiState(val validToken: Boolean = false)
data class ValidUserUiState(val validUser: Boolean = true)

class TelegramRepository(private val telegramUserApi: TelegramUserApi,
                         private val context: Context)  {

    private val _validTokenUiState = MutableStateFlow(ValidTokenUiState())
    val validTokenUiState: StateFlow<ValidTokenUiState> = _validTokenUiState.asStateFlow()

    private val _validUserUiState = MutableStateFlow(ValidUserUiState())
    val validUserUiState: StateFlow<ValidUserUiState> = _validUserUiState.asStateFlow()

    fun getToken(): String? {
        val forwardPreferences = ForwardPreferences(context)
        return forwardPreferences.getToken()
    }

    fun saveToken(token: String) {
        val forwardPreferences = ForwardPreferences(context)
        return forwardPreferences.saveToken(token)
    }

    fun getAuthHeaders(token: String? = null, contentType:Boolean = false): Map<String, String> {
        val tkn = if (token != null) token else getToken()
        if (tkn != null) {
            if (contentType)
                return mapOf("Authorization" to "Basic $tkn", "Content-Type" to "application/json",  "Accept" to "application/json")
            else
                return mapOf("Authorization" to "Basic $tkn", "Accept" to "application/json")
        } else {
            return mapOf("Accept" to "application/json")
        }
    }

    suspend fun  updateServerStatus(token: String? = null){
        try {
            var valid = false
            val resp: Response<ServerStatus> = telegramUserApi.getServerStatus(getAuthHeaders(token))
            if (resp.body() != null ) {
                val status = resp.body()
                Log.d("FORWARD", "Token validation response: ${status.toString()}")
                valid = status?.status == "OK"
            } else {
                Log.d("FORWARD", "Token validation failed")
            }
            _validTokenUiState.value = ValidTokenUiState(valid)
            if (token != null && valid){
                saveToken(token)
            }
        } catch(ex: Exception) {
            ex.printStackTrace()
        }
    }

    suspend fun validateUser(username: String, email: Boolean){
        if ( username == "") {
            _validUserUiState.value = ValidUserUiState(true)
        } else {
            if (email) {
                _validUserUiState.value = ValidUserUiState(username.contains("@"))
            } else {
                try {
                    val usrName: String = if (username.startsWith("@")) username.slice(1..(username.length-1)) else username
                    var valid = false
                    val resp: Response<TelegramUserStatus> =
                        telegramUserApi.getTelegramUserStatus(usrName, getAuthHeaders(null))
                    if (resp.body() != null) {
                        val status = resp.body()
                        Log.d("FORWARD", "telegram User validation response: ${status.toString()}")
                        valid = status?.status == "OK"
                    } else {
                        Log.d("FORWARD", "Telegram user validation failed")
                    }
                    _validUserUiState.value = ValidUserUiState(valid)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    suspend fun sendMessage(sendMessageBody: SendMessageBody) {
        try {
            val sendMessageBody = SendMessageBody(
                message = sendMessageBody.message,
                from = sendMessageBody.from,
                email = sendMessageBody.email,
                telegram = sendMessageBody.telegram,
            )

            val resp: Response<MessageSent> =
                telegramUserApi.sendMessage(sendMessageBody, getAuthHeaders(null, true))
            if (resp.body() != null) {
                val status = resp.body()
                Log.d("FORWARD", "Send Message to user response: ${status.toString()}")
            } else {
                Log.d("FORWARD", "Send Message to user failed")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

}