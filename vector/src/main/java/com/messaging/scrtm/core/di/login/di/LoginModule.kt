package com.messaging.scrtm.core.di.login.di

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.messaging.scrtm.core.di.login.domain.ApolloApolloLoginClientImp
import com.messaging.scrtm.core.di.login.domain.ApolloLoginClient
import com.messaging.scrtm.core.di.login.repository.LoginRepository
import com.messaging.scrtm.core.di.login.repository.LoginRepositoryImp
import com.messaging.scrtm.core.utils.Constants
import com.messaging.scrtm.data.SessionPref
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {


    @Provides
    @Singleton
    fun okhttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY  }
        return OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideApollo(client: OkHttpClient) : ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(Constants.API_URL)
            .okHttpClient(client).build()
    }

    @Provides
    @Singleton
    fun provideRemoteLoginDataSource(apolloClient: ApolloClient) : ApolloLoginClient = ApolloApolloLoginClientImp(apolloClient)

    @Provides
    @Singleton
    fun provideLoginRepository(apolloLoginClient : ApolloLoginClient) : LoginRepository = LoginRepositoryImp(apolloLoginClient)

    @Provides
    @Singleton
    fun provideSessionPref(@ApplicationContext appContext: Context) : SessionPref = SessionPref(appContext)



}