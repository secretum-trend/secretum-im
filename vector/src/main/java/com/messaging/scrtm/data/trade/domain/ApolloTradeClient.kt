package com.messaging.scrtm.data.trade.domain

import com.apollographql.apollo3.ApolloClient
import com.auth.GetPartnerAddressByQuery

interface ApolloTradeClient {
    suspend fun getPartnerAddress(userId: Int):GetPartnerAddressByQuery.Data?

}

class ApolloTradeClientImp(private val apolloClient: ApolloClient) : ApolloTradeClient {
    override suspend fun getPartnerAddress(userId: Int):
            GetPartnerAddressByQuery.Data? {
        return apolloClient.query(GetPartnerAddressByQuery(userId))
            .execute().data
    }
}