package br.com.anderson.marleyspooncodechallenge.repository

import br.com.anderson.marleyspooncodechallenge.model.DataSourceResult
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import io.reactivex.Flowable

interface RecipeRepository {
    fun getRecipe(id: String): Flowable<DataSourceResult<Recipe>>
}
