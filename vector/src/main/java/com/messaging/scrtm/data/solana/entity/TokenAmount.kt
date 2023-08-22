package com.messaging.scrtm.data.solana.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokenAmount(
    val amount: String,
    val decimals: Int,
    val uiAmount: Double,
    val uiAmountString: String
): Parcelable