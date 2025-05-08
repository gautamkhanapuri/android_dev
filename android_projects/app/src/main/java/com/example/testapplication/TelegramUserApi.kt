package com.example.testapplication

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path

interface TelegramUserApi {


    @GET(value="${AppConstants.PATHUSER}/{username}")
    suspend fun getTelegramUserStatus(@Path("username")username: String,
                                      @HeaderMap hdrs: Map<String, String>):
            Response<ApiResponse>

    @GET(value= AppConstants.PATHVERSION)
    suspend fun getServerStatus(@HeaderMap hdrs: Map<String, String>):
            Response<ApiResponse>

    @POST(value= AppConstants.PATHMESSAGE)
    suspend fun sendMessage(@Body sendMessageBody: SendMessageBody,
                            @HeaderMap hdrs: Map<String, String> ):
            Response<ApiResponse>
}