package br.com.anderson.marleyspooncodechallenge.extras

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

val AUTORIZATION = "7ac531648a1b5e1dab6c18b0979f822a5aad0fe5f1109829b8a197eb2be4b84c"

class AutorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url: HttpUrl = request.url.newBuilder().addQueryParameter("access_token", AUTORIZATION).build()
        return chain.proceed(request.newBuilder().url(url).build())
    }
}
