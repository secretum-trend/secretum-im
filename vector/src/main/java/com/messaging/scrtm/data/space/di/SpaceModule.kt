package com.messaging.scrtm.data.space.di

import com.apollographql.apollo3.ApolloClient
import com.messaging.scrtm.data.space.domain.ApolloSpaceClient
import com.messaging.scrtm.data.space.domain.ApolloSpaceClientImp
import com.messaging.scrtm.data.space.repository.SpaceRepository
import com.messaging.scrtm.data.space.repository.SpaceRepositoryImp
import com.messaging.scrtm.data.trade.domain.ApolloTradeClient
import com.messaging.scrtm.data.trade.domain.ApolloTradeClientImp
import com.messaging.scrtm.data.trade.repository.TradeRepository
import com.messaging.scrtm.data.trade.repository.TradeRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SpaceModule {

    @Provides
    @Singleton
    fun provideApolloSpaceClient (apolloClient: ApolloClient) : ApolloSpaceClient = ApolloSpaceClientImp(apolloClient)

    @Provides
    @Singleton
    fun provideSpaceRepository(apolloTradeClient: ApolloSpaceClient) : SpaceRepository = SpaceRepositoryImp(apolloTradeClient)
}