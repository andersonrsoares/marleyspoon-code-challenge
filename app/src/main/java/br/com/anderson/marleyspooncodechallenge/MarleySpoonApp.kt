package br.com.anderson.marleyspooncodechallenge

import android.app.Application
import br.com.anderson.marleyspooncodechallenge.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject
import  dagger.android.HasAndroidInjector

class MarleySpoonApp : Application(), HasAndroidInjector {

    @Inject lateinit var dispatchingAndroidInjector : DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}





