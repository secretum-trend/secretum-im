package im.vector.app.core.di.login.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import im.vector.app.core.di.login.domain.ApolloApolloLoginClientImp
import im.vector.app.core.di.login.domain.ApolloLoginClient
import im.vector.app.core.di.login.repository.LoginRepository
import im.vector.app.core.di.login.repository.LoginRepositoryImp
import im.vector.app.core.utils.Constants
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

}