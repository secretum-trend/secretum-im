package com.messaging.scrtm.trade.eventBus

import com.auth.GetTradeByPkQuery
import org.matrix.android.sdk.api.session.events.model.Event

class TradeEventBus (val offer : GetTradeByPkQuery.Data?, val tradeEventType: TradeEventType, val event: Event?)

enum class TradeEventType{
    CANCEL,
    ACCEPT,
    INITIATE,
    CONFIRM,
    CREATE_TRADE
}