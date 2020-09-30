package br.com.anderson.marleyspooncodechallenge.di

import android.app.Application
import androidx.room.Room
import br.com.anderson.marleyspooncodechallenge.BuildConfig
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

    val URL = "https://graphql.contentful.com/content/v1/spaces/kk2bw5ojx476?access_token=7ac531648a1b5e1dab6c18b0979f822a5aad0fe5f1109829b8a197eb2be4b84c"

    @Singleton
    @Provides
    fun provideService(gson: Gson,okHttpClient: OkHttpClient): ContentfulService {
        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
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
    fun okHttpClientProvider(): OkHttpClient{
        return OkHttpClient().newBuilder()
            .connectTimeout(6000, TimeUnit.MILLISECONDS)
            .readTimeout((1000 * 60).toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout((1000 * 60).toLong(), TimeUnit.MILLISECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    val logInterceptor = HttpLoggingInterceptor()
                    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    this.addInterceptor(logInterceptor)
                }
            }.build()
    }


    /*@Singleton
    @Provides
    fun provideCodeWarsDb(app: Application): CodeWarsDb {
        return Room
            .databaseBuilder(app, CodeWarsDb::class.java, "codewars.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCodeWarsDao(db: CodeWarsDb): CodeWarsDao {
        return db.codeWarsDao()
    }*/

    @Singleton
    @Provides
    fun provideResource(app: Application): ResourceProvider {
        return ResourceProvider(app)
    }


}
