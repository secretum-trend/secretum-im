package com.messaging.scrtm.core.di.login.di

import okhttp3.Interceptor
import okhttp3.Response


class InterceptorSolana() : Interceptor {
    //region Interceptor
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}
