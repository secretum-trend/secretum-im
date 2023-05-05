package com.messaging.scrtm.data.trade.entity

class TradeModel(
    val recipient_address: String,
    val recipient_token_address: String,
    val recipient_token_amount: String,
    val recipient_user_id: Int,
    val sending_token_address: String,
    val sending_token_amount: String,
) : java.io.Serializable
