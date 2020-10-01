package br.com.anderson.marleyspooncodechallenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.anderson.marleyspooncodechallenge.mock
import br.com.anderson.marleyspooncodechallenge.model.DataSourceResult
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import br.com.anderson.marleyspooncodechallenge.repository.RecipeRepository
import org.mockito.Mockito


@RunWith(JUnit4::class)
class RecipeViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val recipeRepository = mock<RecipeRepository>()

    private lateinit var  recipeViewModel: RecipeViewModel
    @Before
    fun init(){
        recipeViewModel = RecipeViewModel(recipeRepository)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `get recipe success`() {

        val id = "id"

        val repositoryResponse = Recipe(id = "id")

        Mockito.`when`(recipeRepository.getRecipe(id)).thenReturn(Flowable.just( DataSourceResult.create(repositoryResponse)))

        val observerData = mock<Observer<Recipe>>()
        val observerLoading = mock<Observer<Boolean>>()

        recipeViewModel.loading.observeForever(observerLoading)
        recipeViewModel.dataRecipe.observeForever(observerData)
        recipeViewModel.fetchRecipe(id)
        Mockito.verify(observerLoading).onChanged(true)
        Mockito.verify(recipeRepository).getRecipe(id)
        Mockito.verify(observerData).onChanged(repositoryResponse)
        Mockito.verify(observerLoading, Mockito.times(2)).onChanged(false)
    }

    @Test
    fun `get recipe data empty`() {

        val id = "id"
        Mockito.`when`(recipeRepository.getRecipe(id)).thenReturn(Flowable.empty())

        val observerData = mock<Observer<Recipe>>()
        val observerLoading = mock<Observer<Boolean>>()

        recipeViewModel.loading.observeForever(observerLoading)
        recipeViewModel.dataRecipe.observeForever(observerData)
        recipeViewModel.fetchRecipe(id)
        Mockito.verify(observerLoading).onChanged(true)
        Mockito.verify(recipeRepository).getRecipe(id)
        Mockito.verify(observerData, Mockito.never()).onChanged(null)
        Mockito.verify(observerLoading).onChanged(false)
    }

}