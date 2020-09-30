package br.com.anderson.marleyspooncodechallenge.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.testing.OpenForTesting
import io.reactivex.Completable
import io.reactivex.Single

@Dao
@OpenForTesting
abstract class ContentfulDao {

    @Query("SELECT * from Recipe")
    abstract fun allRecipes(): Single<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRecipe(user: Recipe): Completable


}