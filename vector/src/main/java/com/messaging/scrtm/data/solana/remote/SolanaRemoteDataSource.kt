package com.messaging.scrtm.data.solana.remote

import com.messaging.scrtm.data.ApiClient
import com.messaging.scrtm.data.solana.entity.ResponseGetBalance
import com.messaging.scrtm.data.solana.entity.ResponseSolana
import okhttp3.RequestBody

interface SolanaRemoteDataSource {
    suspend fun getTokenAccountsByOwner(requestBody : RequestBody) : ResponseSolana
    suspend fun getBalance(requestBody : RequestBody) : ResponseGetBalance
}

class SolanaRemoteDataSourceImp (private val apiClient: ApiClient) : SolanaRemoteDataSource {
    override suspend fun getTokenAccountsByOwner(requestBody : RequestBody): ResponseSolana {
        return apiClient.getTokenAccountsByOwner(requestBody)
    }

    override suspend fun getBalance(requestBody: RequestBody): ResponseGetBalance {
        return apiClient.getBalance(requestBody)
    }

}