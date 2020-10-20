package br.com.anderson.marleyspooncodechallenge.repository

import br.com.anderson.marleyspooncodechallenge.any
import br.com.anderson.marleyspooncodechallenge.dto.RecipeDTO
import br.com.anderson.marleyspooncodechallenge.dto.RecipeQueryDTO
import br.com.anderson.marleyspooncodechallenge.dto.RecipeResponseDTO
import br.com.anderson.marleyspooncodechallenge.dto.RecipeRootDTO
import br.com.anderson.marleyspooncodechallenge.dto.SysDTO
import br.com.anderson.marleyspooncodechallenge.model.DataSourceResult
import br.com.anderson.marleyspooncodechallenge.model.ErrorResult
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.persistence.ContentfulDao
import br.com.anderson.marleyspooncodechallenge.persistence.ContentfulDb
import br.com.anderson.marleyspooncodechallenge.service.ContentfulService
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class RecipeRepositoryTest {

    private val codeWarsService = Mockito.mock(ContentfulService::class.java)
    private val codeWarsDao = Mockito.mock(ContentfulDao::class.java)
    private lateinit var recipeRepository: RecipeRepository

    @Before
    fun setup() {
        val db = Mockito.mock(ContentfulDb::class.java)
        Mockito.`when`(db.contentfulDao()).thenReturn(codeWarsDao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()

        recipeRepository = RecipeRepositoryImpl(codeWarsDao, codeWarsService)
    }

    @Test
    fun `test get recipe empty database`() {

        val id = "id"
        val queryDTO = RecipeQueryDTO("id")

        Mockito.`when`(codeWarsDao.getRecipe(id)).thenReturn(Maybe.empty())
        val remoteData = RecipeResponseDTO(RecipeRootDTO(RecipeDTO(sys = SysDTO(id))))

        Mockito.`when`(codeWarsDao.insertRecipe(any())).thenReturn(Completable.complete())
        Mockito.`when`(codeWarsService.getRecipe(queryDTO)).thenReturn(Single.just(remoteData))

        val testSubscriber = recipeRepository.getRecipe(id).test()

        testSubscriber.awaitDone(1, TimeUnit.SECONDS)

        testSubscriber.assertNoErrors()
        testSubscriber.assertSubscribed()
        testSubscriber.assertComplete()
        testSubscriber.assertValues(DataSourceResult.create(remoteData.recipeRootDTO?.recipe?.toRecipe()!!))
    }

    @Test
    fun `test get recipe `() {

        val id = "id"
        val queryDTO = RecipeQueryDTO("id")

        val localData = Recipe(id = id)

        Mockito.`when`(codeWarsDao.getRecipe(id)).thenReturn(Maybe.just(localData))
        val remoteData = RecipeResponseDTO(RecipeRootDTO(RecipeDTO(sys = SysDTO(id))))

        Mockito.`when`(codeWarsDao.insertRecipe(any())).thenReturn(Completable.complete())
        Mockito.`when`(codeWarsService.getRecipe(queryDTO)).thenReturn(Single.just(remoteData))

        val testSubscriber = recipeRepository.getRecipe(id).test()

        testSubscriber.awaitDone(1, TimeUnit.SECONDS)

        testSubscriber.assertNoErrors()
        testSubscriber.assertSubscribed()
        testSubscriber.assertComplete()
        testSubscriber.assertValues(
            DataSourceResult.create(localData),
            DataSourceResult.create(remoteData.recipeRootDTO?.recipe?.toRecipe()!!)
        )
    }

    @Test
    fun `test get recipe remote error database empty`() {

        val id = "id"
        val queryDTO = RecipeQueryDTO("id")

        Mockito.`when`(codeWarsDao.getRecipe(id)).thenReturn(Maybe.empty())

        Mockito.`when`(codeWarsDao.insertRecipe(any())).thenReturn(Completable.complete())
        Mockito.`when`(codeWarsService.getRecipe(queryDTO)).thenReturn(
            Single.error(
                HttpException(
                    Response.error<Single<RecipeResponseDTO>>(500, "error".toResponseBody())
                )
            )
        )

        val testSubscriber = recipeRepository.getRecipe(id).test()

        testSubscriber.awaitDone(1, TimeUnit.SECONDS)

        testSubscriber.assertNoErrors()
        testSubscriber.assertSubscribed()
        testSubscriber.assertComplete()
        testSubscriber.assertValue {
            it.error is ErrorResult.GenericError
        }
    }
}
