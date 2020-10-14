package br.com.anderson.marleyspooncodechallenge.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import br.com.anderson.marleyspooncodechallenge.MarleySpoonApp
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber

/**
 * Helper class to automatically inject fragments if they implement [Injectable].
 */
object AppInjector {
    const val TAG = "AppInjector"
    fun init(marleySpoonApp: MarleySpoonApp) {
        DaggerAppComponent.builder().application(marleySpoonApp)
            .build().inject(marleySpoonApp)

        marleySpoonApp
            .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    handleActivity(activity)
                }

                override fun onActivityStarted(activity: Activity) {
                    Timber.d("$TAG - onActivityStarted")
                }

                override fun onActivityResumed(activity: Activity) {
                    Timber.d("$TAG - onActivityResumed")
                }

                override fun onActivityPaused(activity: Activity) {
                    Timber.d("$TAG - onActivityPaused")
                }

                override fun onActivityStopped(activity: Activity) {
                    Timber.d("$TAG - onActivityStopped")
                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                    Timber.d("$TAG - onActivitySaveInstanceState")
                }

                override fun onActivityDestroyed(activity: Activity) {
                    Timber.d("$TAG - onActivityDestroyed")
                }
            })
    }

    private fun handleActivity(activity: Any) {
        if (activity is FragmentActivity) {
            AndroidInjection.inject(activity)
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                            f.childFragmentManager
                                .registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
                        }
                    },
                    true
                )
        }
    }

    val fragmentLifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentCreated(
            fm: FragmentManager,
            f: Fragment,
            savedInstanceState: Bundle?
        ) {
            if (f is Injectable) {
                AndroidSupportInjection.inject(f)
            }
        }
    }
}
