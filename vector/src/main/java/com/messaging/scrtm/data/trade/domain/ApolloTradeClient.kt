package com.messaging.scrtm.data.trade.domain

import com.apollographql.apollo3.ApolloClient
import com.auth.*
import com.auth.type.CancelPayload
import com.auth.type.CreateOfferPayload
import com.auth.type.ExchangePayload
import com.auth.type.InitializePayload
import com.google.gson.Gson
import org.json.JSONObject

interface ApolloTradeClient {
    suspend fun getPartnerAddress(userId: Int): GetPartnerAddressByQuery.Data?

    suspend fun createOffer(payload: CreateOfferPayload): CreateOfferByMutation.Data?

    suspend fun getNonceByUserId(userId: Int): GetNonceByUserIdQuery.Data?

    suspend fun tradeByPK(id: Int): GetTradeByPkQuery.Data?

    suspend fun cancelOffer(id: Int): CancelOfferMutation.Data?

    suspend fun cancelTransaction(
        id: Int,
        signature: String
    ): Pair<CancelTransactionMutation.Data?, String?>

    suspend fun acceptTrade(id: Int): AcceptTradeMutation.Data?

    suspend fun initializeTrade(id: Int): InitializeTradeMutation.Data?

    suspend fun exchangeTrade(id: Int, signature: String): ExchangeTradeMutation.Data?

    suspend fun getRateByAddress(
        addresses: List<String>
    ): GetRateByAddressesQuery.Data?

    suspend fun buildInitializeTransaction(
        initializePayload: InitializePayload
    ): Pair<BuildInitializeTransactionMutation.Data?, String?>

    suspend fun buildExchangeTransaction(
        exchange: ExchangePayload
    ): Pair<BuildExchangeTradeTransactionMutation.Data?, String?>

    suspend fun buildCancelTransaction(
        cancelPayload: CancelPayload
    ): Pair<BuildCancelTradeTransactionMutation.Data?, String?>
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

    override suspend fun cancelTransaction(
        id: Int,
        signature: String
    ): Pair<CancelTransactionMutation.Data?, String?> {
        val response = apolloClient.mutation(CancelTransactionMutation(id, signature)).execute()
        return Pair(response.data, response.errors?.get(0)?.message)
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

    override suspend fun buildInitializeTransaction(initializePayload: InitializePayload): Pair<BuildInitializeTransactionMutation.Data?, String?> {
        val response = apolloClient.mutation(BuildInitializeTransactionMutation(initializePayload)).execute()
        return Pair(response.data, parseErrorMessage(Gson().toJson(response)))
    }

    override suspend fun buildExchangeTransaction(exchange: ExchangePayload): Pair<BuildExchangeTradeTransactionMutation.Data?, String?> {
        val response = apolloClient.mutation(BuildExchangeTradeTransactionMutation(exchange)).execute()
        return Pair(response.data, parseErrorMessage(Gson().toJson(response)))
    }


    override suspend fun buildCancelTransaction(cancelPayload: CancelPayload):Pair<BuildCancelTradeTransactionMutation.Data?, String?>  {
        val response = apolloClient.mutation(BuildCancelTradeTransactionMutation(cancelPayload)).execute()
        return Pair(response.data, parseErrorMessage(Gson().toJson(response)))
    }



    private fun parseErrorMessage(jsonString: String): String? {
        try {
            val json = JSONObject(jsonString)
            val errorsArray = json.getJSONArray("errors")
            if (errorsArray.length() > 0) {
                val error = errorsArray.getJSONObject(0)
                val extensions = error.getJSONObject("extensions")
                val internal = extensions.getJSONObject("internal")
                val response = internal.getJSONObject("response")
                val body = response.getJSONObject("body")
                return body.getString("message")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}