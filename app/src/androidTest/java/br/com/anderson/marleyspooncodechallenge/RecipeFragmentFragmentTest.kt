package br.com.anderson.marleyspooncodechallenge

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.ui.recipe.RecipeFragment
import br.com.anderson.marleyspooncodechallenge.ui.recipe.RecipeViewModel
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
@SmallTest
class RecipeFragmentFragmentTest {

    lateinit var testviewModel: RecipeViewModel

    lateinit var factory: FragmentFactory

    @Before
    fun setup() {
        testviewModel = Mockito.mock(RecipeViewModel::class.java)
        factory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                return RecipeFragment()
                    .apply {
                        this.factory = ViewModelUtil.createFor(testviewModel)
                    }
            }
        }
    }

    @Test fun `test_recipe_ui_all_data`() {
        val liveDataRecipe = MutableLiveData<Recipe>()
        val loading = MutableLiveData<Boolean>()
        val message = MutableLiveData<String>()
        val retry = MutableLiveData<String>()
        Mockito.`when`(testviewModel.dataRecipe).thenReturn(liveDataRecipe)
        Mockito.`when`(testviewModel.loading).thenReturn(loading)
        Mockito.`when`(testviewModel.message).thenReturn(message)
        Mockito.`when`(testviewModel.retry).thenReturn(retry)

        val scenario = launchFragmentInContainer<RecipeFragment>(fragmentArgs = bundleOf("recipeId" to "id"), themeResId = R.style.AppTheme, factory = factory)

        scenario.onFragment {
            liveDataRecipe.value = Recipe(title = "some title", description = "description", tags = arrayListOf("tag 1"), chefName = "chef", id = "id")
        }

        onView(ViewMatchers.withText("description")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText("some title")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText("Tags: tag 1")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText("Chef: chef")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test fun `test_recipe_ui_chef_null`() {
        val liveDataRecipe = MutableLiveData<Recipe>()
        val loading = MutableLiveData<Boolean>()
        val message = MutableLiveData<String>()
        val retry = MutableLiveData<String>()
        Mockito.`when`(testviewModel.dataRecipe).thenReturn(liveDataRecipe)
        Mockito.`when`(testviewModel.loading).thenReturn(loading)
        Mockito.`when`(testviewModel.message).thenReturn(message)
        Mockito.`when`(testviewModel.retry).thenReturn(retry)

        val scenario = launchFragmentInContainer<RecipeFragment>(fragmentArgs = bundleOf("recipeId" to "id"), themeResId = R.style.AppTheme, factory = factory)

        scenario.onFragment {
            liveDataRecipe.value = Recipe(title = "some title", description = "description", tags = null, chefName = "chef", id = "id")
        }

        onView(ViewMatchers.withText("description")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText("some title")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.tags)).check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        onView(ViewMatchers.withText("Chef: chef")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test fun `test_recipe_ui_tags_empty`() {
        val liveDataRecipe = MutableLiveData<Recipe>()
        val loading = MutableLiveData<Boolean>()
        val message = MutableLiveData<String>()
        val retry = MutableLiveData<String>()
        Mockito.`when`(testviewModel.dataRecipe).thenReturn(liveDataRecipe)
        Mockito.`when`(testviewModel.loading).thenReturn(loading)
        Mockito.`when`(testviewModel.message).thenReturn(message)
        Mockito.`when`(testviewModel.retry).thenReturn(retry)

        val scenario = launchFragmentInContainer<RecipeFragment>(fragmentArgs = bundleOf("recipeId" to "id"), themeResId = R.style.AppTheme, factory = factory)

        scenario.onFragment {
            liveDataRecipe.value = Recipe(title = "some title", description = "description", tags = arrayListOf("tag 1"), chefName = null, id = "id")
        }

        onView(ViewMatchers.withText("description")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText("some title")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText("Tags: tag 1")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.chef)).check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))

        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.moveToState(Lifecycle.State.DESTROYED)
    }
}
