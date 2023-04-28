package com.messaging.scrtm.data.solana.entity

data class Info(
    val isNative: Boolean,
    val mint: String,
    val owner: String,
    val state: String,
    val tokenAmount: TokenAmount
)