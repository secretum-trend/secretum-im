package com.messaging.scrtm.data.trade.domain

import com.apollographql.apollo3.ApolloClient
import com.auth.*
import com.auth.type.CreateOfferPayload
import com.auth.type.InitializePayload

interface ApolloTradeClient {
    suspend fun getPartnerAddress(userId: Int): GetPartnerAddressByQuery.Data?

    suspend fun createOffer(payload: CreateOfferPayload): CreateOfferByMutation.Data?

    suspend fun getNonceByUserId(userId: Int): GetNonceByUserIdQuery.Data?

    suspend fun tradeByPK(id: Int): GetTradeByPkQuery.Data?

    suspend fun cancelOffer(id: Int): CancelOfferMutation.Data?

    suspend fun acceptTrade(id: Int): AcceptTradeMutation.Data?

    suspend fun initializeTrade(id: Int): InitializeTradeMutation.Data?

    suspend fun exchangeTrade(id: Int, signature: String): ExchangeTradeMutation.Data?
    suspend fun getRateByAddress(
        addresses: List<String>
    ): GetRateByAddressesQuery.Data?

    suspend fun buildInitializeTransaction(
        initializePayload: InitializePayload
    ): BuildInitializeTransactionMutation.Data?

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

    override suspend fun getRateByAddress(
        addresses: List<String>
    ): GetRateByAddressesQuery.Data? {
        return apolloClient.query(GetRateByAddressesQuery()).execute().data
    }

    override suspend fun buildInitializeTransaction(initializePayload: InitializePayload): BuildInitializeTransactionMutation.Data? =
        apolloClient.mutation(BuildInitializeTransactionMutation(initializePayload)).execute().data
}