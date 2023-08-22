package com.messaging.scrtm.data.solana.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Account(
    val `data`: Data,
    val executable: Boolean,
    val lamports: Int,
    val owner: String,
    val rentEpoch: Int
): Parcelable