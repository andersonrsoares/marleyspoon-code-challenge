package br.com.anderson.marleyspooncodechallenge.repository



import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.anderson.marleyspooncodechallenge.dto.*
import br.com.anderson.marleyspooncodechallenge.persistence.ContentfulDao
import br.com.anderson.marleyspooncodechallenge.persistence.ContentfulDb
import br.com.anderson.marleyspooncodechallenge.service.ContentfulService
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.TimeUnit
import br.com.anderson.marleyspooncodechallenge.any
import br.com.anderson.marleyspooncodechallenge.model.DataSourceResult
import br.com.anderson.marleyspooncodechallenge.model.ErrorResult
import br.com.anderson.marleyspooncodechallenge.model.Recipe


@RunWith(JUnit4::class)
class RecipesRepositoryTest {


    private val codeWarsService = Mockito.mock(ContentfulService::class.java)
    private val codeWarsDao = Mockito.mock(ContentfulDao::class.java)
    private lateinit var recipesRepository: RecipesRepository

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup(){
        val db = Mockito.mock(ContentfulDb::class.java)
        Mockito.`when`(db.contentfulDao()).thenReturn(codeWarsDao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()

        recipesRepository = RecipesRepository(codeWarsDao,codeWarsService)
    }



    @Test
    fun `test get recipes empty database`() {

        val id = "id"
        val queryDTO = RecipesQueryDTO()

        Mockito.`when`(codeWarsDao.allRecipes()).thenReturn(Single.just(arrayListOf()))
         val remoteData = RecipeCollencionResponseDTO(RecipeCollectionRootDTO(RecipeCollectionDTO(items = arrayListOf(RecipeDTO(sys = SysDTO(id))), total = 1 )) )

        Mockito.`when`(codeWarsDao.insertRecipe(any())).thenReturn(Completable.complete())
        Mockito.`when`(codeWarsService.getRecipes(queryDTO)).thenReturn(Single.just(remoteData))

        val testSubscriber = recipesRepository.getRecipes().test()

        testSubscriber.awaitDone(1, TimeUnit.SECONDS)

        testSubscriber.assertNoErrors()
        testSubscriber.assertSubscribed()
        testSubscriber.assertNotComplete()
        testSubscriber.assertValues(DataSourceResult.create(remoteData.recipeCollectionRootDTO?.recipeCollection?.toRecipes()!!))


    }

    @Test
    fun `test get recipes `() {
        val queryDTO = RecipesQueryDTO()
        val localData =  arrayListOf(Recipe(id = "id"))

        Mockito.`when`(codeWarsDao.allRecipes()).thenReturn(Single.just(localData))
        val remoteData = RecipeCollencionResponseDTO(RecipeCollectionRootDTO(RecipeCollectionDTO(items = arrayListOf(RecipeDTO(sys = SysDTO("id"))), total = 1 )) )

        Mockito.`when`(codeWarsDao.insertRecipe(any())).thenReturn(Completable.complete())
        Mockito.`when`(codeWarsService.getRecipes(queryDTO)).thenReturn(Single.just(remoteData))

        val testSubscriber = recipesRepository.getRecipes().test()

        testSubscriber.awaitDone(1, TimeUnit.SECONDS)

        testSubscriber.assertNoErrors()
        testSubscriber.assertSubscribed()
        testSubscriber.assertComplete()
        testSubscriber.assertValues(DataSourceResult.create(localData),
            DataSourceResult.create(remoteData.recipeCollectionRootDTO?.recipeCollection?.toRecipes()!!))

    }

    @Test
    fun `test get recipes error remote database empty`() {
        val queryDTO = RecipesQueryDTO()

        Mockito.`when`(codeWarsDao.allRecipes()).thenReturn(Single.just(arrayListOf()))


        Mockito.`when`(codeWarsDao.insertRecipe(any())).thenReturn(Completable.complete())
        Mockito.`when`(codeWarsService.getRecipes(queryDTO)).thenReturn(Single.error(
            HttpException(
                Response.error<Single<RecipeResponseDTO>>(500, "error".toResponseBody()))
        ))

        val testSubscriber =  recipesRepository.getRecipes().test()


        testSubscriber.awaitDone(1, TimeUnit.SECONDS)

        testSubscriber.assertNoErrors()
        testSubscriber.assertSubscribed()
        testSubscriber.assertNotComplete()
        testSubscriber.assertValue {
            it.error is ErrorResult.GenericError
        }
    }


}