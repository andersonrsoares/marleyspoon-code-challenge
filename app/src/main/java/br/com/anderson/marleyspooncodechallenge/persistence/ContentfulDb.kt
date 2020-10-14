package br.com.anderson.marleyspooncodechallenge.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.anderson.marleyspooncodechallenge.model.Recipe

@Database(
    entities = [Recipe::class],
    version = 1,
    exportSchema = false
)

abstract class ContentfulDb : RoomDatabase() {
    abstract fun contentfulDao(): ContentfulDao
}
