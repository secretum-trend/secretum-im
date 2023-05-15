package com.messaging.scrtm.trade.eventBus

import com.auth.GetTradeByPkQuery

class TradeEventBus (val offer : GetTradeByPkQuery.Data?, val tradeEventType: TradeEventType )

enum class TradeEventType{
    CANCEL,
    ACCEPT,
    INITIATE,
    CONFIRM
}