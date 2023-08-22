package com.messaging.scrtm.data.solana.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Context(
    val apiVersion: String,
    val slot: Int
): Parcelable