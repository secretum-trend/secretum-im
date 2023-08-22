package com.messaging.scrtm.data.solana.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val parsed: Parsed,
    val program: String,
    val space: Int
): Parcelable