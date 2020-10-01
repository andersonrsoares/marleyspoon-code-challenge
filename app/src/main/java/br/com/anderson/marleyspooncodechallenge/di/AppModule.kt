package br.com.anderson.marleyspooncodechallenge.di

import android.app.Application
import androidx.room.Room
import br.com.anderson.marleyspooncodechallenge.BuildConfig
import br.com.anderson.marleyspooncodechallenge.dto.Contentful
import br.com.anderson.marleyspooncodechallenge.extras.AutorizationInterceptor
import br.com.anderson.marleyspooncodechallenge.persistence.ContentfulDao
import br.com.anderson.marleyspooncodechallenge.persistence.ContentfulDb
import br.com.anderson.marleyspooncodechallenge.provider.ResourceProvider
import br.com.anderson.marleyspooncodechallenge.service.ContentfulService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    val URL = "https://graphql.contentful.com/"

    @Singleton
    @Provides
    fun provideService(okHttpClient: OkHttpClient): ContentfulService {
        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ContentfulService::class.java)
    }


    @Singleton
    @Provides
    fun okHttpClientCacheProvider(app: Application): Cache {
        return Cache(app.cacheDir, (5 * 1024 * 1024).toLong())
    }

    @Singleton
    @Provides
    fun okHttpClientProvider(autorizationInterceptor: AutorizationInterceptor): OkHttpClient{
        return OkHttpClient().newBuilder()
            .connectTimeout(6000, TimeUnit.MILLISECONDS)
            .readTimeout((1000 * 60).toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout((1000 * 60).toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(autorizationInterceptor)
            .apply {
                if (BuildConfig.DEBUG) {
                    val logInterceptor = HttpLoggingInterceptor()
                    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    this.addInterceptor(logInterceptor)
                }
            }.build()
    }

    @Singleton
    @Provides
    fun autorizationInterceptorProvider(): AutorizationInterceptor {
        return AutorizationInterceptor()
    }

    @Singleton
    @Provides
    fun provideContentfulDb(app: Application): ContentfulDb {
        return Room
            .databaseBuilder(app, ContentfulDb::class.java, "contentfulDb.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideContentfulDao(db: ContentfulDb): ContentfulDao {
        return db.contentfulDao()
    }

    @Singleton
    @Provides
    fun provideResource(app: Application): ResourceProvider {
        return ResourceProvider(app)
    }


}
