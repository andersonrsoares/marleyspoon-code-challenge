package br.com.anderson.marleyspooncodechallenge.di

import br.com.anderson.marleyspooncodechallenge.ui.listrecipe.ListRecipeFragment
import br.com.anderson.marleyspooncodechallenge.ui.recipe.RecipeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeListRecipe(): ListRecipeFragment

    @ContributesAndroidInjector
    abstract fun contributeRecipeFragment(): RecipeFragment
}
