package br.com.anderson.marleyspooncodechallenge.di

import android.app.Application
import br.com.anderson.marleyspooncodechallenge.provider.ResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DataBaseModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideResource(app: Application): ResourceProvider {
        return ResourceProvider(app)
    }
}
