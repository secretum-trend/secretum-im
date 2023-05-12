package com.messaging.scrtm.data.trade.domain

import com.apollographql.apollo3.ApolloClient
import com.auth.AcceptTradeMutation
import com.auth.CancelOfferMutation
import com.auth.CreateOfferByMutation
import com.auth.ExchangeTradeMutation
import com.auth.GetNonceByUserIdQuery
import com.auth.GetPartnerAddressByQuery
import com.auth.GetTradeByPkQuery
import com.auth.InitializeTradeMutation
import com.auth.type.CreateOfferPayload

interface ApolloTradeClient {
    suspend fun getPartnerAddress(userId: Int): GetPartnerAddressByQuery.Data?

    suspend fun createOffer(payload: CreateOfferPayload): CreateOfferByMutation.Data?

    suspend fun getNonceByUserId(userId: Int): GetNonceByUserIdQuery.Data?

    suspend fun tradeByPK(id: Int): GetTradeByPkQuery.Data?

    suspend fun cancelOffer(id: Int): CancelOfferMutation.Data?

    suspend fun acceptTrade(id: Int): AcceptTradeMutation.Data?

    suspend fun initializeTrade(id: Int): InitializeTradeMutation.Data?

    suspend fun exchangeTrade(id: Int, signature: String): ExchangeTradeMutation.Data?


}

class ApolloTradeClientImp(private val apolloClient: ApolloClient) : ApolloTradeClient {
    override suspend fun getPartnerAddress(userId: Int):
            GetPartnerAddressByQuery.Data? {
        return apolloClient.query(GetPartnerAddressByQuery(userId))
            .execute().data
    }

    override suspend fun createOffer(payload: CreateOfferPayload): CreateOfferByMutation.Data? {
        return apolloClient.mutation(CreateOfferByMutation(payload)).execute().data
    }

    override suspend fun getNonceByUserId(userId: Int): GetNonceByUserIdQuery.Data? {
        return apolloClient.query(GetNonceByUserIdQuery(userId)).execute().data
    }

    override suspend fun tradeByPK(id: Int): GetTradeByPkQuery.Data? {
        return apolloClient.query(GetTradeByPkQuery(id)).execute().data
    }

    override suspend fun cancelOffer(id: Int): CancelOfferMutation.Data? {
        return apolloClient.mutation(CancelOfferMutation(id)).execute().data
    }

    override suspend fun acceptTrade(id: Int): AcceptTradeMutation.Data? {
        return apolloClient.mutation(AcceptTradeMutation(id)).execute().data
    }

    override suspend fun initializeTrade(id: Int): InitializeTradeMutation.Data? {
        return apolloClient.mutation(InitializeTradeMutation(id)).execute().data
    }

    override suspend fun exchangeTrade(id: Int, signature: String): ExchangeTradeMutation.Data? {
        return apolloClient.mutation(ExchangeTradeMutation(id, signature)).execute().data
    }
}