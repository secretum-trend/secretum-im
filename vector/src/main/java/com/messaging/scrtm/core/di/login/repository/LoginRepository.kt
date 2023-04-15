package com.messaging.scrtm.core.di.login.repository

import com.auth.GetNonceByQuery
import com.auth.JoinPublicKeyToWhitelistMutation
import com.auth.VerifySignatureMutation
import com.messaging.scrtm.core.di.login.domain.ApolloLoginClient
import javax.inject.Inject

interface LoginRepository {
    suspend fun getNonce(publicKey: String): GetNonceByQuery.GetNonce?
    suspend fun joinWhitelistFree(publicKey: String): JoinPublicKeyToWhitelistMutation.JoinWhitelistFree?
    suspend fun authenticate(
        publicKey: String,
        signature: String
    ): VerifySignatureMutation.Authenticate?
}

class LoginRepositoryImp @Inject constructor(private val apolloLoginClient: ApolloLoginClient) :
    LoginRepository {
    override suspend fun getNonce(publicKey: String): GetNonceByQuery.GetNonce? =
        apolloLoginClient.getNonce(publicKey)

    override suspend fun joinWhitelistFree(publicKey: String): JoinPublicKeyToWhitelistMutation.JoinWhitelistFree? =
        apolloLoginClient.joinWhitelistFree(publicKey)

    override suspend fun authenticate(
        publicKey: String,
        signature: String
    ): VerifySignatureMutation.Authenticate? =
        apolloLoginClient.authenticate(publicKey, signature)

}