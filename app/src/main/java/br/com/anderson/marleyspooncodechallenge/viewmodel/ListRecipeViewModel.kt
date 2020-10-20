package br.com.anderson.marleyspooncodechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.anderson.marleyspooncodechallenge.model.DataSourceResult
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.repository.RecipesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ListRecipeViewModel @Inject constructor(val repository: RecipesRepository) : BaseViewModel() {

    private var _dataRecipes = MutableLiveData<List<Recipe>>()

    val dataRecipes: LiveData<List<Recipe>>
        get() = _dataRecipes

    fun listRecipes() {
        _loading.postValue(true)
        disposable.add(
            repository
                .getRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::subscrible, this::error, this::complete)
        )
    }

    private fun subscrible(result: DataSourceResult<List<Recipe>>) {
        when {
            result.body != null -> emitList(result.body)
            result.error != null -> error(result.error)
        }
        complete()
    }

    private fun emitList(result: List<Recipe>) {
        if (result.isNotEmpty()) {
            _dataRecipes.postValue(result)
        }
    }

    override fun refresh() {
        super.refresh()
        listRecipes()
    }
}
