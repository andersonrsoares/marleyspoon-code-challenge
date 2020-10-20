package br.com.anderson.marleyspooncodechallenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.anderson.marleyspooncodechallenge.MockJSONDataSource
import br.com.anderson.marleyspooncodechallenge.MockJSONDataSourceRule
import br.com.anderson.marleyspooncodechallenge.dto.RecipeResponseDTO
import br.com.anderson.marleyspooncodechallenge.mock
import br.com.anderson.marleyspooncodechallenge.model.DataSourceResult
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.repository.RecipesRepository
import br.com.anderson.marleyspooncodechallenge.ui.listrecipe.ListRecipeViewModel
import com.google.common.truth.Truth
import com.google.gson.Gson
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class ListRecipeViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val recipesRepository = mock<RecipesRepository>()

    private lateinit var listRecipeViewModel: ListRecipeViewModel

    @Rule
    @JvmField
    val mockJSONDataSourceRule = MockJSONDataSourceRule(Gson())

    @Before
    fun init() {
        listRecipeViewModel =
            ListRecipeViewModel(
                recipesRepository
            )
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    @MockJSONDataSource(fileName = "api-response/recipe_success.json", clazz = RecipeResponseDTO::class)
    fun `test parse`() {
        val recipe = mockJSONDataSourceRule.getValue<RecipeResponseDTO>()

        print(recipe)
        Truth.assertThat(recipe).isNotNull()
    }

    @Test
    fun `list recipes success`() {

        val repositoryResponse = listOf(Recipe(id = "id"))

        Mockito.`when`(recipesRepository.getRecipes()).thenReturn(Flowable.just(DataSourceResult.create(repositoryResponse)))

        val observerData = mock<Observer<List<Recipe>>>()
        val observerLoading = mock<Observer<Boolean>>()

        listRecipeViewModel.loading.observeForever(observerLoading)
        listRecipeViewModel.dataRecipes.observeForever(observerData)
        listRecipeViewModel.listRecipes()
        Mockito.verify(observerLoading).onChanged(true)
        Mockito.verify(recipesRepository).getRecipes()
        Mockito.verify(observerData).onChanged(repositoryResponse)
        Mockito.verify(observerLoading, Mockito.times(2)).onChanged(false)
    }

    @Test
    fun `list recipes data empty`() {

        val repositoryResponse = listOf<Recipe>()

        Mockito.`when`(recipesRepository.getRecipes()).thenReturn(Flowable.just(DataSourceResult.create(repositoryResponse)))

        val observerData = mock<Observer<List<Recipe>>>()
        val observerLoading = mock<Observer<Boolean>>()

        listRecipeViewModel.loading.observeForever(observerLoading)
        listRecipeViewModel.dataRecipes.observeForever(observerData)
        listRecipeViewModel.listRecipes()
        Mockito.verify(observerLoading).onChanged(true)
        Mockito.verify(recipesRepository).getRecipes()
        Mockito.verify(observerData, Mockito.never()).onChanged(repositoryResponse)
        Mockito.verify(observerLoading, Mockito.times(2)).onChanged(false)
    }
}
