package br.com.anderson.marleyspooncodechallenge.persistence

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class RecipeDaoTest : BaseDaoTest() {

    @Test fun `test empty db`() {
        database.contentfulDao().allRecipes()
            .test()
            .assertValue { it.isEmpty() }
    }

    @Test fun `test insert and get recipes`() {

        database.contentfulDao().insertRecipe(RECIPE).blockingAwait()

        database.contentfulDao().allRecipes()
            .test()
            .assertValue { it.find { f -> f.id == "id" } != null }
    }

    @Test fun `test update and get recipes`() {
        database.contentfulDao().insertRecipe(RECIPE).blockingAwait()

        val newTilte = "new title"

        val updated = RECIPE.copy(title = newTilte)
        database.contentfulDao().updateRecipe(updated).blockingAwait()

        database.contentfulDao().allRecipes()
            .test()
            // assertValue asserts that there was only one emission of the user
            .assertValue { it.find { f -> f.title == newTilte } != null }
    }

    @Test fun `test insert and get recipe`() {

        database.contentfulDao().insertRecipe(RECIPE).blockingAwait()

        database.contentfulDao().getRecipe("id")
            .test()
            .assertValue { it == RECIPE }
    }

    companion object {
        private val RECIPE = Recipe(id = "id", description = "description", title = "title")
    }
}
