package br.com.anderson.marleyspooncodechallenge.di

import br.com.anderson.marleyspooncodechallenge.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainFragmentBuildersModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

}
