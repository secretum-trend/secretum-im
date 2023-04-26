package com.messaging.scrtm.data.solana.repository

import com.messaging.scrtm.data.solana.entity.ApiResponse
import com.messaging.scrtm.data.solana.entity.TokenAccount
import com.messaging.scrtm.data.solana.remote.SolanaRemoteDataSource
import retrofit2.Response

interface SolanaRepository {
    suspend fun getTokenAccountsByOwner(address : String) : Response<ApiResponse<List<TokenAccount>>>

}

class SolanaRepositoryImp(private val solanaRemoteDataSource: SolanaRemoteDataSource) : SolanaRepository {
    override suspend fun getTokenAccountsByOwner(address: String): Response<ApiResponse<List<TokenAccount>>> {
        return solanaRemoteDataSource.getTokenAccountsByOwner(address)
    }
}