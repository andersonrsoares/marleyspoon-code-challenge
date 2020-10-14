package br.com.anderson.marleyspooncodechallenge.di

import br.com.anderson.marleyspooncodechallenge.ui.ListRecipeFragment
import br.com.anderson.marleyspooncodechallenge.ui.RecipeFragment
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
