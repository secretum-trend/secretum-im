package com.messaging.scrtm.data.solana.entity

class ResponseGetBalance(
    val id: String,
    val jsonrpc: String,
    val result: ResultBalance
)

data class ResultBalance(
    val context: Context,
    val value: Int
)

