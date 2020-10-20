package br.com.anderson.marleyspooncodechallenge.di

import android.app.Application
import br.com.anderson.marleyspooncodechallenge.persistence.ContentfulDao
import br.com.anderson.marleyspooncodechallenge.provider.ResourceProvider
import br.com.anderson.marleyspooncodechallenge.repository.RecipeRepository
import br.com.anderson.marleyspooncodechallenge.repository.RecipeRepositoryImpl
import br.com.anderson.marleyspooncodechallenge.repository.RecipesRepository
import br.com.anderson.marleyspooncodechallenge.repository.RecipesRepositoryImpl
import br.com.anderson.marleyspooncodechallenge.service.ContentfulService
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

    @Singleton
    @Provides
    fun provideRecipeRepository(
        localDataSouse: ContentfulDao,
        remoteDataSource: ContentfulService
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            localDataSouse,
            remoteDataSource
        )
    }

    @Singleton
    @Provides
    fun provideRecipesRepository(
        localDataSouse: ContentfulDao,
        remoteDataSource: ContentfulService
    ): RecipesRepository {
        return RecipesRepositoryImpl(
            localDataSouse,
            remoteDataSource
        )
    }
}
