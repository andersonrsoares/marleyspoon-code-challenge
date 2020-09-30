package br.com.anderson.marleyspooncodechallenge.di

import br.com.anderson.marleyspooncodechallenge.ui.ListRecipeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeListRecipe(): ListRecipeFragment



}

