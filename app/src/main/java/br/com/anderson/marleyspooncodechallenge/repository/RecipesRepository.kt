package br.com.anderson.marleyspooncodechallenge.repository

import br.com.anderson.marleyspooncodechallenge.dto.RecipesQueryDTO
import br.com.anderson.marleyspooncodechallenge.extras.transformToDataSourceResult
import br.com.anderson.marleyspooncodechallenge.model.DataSourceResult
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.persistence.ContentfulDao
import br.com.anderson.marleyspooncodechallenge.service.ContentfulService
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipesRepository @Inject constructor(
    val localDataSouse: ContentfulDao,
    val remoteDataSource: ContentfulService
) {

    fun getRecipes(): Flowable<DataSourceResult<List<Recipe>>> {
        val localData = getLocalRecipe()
        val remoteData = getRemoteRecipes()

        return Flowable.merge(localData, remoteData).subscribeOn(Schedulers.io())
    }

    private fun getRemoteRecipes(): Flowable<DataSourceResult<List<Recipe>>> {
        return remoteDataSource.getRecipes(RecipesQueryDTO())
            .subscribeOn(Schedulers.io())
            .map {
                it.recipeCollectionRootDTO?.recipeCollection?.toRecipes() ?: arrayListOf()
            }.doOnSuccess {
                it.forEach { recipe ->
                    localDataSouse.insertRecipe(recipe).subscribe()
                }
            }.transformToDataSourceResult().toFlowable()
    }

    private fun getLocalRecipe(): Flowable<DataSourceResult<List<Recipe>>> {
        return localDataSouse.allRecipes()
            .subscribeOn(Schedulers.io())
            .flatMap {
                if (it.isNotEmpty()) {
                    Single.just(it)
                } else {
                    Single.never()
                }
            }
            .transformToDataSourceResult()
            .toFlowable()
    }
}
