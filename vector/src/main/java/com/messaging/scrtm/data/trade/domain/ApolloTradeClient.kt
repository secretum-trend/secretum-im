package com.messaging.scrtm.data.trade.domain

import com.apollographql.apollo3.ApolloClient
import com.auth.*
import com.auth.type.CreateOfferOutput
import com.auth.type.CreateOfferPayload

interface ApolloTradeClient {
    suspend fun getPartnerAddress(userId: Int):GetPartnerAddressByQuery.Data?

    suspend fun createOffer(payload: CreateOfferPayload) : CreateOfferByMutation.Data?

    suspend fun getNonceByUserId(userId: Int) : GetNonceByUserIdQuery.Data?
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


}