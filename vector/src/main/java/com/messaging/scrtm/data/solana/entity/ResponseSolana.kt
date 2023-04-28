package com.messaging.scrtm.data.solana.entity

data class ResponseSolana(
    val id: String,
    val jsonrpc: String,
    val result: Result
)