package br.com.anderson.marleyspooncodechallenge.ui


import android.os.Build
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import android.app.Application
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.assertion.ViewAssertions
import br.com.anderson.marleyspooncodechallenge.R
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.viewmodel.RecipeViewModel
import org.junit.Before
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = [Build.VERSION_CODES.P], application = Application::class,qualifiers = "w360dp-h880dp-xhdpi" )
class RecipeFragmentFragmentTest {

    lateinit var testviewModel: RecipeViewModel

    lateinit var factory:FragmentFactory

    @Before
    fun setup(){
        testviewModel = Mockito.mock(RecipeViewModel::class.java)
        factory = object : FragmentFactory(){
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                return RecipeFragment().apply {
                    this.viewModel = testviewModel
                }
            }
        }
    }


    @Test fun `test recipe ui`() {
        val liveDataRecipe = MutableLiveData<Recipe>()
        val loading = MutableLiveData<Boolean>()
        val message = MutableLiveData<String>()
        val retry = MutableLiveData<String>()
        Mockito.`when`(testviewModel.dataRecipe).thenReturn(liveDataRecipe)
        Mockito.`when`(testviewModel.loading).thenReturn(loading)
        Mockito.`when`(testviewModel.message).thenReturn(message)
        Mockito.`when`(testviewModel.retry).thenReturn(retry)


        liveDataRecipe.value = Recipe(  title = "some title", description = "description", id = "id")
        val  scenario = launchFragmentInContainer<RecipeFragment>(fragmentArgs = bundleOf("recipeId" to "id"  ), themeResId = R.style.AppTheme, factory = factory)

        scenario.onFragment {

        }

        onView(withText("description")).check(ViewAssertions.matches(isDisplayed()))

        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.moveToState(Lifecycle.State.DESTROYED)

    }




}
