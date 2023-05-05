package com.messaging.scrtm.data.trade.repository

import com.auth.CreateOfferByMutation
import com.auth.GetNonceByUserIdQuery
import com.auth.GetPartnerAddressByQuery
import com.auth.GetTradeByPkQuery
import com.auth.type.CreateOfferPayload
import com.messaging.scrtm.data.trade.domain.ApolloTradeClient

interface TradeRepository {
    suspend fun getPartnerAddress(userId: Int):
            GetPartnerAddressByQuery.Data?
    suspend fun createOffer(payload: CreateOfferPayload) : CreateOfferByMutation.Data?

    suspend fun getNonceByUserId(userId: Int): GetNonceByUserIdQuery.Data?

    suspend fun tradeByPK(id: Int) : GetTradeByPkQuery.Data?

}

class TradeRepositoryImp(private val apolloTradeClient: ApolloTradeClient) : TradeRepository {
    override suspend fun getPartnerAddress(userId: Int): GetPartnerAddressByQuery.Data? {
        return apolloTradeClient.getPartnerAddress(userId)
    }

    override suspend fun createOffer(payload: CreateOfferPayload): CreateOfferByMutation.Data? {
        return apolloTradeClient.createOffer(payload)
    }

    override suspend fun getNonceByUserId(userId: Int): GetNonceByUserIdQuery.Data? {
        return apolloTradeClient.getNonceByUserId(userId)
    }

    override suspend fun tradeByPK(id: Int): GetTradeByPkQuery.Data? {
        return apolloTradeClient.tradeByPK(id)    }

}
