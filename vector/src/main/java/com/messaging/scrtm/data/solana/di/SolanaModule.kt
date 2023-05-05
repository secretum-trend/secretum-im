package com.messaging.scrtm.data.solana.di

import com.messaging.scrtm.BuildConfig
import com.messaging.scrtm.core.di.login.di.AuthorizationInterceptor
import com.messaging.scrtm.core.di.login.di.InterceptorSolana
import com.messaging.scrtm.data.ApiClient
import com.messaging.scrtm.data.solana.remote.SolanaRemoteDataSource
import com.messaging.scrtm.data.solana.remote.SolanaRemoteDataSourceImp
import com.messaging.scrtm.data.solana.repository.SolanaRepository
import com.messaging.scrtm.data.solana.repository.SolanaRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SolanaModule {

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

            }
        return Retrofit.Builder()
            .baseUrl("https://api.testnet.solana.com/")
            .client(
                OkHttpClient.Builder()
                    // A zero value means no timeout at all.
                    // This is enforced because our client isn't able to send/receive a request body/response body always to/from the server within the defined timeout.
                    // This guarantees that whatever the size of the image being uploaded is, it won't get `SocketTimeoutException` unless the server itself has issues.
                    .writeTimeout(0, TimeUnit.SECONDS)
                    .readTimeout(0, TimeUnit.SECONDS)
                    .addInterceptor(InterceptorSolana())
                    .addInterceptor(loggingInterceptor)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideSolanaRemoteDataSource(apiClient: ApiClient) : SolanaRemoteDataSource = SolanaRemoteDataSourceImp(apiClient)

     @Provides
    @Singleton
    fun provideSolanaRepository(solanaRemoteDataSource: SolanaRemoteDataSource) : SolanaRepository = SolanaRepositoryImp(solanaRemoteDataSource)


}