package br.com.anderson.marleyspooncodechallenge.services


import br.com.anderson.marleyspooncodechallenge.ApiUtil
import br.com.anderson.marleyspooncodechallenge.dto.RecipeQueryDTO
import br.com.anderson.marleyspooncodechallenge.dto.RecipesQueryDTO
import org.junit.*
import com.google.common.truth.Truth.assertThat
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ServiceRecipiesTest : BaseServiceTest() {

    @Test
    fun `test recipes response success full data`() {
        //GIVEN
        ApiUtil.enqueueResponse(mockWebServer,"recipes_success.json")

        val query = RecipesQueryDTO()
        val response = service.getRecipes(query).test()

        //when
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/")
        //THEN

        response.assertNoErrors()
        val recipesResult =  response.values().first()
        assertThat(recipesResult.recipeCollectionRootDTO).isNotNull()
        assertThat(recipesResult.recipeCollectionRootDTO?.recipeCollection).isNotNull()
        assertThat(recipesResult.recipeCollectionRootDTO?.recipeCollection).isNotNull()
        assertThat(recipesResult.recipeCollectionRootDTO?.recipeCollection?.items).isNotNull()
        assertThat(recipesResult.recipeCollectionRootDTO?.recipeCollection?.total).isEqualTo(4)
        assertThat(recipesResult.recipeCollectionRootDTO?.recipeCollection?.items).hasSize(4)

        val recipe = recipesResult.recipeCollectionRootDTO?.recipeCollection?.items?.first()!!

        assertThat(recipe.photo).isNotNull()
        assertThat(recipe.photo?.url).isEqualTo("https://images.ctfassets.net/kk2bw5ojx476/61XHcqOBFYAYCGsKugoMYK/0009ec560684b37f7f7abadd66680179/SKU1240_hero-374f8cece3c71f5fcdc939039e00fb96.jpg")
        assertThat(recipe.sys).isNotNull()
        assertThat(recipe.sys?.id).isEqualTo("4dT8tcb6ukGSIg2YyuGEOm")
        assertThat(recipe.title).isEqualTo("White Cheddar Grilled Cheese with Cherry Preserves & Basil")
    }

    @Test
    fun `test recipe response success full data`() {
        //GIVEN
        ApiUtil.enqueueResponse(mockWebServer,"recipe_success.json")

        val query = RecipeQueryDTO("id")
        val response = service.getRecipe(query).test()

        //when
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/")
        //THEN

        response.assertNoErrors()
        val recipesResult =  response.values().first()
        assertThat(recipesResult.recipeRootDTO).isNotNull()
        assertThat(recipesResult.recipeRootDTO?.recipe).isNotNull()

        val recipe = recipesResult.recipeRootDTO?.recipe!!

        assertThat(recipe.photo).isNotNull()
        assertThat(recipe.photo?.url).isEqualTo("https://images.ctfassets.net/kk2bw5ojx476/3TJp6aDAcMw6yMiE82Oy0K/2a4cde3c1c7e374166dcce1e900cb3c1/SKU1503_Hero_102__1_-6add52eb4eec83f785974ddeac3316b7.jpg")
        assertThat(recipe.sys).isNotNull()
        assertThat(recipe.sys?.id).isEqualTo("2E8bc3VcJmA8OgmQsageas")
        assertThat(recipe.title).isEqualTo("Grilled Steak & Vegetables with Cilantro-Jalapeño Dressing")
        assertThat(recipe.tagsCollection).isNotNull()
        assertThat(recipe.tagsCollection?.items).isNotEmpty()
        assertThat(recipe.tagsCollection?.items?.get(0)?.name).contains("gluten free")
        assertThat(recipe.description).contains("Warmer weather means")
        assertThat(recipe.chef).isNotNull()
        assertThat(recipe.chef?.name).contains("Mark Zucchiniberg")
    }


}