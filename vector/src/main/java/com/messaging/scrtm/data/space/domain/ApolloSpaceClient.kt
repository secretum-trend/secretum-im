package com.messaging.scrtm.data.space.domain

import com.apollographql.apollo3.ApolloClient
import com.auth.GetSpaceWhiteListQuery
import javax.inject.Inject


interface ApolloSpaceClient {
    suspend fun getSpaceWhiteList(): GetSpaceWhiteListQuery.Data?

}

class ApolloSpaceClientImp @Inject constructor(private val apolloClient: ApolloClient) : ApolloSpaceClient {
    override suspend fun getSpaceWhiteList(): GetSpaceWhiteListQuery.Data?  = apolloClient.query(GetSpaceWhiteListQuery()).execute().data
}