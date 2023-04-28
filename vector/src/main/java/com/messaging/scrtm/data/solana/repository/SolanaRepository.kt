package com.messaging.scrtm.data.solana.repository

import com.messaging.scrtm.data.solana.entity.ApiResponse
import com.messaging.scrtm.data.solana.entity.ResponseSolana
import com.messaging.scrtm.data.solana.entity.TokenAccount
import com.messaging.scrtm.data.solana.remote.SolanaRemoteDataSource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.util.*

interface SolanaRepository {
    suspend fun getTokenAccountsByOwner(address : String) : ResponseSolana

}

class SolanaRepositoryImp(private val solanaRemoteDataSource: SolanaRemoteDataSource) : SolanaRepository {
    override suspend fun getTokenAccountsByOwner(address : String): ResponseSolana {
        val uniqueID: String = UUID.randomUUID().toString()
        val json = "{\n" +
                "    \"method\": \"getTokenAccountsByOwner\",\n" +
                "    \"jsonrpc\": \"2.0\",\n" +
                "    \"params\": [\n" +
                "        \"$address\",\n" +
                "        {\n" +
                "            \"programId\": \"TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"encoding\": \"jsonParsed\"\n" +
                "        }                                                                                                                                                                                                                  \n" +
                "    ],\n" +
                "    \"id\": \"$uniqueID\"\n" +
                "}"
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
        return solanaRemoteDataSource.getTokenAccountsByOwner(requestBody)
    }
}