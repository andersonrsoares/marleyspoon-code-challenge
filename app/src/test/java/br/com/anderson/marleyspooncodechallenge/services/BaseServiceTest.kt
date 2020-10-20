package br.com.anderson.marleyspooncodechallenge.services

import br.com.anderson.marleyspooncodechallenge.service.ContentfulService
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

open class BaseServiceTest {

    protected lateinit var service: ContentfulService

    protected lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ContentfulService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }
}
