package br.com.anderson.marleyspooncodechallenge.di

import android.app.Application
import androidx.room.Room
import br.com.anderson.marleyspooncodechallenge.persistence.ContentfulDao
import br.com.anderson.marleyspooncodechallenge.persistence.ContentfulDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

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
}
