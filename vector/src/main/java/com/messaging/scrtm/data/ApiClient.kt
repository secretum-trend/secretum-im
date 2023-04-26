package com.messaging.scrtm.data

import com.messaging.scrtm.data.solana.entity.ApiResponse
import com.messaging.scrtm.data.solana.entity.TokenAccount
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {
    @GET("/token%20accounts/{address}")
    suspend fun getTokenAccountsByOwner(@Path("address") address: String): Response<ApiResponse<List<TokenAccount>>>
}