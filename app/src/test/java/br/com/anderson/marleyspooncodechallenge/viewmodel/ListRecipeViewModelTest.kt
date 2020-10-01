package br.com.anderson.marleyspooncodechallenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.anderson.marleyspooncodechallenge.mock
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import br.com.anderson.marleyspooncodechallenge.model.DataSourceResult
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.repository.RecipesRepository



@RunWith(JUnit4::class)
class ListRecipeViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val recipesRepository = mock<RecipesRepository>()

    private lateinit var  listRecipeViewModel: ListRecipeViewModel
    @Before
    fun init(){
        listRecipeViewModel = ListRecipeViewModel(recipesRepository)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `list recipes success`() {

        val repositoryResponse = listOf(Recipe(id = "id"))

        `when`(recipesRepository.getRecipes()).thenReturn(Flowable.just(DataSourceResult.create(repositoryResponse)))

        val observerData = mock<Observer<List<Recipe>>>()
        val observerLoading = mock<Observer<Boolean>>()

        listRecipeViewModel.loading.observeForever(observerLoading)
        listRecipeViewModel.dataRecipes.observeForever(observerData)
        listRecipeViewModel.listRecipes()
        verify(observerLoading).onChanged(true)
        verify(recipesRepository).getRecipes()
        verify(observerData).onChanged(repositoryResponse)
        verify(observerLoading, times(2)).onChanged(false)
    }

    @Test
    fun `list recipes data empty`() {

         val repositoryResponse = listOf<Recipe>()

        `when`(recipesRepository.getRecipes()).thenReturn(Flowable.just( DataSourceResult.create(repositoryResponse)))

        val observerData = mock<Observer<List<Recipe>>>()
        val observerLoading = mock<Observer<Boolean>>()

        listRecipeViewModel.loading.observeForever(observerLoading)
        listRecipeViewModel.dataRecipes.observeForever(observerData)
        listRecipeViewModel.listRecipes()
        verify(observerLoading).onChanged(true)
        verify(recipesRepository).getRecipes()
        verify(observerData, never()).onChanged(repositoryResponse)
        verify(observerLoading, times(2)).onChanged(false)
    }

}