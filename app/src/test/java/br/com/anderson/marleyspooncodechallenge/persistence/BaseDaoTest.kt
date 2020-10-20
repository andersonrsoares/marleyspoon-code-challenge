package br.com.anderson.marleyspooncodechallenge.persistence

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before

open class BaseDaoTest {

    protected lateinit var database: ContentfulDb

    @Before fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            ContentfulDb::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After fun closeDb() {
        database.close()
    }
}
