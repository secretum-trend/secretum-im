package com.messaging.scrtm.data.trade.repository

import com.auth.GetPartnerAddressByQuery
import com.messaging.scrtm.data.trade.domain.ApolloTradeClient

interface TradeRepository {
    suspend fun getPartnerAddress(userId: Int):
            GetPartnerAddressByQuery.Data?
}
class TradeRepositoryImp (private val apolloTradeClient: ApolloTradeClient) : TradeRepository {
    override suspend fun getPartnerAddress(userId: Int): GetPartnerAddressByQuery.Data?{
        return apolloTradeClient.getPartnerAddress(userId)
    }

}
