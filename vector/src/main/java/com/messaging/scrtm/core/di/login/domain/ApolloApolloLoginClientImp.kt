package com.messaging.scrtm.core.di.login.domain

import com.apollographql.apollo3.ApolloClient
import com.auth.GetNonceByQuery
import com.auth.JoinPublicKeyToWhitelistMutation
import com.auth.VerifySignatureMutation

interface ApolloLoginClient {
    suspend fun getNonce(publicKey: String): GetNonceByQuery.GetNonce?
    suspend fun joinWhitelistFree(publicKey: String) : JoinPublicKeyToWhitelistMutation.JoinWhitelistFree?
    suspend fun authenticate(publicKey: String, signature : String) : VerifySignatureMutation.Authenticate?
}

class ApolloApolloLoginClientImp(private val apolloClient: ApolloClient) : ApolloLoginClient {
    override suspend fun getNonce(publicKey: String): GetNonceByQuery.GetNonce? {
        return apolloClient.query(GetNonceByQuery(publicKey)).execute().data?.getNonce
    }

    override suspend fun joinWhitelistFree(publicKey: String): JoinPublicKeyToWhitelistMutation.JoinWhitelistFree? =
        apolloClient.mutation(JoinPublicKeyToWhitelistMutation(publicKey)).execute().data?.joinWhitelistFree

    override suspend fun authenticate(
        publicKey: String,
        signature: String
    ): VerifySignatureMutation.Authenticate? {
        return apolloClient.mutation(VerifySignatureMutation(publicKey, signature)).execute().data?.authenticate
    }

}