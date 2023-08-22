package com.messaging.scrtm.core.utils

import com.messaging.scrtm.data.solana.entity.Value
import kotlin.math.pow

object SolanaUtils {
     fun Value.isNFT(): Boolean {
        val supply = this.account.data.parsed.info.tokenAmount.uiAmount
        val decimals = this.account.data.parsed.info.tokenAmount.decimals
        return supply / 10.0.pow(decimals.toDouble()) == 1.0
    }
}