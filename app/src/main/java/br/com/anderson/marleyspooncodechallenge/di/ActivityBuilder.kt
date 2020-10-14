package br.com.anderson.marleyspooncodechallenge.di

import br.com.anderson.marleyspooncodechallenge.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    internal abstract fun contributeMainActivity(): MainActivity
}
