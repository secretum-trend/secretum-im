package com.messaging.scrtm.data.solana.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val context: Context,
    val value: List<Value>
): Parcelable