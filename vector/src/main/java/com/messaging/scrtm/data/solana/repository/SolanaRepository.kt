package com.messaging.scrtm.data.solana.repository

import com.messaging.scrtm.data.solana.entity.ResponseGetBalance
import com.messaging.scrtm.data.solana.entity.ResponseSolana
import com.messaging.scrtm.data.solana.remote.SolanaRemoteDataSource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.UUID

interface SolanaRepository {
    suspend fun getTokenAccountsByOwner(address : String) : ResponseSolana
    suspend fun getTokenBalance(address : String) : ResponseGetBalance
    suspend fun getTokenInfo(tokenAddress: String): String
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

    override suspend fun getTokenBalance(address: String) : ResponseGetBalance {
        val json = "{\n" +
                "    \"jsonrpc\": \"2.0\", \"id\": 1,\n" +
                "    \"method\": \"getBalance\",\n" +
                "    \"params\": [\n" +
                "      \"$address\"\n" +
                "    ]\n" +
                "  }"
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
        return solanaRemoteDataSource.getBalance(requestBody)
    }

    override suspend fun getTokenInfo(tokenAddress: String): String {
        val metadataUrl = "https://metadata-api.metaplex.com"

        val httpClient = OkHttpClient()

        val metadataRequest = Request.Builder()
            .url("$metadataUrl/metadata/$tokenAddress")
            .build()
            val metadataResponse = httpClient.newCall(metadataRequest).execute()
            val metadataResponseBody = metadataResponse.body?.string()
            val metadataJson = JSONObject(metadataResponseBody.toString())
            return metadataJson.toString()

    }
}