package com.messaging.scrtm.data.trade.repository

import com.auth.*
import com.auth.type.CreateOfferPayload
import com.messaging.scrtm.data.trade.domain.ApolloTradeClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

interface TradeRepository {
    suspend fun getPartnerAddress(userId: Int):
            GetPartnerAddressByQuery.Data?

    suspend fun createOffer(payload: CreateOfferPayload): CreateOfferByMutation.Data?

    suspend fun getNonceByUserId(userId: Int): GetNonceByUserIdQuery.Data?

    fun tradeByPK(id: Int): GetTradeByPkQuery.Data?

    suspend fun cancelOffer(id: Int): CancelOfferMutation.Data?

    suspend fun acceptTrade(id: Int): AcceptTradeMutation.Data?

    suspend fun initializeTrade(id: Int): InitializeTradeMutation.Data?

    suspend fun exchangeTrade(id: Int, signature: String): ExchangeTradeMutation.Data?
    suspend fun getRateByAddress(
        addresses: List<String>
    ): GetRateByAddressesQuery.Data?

}

class TradeRepositoryImp @Inject constructor(private val apolloTradeClient: ApolloTradeClient) :
    TradeRepository {
    override suspend fun getPartnerAddress(userId: Int): GetPartnerAddressByQuery.Data? {
        return apolloTradeClient.getPartnerAddress(userId)
    }

    override suspend fun createOffer(payload: CreateOfferPayload): CreateOfferByMutation.Data? {
        return apolloTradeClient.createOffer(payload)
    }

    override suspend fun getNonceByUserId(userId: Int): GetNonceByUserIdQuery.Data? {
        return apolloTradeClient.getNonceByUserId(userId)
    }


    override fun tradeByPK(id: Int): GetTradeByPkQuery.Data? {
        val deferred = CoroutineScope(Dispatchers.IO).async {
            apolloTradeClient.tradeByPK(id)
        }

        return runBlocking {
            deferred.await()
        }
    }

    override suspend fun cancelOffer(id: Int): CancelOfferMutation.Data? {
        return apolloTradeClient.cancelOffer(id)
    }

    override suspend fun acceptTrade(id: Int): AcceptTradeMutation.Data? {
        return apolloTradeClient.acceptTrade(id)
    }

    override suspend fun initializeTrade(id: Int): InitializeTradeMutation.Data? {
        return apolloTradeClient.initializeTrade(id)
    }

    override suspend fun exchangeTrade(id: Int, signature: String): ExchangeTradeMutation.Data? {
        return apolloTradeClient.exchangeTrade(id, signature)
    }

    override suspend fun getRateByAddress(
        addresses: List<String>
    ): GetRateByAddressesQuery.Data? {
        return apolloTradeClient.getRateByAddress(addresses)
    }
}
