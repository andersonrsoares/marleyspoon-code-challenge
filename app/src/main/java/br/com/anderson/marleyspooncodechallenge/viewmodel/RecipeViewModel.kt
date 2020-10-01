package br.com.anderson.marleyspooncodechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.anderson.marleyspooncodechallenge.R
import br.com.anderson.marleyspooncodechallenge.model.DataSourceResult
import br.com.anderson.marleyspooncodechallenge.model.ErrorResult
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.repository.RecipeRepository
import br.com.anderson.marleyspooncodechallenge.testing.OpenForTesting
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@OpenForTesting
class RecipeViewModel @Inject constructor(val repository: RecipeRepository) : BaseViewModel()  {

    private var _dataRecipe = MutableLiveData<Recipe>()

    val dataRecipe:LiveData<Recipe>
        get() = _dataRecipe


    fun fetchRecipe(id:String?){
        _loading.postValue(true)
        disposable.add(repository
            .getRecipe(id ?: "")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::subscrible,this::error,this::complete))
    }


    private fun subscrible(result:DataSourceResult<Recipe>){
         when{
             result.error is ErrorResult.NotFound-> _message.postValue(resourceProvider.getString(R.string.message_not_found))
             result.body != null  -> _dataRecipe.postValue(result.body)
         }
        complete()
    }



}