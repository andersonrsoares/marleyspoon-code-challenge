package br.com.anderson.marleyspooncodechallenge.persistence

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule


open class BaseDaoTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    protected lateinit var database: ContentfulDb

    @Before fun createDb() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext<Context>(),
            ContentfulDb::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After fun closeDb() {
        database.close()
    }
}
