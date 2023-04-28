package com.messaging.scrtm.data

import com.messaging.scrtm.data.solana.entity.ApiResponse
import com.messaging.scrtm.data.solana.entity.ResponseSolana
import com.messaging.scrtm.data.solana.entity.TokenAccount
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiClient {
    @POST("/")
    suspend fun getTokenAccountsByOwner(@Body requestBody : RequestBody): ResponseSolana
}