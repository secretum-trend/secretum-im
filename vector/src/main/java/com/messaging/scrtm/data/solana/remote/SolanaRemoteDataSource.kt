package com.messaging.scrtm.data.solana.remote

import com.messaging.scrtm.data.ApiClient
import com.messaging.scrtm.data.solana.entity.ApiResponse
import com.messaging.scrtm.data.solana.entity.TokenAccount
import retrofit2.Response

interface SolanaRemoteDataSource {
    suspend fun getTokenAccountsByOwner(address : String) : Response<ApiResponse<List<TokenAccount>>>
}

class SolanaRemoteDataSourceImp (private val apiClient: ApiClient) : SolanaRemoteDataSource {
    override suspend fun getTokenAccountsByOwner(address: String): Response<ApiResponse<List<TokenAccount>>> {
        return apiClient.getTokenAccountsByOwner(address)
    }

}