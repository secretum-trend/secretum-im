package com.messaging.scrtm.data.trade.domain

import com.apollographql.apollo3.ApolloClient
import com.auth.GetNonceByQuery
import com.messaging.scrtm.data.trade.entity.Wallets
import org.matrix.android.sdk.api.session.user.model.User

//interface ApolloTradeClient {
//    fun getPartnerAddress(userId: String) : Wallets
//}
//
//class ApolloTradeClientImp(private val apolloClient: ApolloClient) : ApolloTradeClient {
//    override fun getPartnerAddress(userId: String): Wallets {
//        return apolloClient.query(GetNonceByQuery.GetNonce(userId)).execute().data as Wallets
//    }
//}