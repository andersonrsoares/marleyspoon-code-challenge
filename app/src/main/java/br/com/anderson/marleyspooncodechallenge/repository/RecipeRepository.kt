package br.com.anderson.marleyspooncodechallenge.repository

import br.com.anderson.marleyspooncodechallenge.dto.RecipeQueryDTO
import br.com.anderson.marleyspooncodechallenge.extras.transformToDataSourceResult
import br.com.anderson.marleyspooncodechallenge.model.DataSourceResult
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.persistence.ContentfulDao
import br.com.anderson.marleyspooncodechallenge.service.ContentfulService
import br.com.anderson.marleyspooncodechallenge.testing.OpenForTesting
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class RecipeRepository @Inject constructor(
    val localDataSouse: ContentfulDao,
    val remoteDataSource: ContentfulService
) {

    fun getRecipe(id: String): Flowable<DataSourceResult<Recipe>> {
        val localData = getRemoteRecipe(id)
        val remoteData = getLocalRecipe(id)

        return Flowable.merge(localData, remoteData).subscribeOn(Schedulers.io())
    }

    private fun getRemoteRecipe(id: String): Flowable<DataSourceResult<Recipe>> {
        return remoteDataSource.getRecipe(RecipeQueryDTO(id))
            .subscribeOn(Schedulers.io())
            .map {
                it.recipeRootDTO?.recipe?.toRecipe()!!
            }.doOnSuccess {
                localDataSouse.insertRecipe(it).subscribe()
            }.transformToDataSourceResult().toFlowable()
    }

    private fun getLocalRecipe(id: String): Flowable<DataSourceResult<Recipe>> {
        return localDataSouse.getRecipe(id)
            .subscribeOn(Schedulers.io())
            .transformToDataSourceResult()
            .toFlowable()
    }
}
