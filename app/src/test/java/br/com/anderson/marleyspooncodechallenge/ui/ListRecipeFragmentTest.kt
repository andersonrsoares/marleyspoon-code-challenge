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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import br.com.anderson.marleyspooncodechallenge.R
import br.com.anderson.marleyspooncodechallenge.RecyclerViewMatcher
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.viewmodel.ListRecipeViewModel
import org.junit.Before
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = [Build.VERSION_CODES.P], application = Application::class,qualifiers = "w360dp-h880dp-xhdpi" )
class ListRecipeFragmentTest {

    lateinit var testviewModel: ListRecipeViewModel

    lateinit var factory:FragmentFactory

    val mockNavController = Mockito.mock(NavController::class.java)

    @Before
    fun setup(){
        testviewModel = Mockito.mock(ListRecipeViewModel::class.java)
        factory = object : FragmentFactory(){
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                return  ListRecipeFragment().apply {
                    this.viewModel = testviewModel
                }
            }
        }
    }


    @Test fun `test list recipe ui recycleview list click item`() {
        val liveDataListRecipes = MutableLiveData<List<Recipe>>()
        val loading = MutableLiveData<Boolean>()
        val message = MutableLiveData<String>()
        val retry = MutableLiveData<String>()
        val clean = MutableLiveData<Boolean>()
        Mockito.`when`(testviewModel.dataRecipes).thenReturn(liveDataListRecipes)
        Mockito.`when`(testviewModel.loading).thenReturn(loading)
        Mockito.`when`(testviewModel.message).thenReturn(message)
        Mockito.`when`(testviewModel.retry).thenReturn(retry)
        Mockito.`when`(testviewModel.clean).thenReturn(clean)


        liveDataListRecipes.value = arrayListOf(Recipe(title = "some title", id = "id1"))
        val  scenario = launchFragmentInContainer<ListRecipeFragment>(themeResId = R.style.AppTheme, factory = factory)

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), mockNavController)
        }

        onView(listMatcher().atPosition(0)).check(ViewAssertions.matches(isDisplayed()))
        onView(listMatcher().atPosition(0)).check(ViewAssertions.matches(hasDescendant(withText("some title"))))
        onView(withText("some title")).perform(ViewActions.click())

        Mockito.verify(mockNavController).navigate(ListRecipeFragmentDirections.actionListRecipeFragmentToRecipeFragment("id1"))

        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.moveToState(Lifecycle.State.DESTROYED)

    }



    private fun listMatcher(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.recycleview)
    }



}
