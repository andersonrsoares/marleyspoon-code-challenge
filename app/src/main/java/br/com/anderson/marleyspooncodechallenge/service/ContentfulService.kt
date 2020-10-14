package br.com.anderson.marleyspooncodechallenge.service

import br.com.anderson.marleyspooncodechallenge.dto.RecipeCollencionResponseDTO
import br.com.anderson.marleyspooncodechallenge.dto.RecipeQueryDTO
import br.com.anderson.marleyspooncodechallenge.dto.RecipeResponseDTO
import br.com.anderson.marleyspooncodechallenge.dto.RecipesQueryDTO
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * REST API access points
 */

interface ContentfulService {

    @POST("/content/v1/spaces/kk2bw5ojx476")
    fun getRecipes(@Body body: RecipesQueryDTO): Single<RecipeCollencionResponseDTO>

    @POST("/content/v1/spaces/kk2bw5ojx476")
    fun getRecipe(@Body body: RecipeQueryDTO): Single<RecipeResponseDTO>
}
