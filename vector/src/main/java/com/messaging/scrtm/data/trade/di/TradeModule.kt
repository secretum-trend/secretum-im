package com.messaging.scrtm.data.trade.di

import com.apollographql.apollo3.ApolloClient
import com.messaging.scrtm.core.di.login.domain.ApolloLoginClient
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
class TradeModule {


    @Provides
    @Singleton
    fun provideApolloTradeClient (apolloClient: ApolloClient) : ApolloTradeClient = ApolloTradeClientImp(apolloClient)

    @Provides
    @Singleton
    fun provideTradeRepository(apolloTradeClient: ApolloTradeClient) : TradeRepository = TradeRepositoryImp(apolloTradeClient)

}