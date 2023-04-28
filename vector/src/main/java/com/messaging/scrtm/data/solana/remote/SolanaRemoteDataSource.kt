package com.messaging.scrtm.data.solana.remote

import com.messaging.scrtm.data.ApiClient
import com.messaging.scrtm.data.solana.entity.ApiResponse
import com.messaging.scrtm.data.solana.entity.ResponseSolana
import com.messaging.scrtm.data.solana.entity.TokenAccount
import okhttp3.RequestBody
import retrofit2.Response

interface SolanaRemoteDataSource {
    suspend fun getTokenAccountsByOwner(requestBody : RequestBody) : ResponseSolana
}

class SolanaRemoteDataSourceImp (private val apiClient: ApiClient) : SolanaRemoteDataSource {
    override suspend fun getTokenAccountsByOwner(requestBody : RequestBody): ResponseSolana {
        return apiClient.getTokenAccountsByOwner(requestBody)
    }

}