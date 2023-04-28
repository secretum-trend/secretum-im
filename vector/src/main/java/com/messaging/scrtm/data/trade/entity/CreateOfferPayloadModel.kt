package com.messaging.scrtm.data.trade.entity

data class CreateOfferPayloadModel(
    val trade: TradeModel,
    val publicKey: String,
    var signature: String,
) : java.io.Serializable