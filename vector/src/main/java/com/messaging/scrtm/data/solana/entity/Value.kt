package com.messaging.scrtm.data.solana.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Value(
    val account: Account,
    val pubkey: String
) : Parcelable