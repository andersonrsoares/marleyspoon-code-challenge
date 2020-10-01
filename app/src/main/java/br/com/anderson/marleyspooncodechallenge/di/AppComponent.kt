package br.com.anderson.marleyspooncodechallenge.di

import android.app.Application
import br.com.anderson.marleyspooncodechallenge.MarleySpoonApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBuilder::class,
        AppModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(challengeApp: MarleySpoonApp)
}
