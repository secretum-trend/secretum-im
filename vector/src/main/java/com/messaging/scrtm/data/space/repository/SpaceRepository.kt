package com.messaging.scrtm.data.space.repository

import com.messaging.scrtm.data.space.domain.ApolloSpaceClient
import com.messaging.scrtm.data.trade.domain.ApolloTradeClient
import javax.inject.Inject

interface SpaceRepository {
}

class SpaceRepositoryImp @Inject constructor(private val apolloTradeClient: ApolloSpaceClient) : SpaceRepository {

}