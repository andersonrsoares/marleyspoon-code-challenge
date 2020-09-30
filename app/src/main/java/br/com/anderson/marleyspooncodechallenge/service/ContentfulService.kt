package br.com.anderson.marleyspooncodechallenge.service


import br.com.anderson.marleyspooncodechallenge.dto.RecipeCollencionResponseDTO
import br.com.anderson.marleyspooncodechallenge.dto.RecipeQueryDTO
import br.com.anderson.marleyspooncodechallenge.dto.RecipeResponseDTO
import br.com.anderson.marleyspooncodechallenge.dto.RecipesQueryDTO
import io.reactivex.Single

import retrofit2.http.*

/**
 * REST API access points
 */


interface  ContentfulService {

    @POST("/")
    fun getRecipes(@Body body: RecipesQueryDTO): Single<RecipeCollencionResponseDTO>

    @POST("/")
    fun getRecipe(@Body body: RecipeQueryDTO): Single<RecipeResponseDTO>
}
