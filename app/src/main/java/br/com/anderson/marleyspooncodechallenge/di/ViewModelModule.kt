
package br.com.anderson.marleyspooncodechallenge.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.anderson.marleyspooncodechallenge.viewmodel.ListRecipeViewModel
import br.com.anderson.marleyspooncodechallenge.viewmodel.RecipeViewModel
import br.com.anderson.marleyspooncodechallenge.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ListRecipeViewModel::class)
    abstract fun bindListRecipeViewModel(viewModel: ListRecipeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecipeViewModel::class)
    abstract fun bindRecipeViewModel(viewModel: RecipeViewModel): ViewModel
}
