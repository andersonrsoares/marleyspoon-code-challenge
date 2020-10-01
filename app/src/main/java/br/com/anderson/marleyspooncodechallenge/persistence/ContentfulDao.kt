package br.com.anderson.marleyspooncodechallenge.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.testing.OpenForTesting
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
@OpenForTesting
abstract class ContentfulDao {

    @Query("SELECT * from Recipe")
    abstract fun allRecipes(): Single<List<Recipe>>

    @Query("SELECT * from Recipe where id == :id")
    abstract fun getRecipes(id:String): Maybe<Recipe>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertRecipe(user: Recipe): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateRecipe(user: Recipe): Completable

}