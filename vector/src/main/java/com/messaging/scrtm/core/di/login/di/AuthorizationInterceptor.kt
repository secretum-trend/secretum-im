package com.messaging.scrtm.core.di.login.di

import com.messaging.scrtm.data.SessionPref
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthorizationInterceptor( val session : SessionPref) : Interceptor {
    //region Interceptor
    override fun intercept(chain: Interceptor.Chain): Response {
        val key = session.accessToken
        val header = String.format("Bearer %s", key)

        val request = chain.request().newBuilder()
//            .addHeader("x-hasura-admin-secret","l4NsKP3fan1E3j41")
        if (key.isNotEmpty()){
            request.addHeader("Authorization",header)
        }
        return chain.proceed(request.build())
    }
}
