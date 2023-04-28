package com.messaging.scrtm.data.solana.entity

data class Account(
    val `data`: Data,
    val executable: Boolean,
    val lamports: Int,
    val owner: String,
    val rentEpoch: Int
)