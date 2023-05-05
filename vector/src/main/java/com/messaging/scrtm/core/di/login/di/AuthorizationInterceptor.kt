package com.messaging.scrtm.core.di.login.di

import okhttp3.Interceptor
import okhttp3.Response


class AuthorizationInterceptor() : Interceptor {
    //region Interceptor
    override fun intercept(chain: Interceptor.Chain): Response {
        val key = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdGF0dXMiOiJBQ1RJVkUiLCJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLXVzZXItaWQiOiI3MTgiLCJ4LWhhc3VyYS1kZWZhdWx0LXJvbGUiOiJVU0VSIn0sImlhdCI6MTY4MzI2NzIyNH0.s9K1n92ZJSiIq2rgmx3BTxRY5oX0Zqydl98n_INdnz0"
        val header = String.format("Bearer %s", key)

        val request = chain.request()
            .newBuilder()
            .addHeader("x-hasura-admin-secret","l4NsKP3fan1E3j41")
            .addHeader("Authorization",header)
            .build()
        return chain.proceed(request)
    }
}
