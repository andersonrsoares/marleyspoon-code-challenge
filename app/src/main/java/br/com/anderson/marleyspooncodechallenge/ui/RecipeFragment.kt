package br.com.anderson.marleyspooncodechallenge.ui

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.anderson.marleyspooncodechallenge.R
import br.com.anderson.marleyspooncodechallenge.di.Injectable
import br.com.anderson.marleyspooncodechallenge.extras.observe
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.viewmodel.RecipeViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_recipe.*
import javax.inject.Inject


class RecipeFragment : Fragment(R.layout.fragment_recipe), Injectable{

    @Inject
    lateinit var viewModel: RecipeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRetryButton()
        initObservers()
        loadRecipe()
    }

    private fun loadRecipe(){
        viewModel.fetchRecipe(recipeId())
    }

    private fun initObservers(){
        observe(viewModel.dataRecipe,this::onLoadDataRecipe)
        observe(viewModel.message,this::onMessage)
        observe(viewModel.loading,this::onLoading)
        observe(viewModel.retry,this::onRetry)
    }


    private fun onLoadDataRecipe(data: Recipe) {
        Glide.with(requireContext()).load(data.photo).apply(
            RequestOptions()
                .placeholder(R.drawable.image_placeholder)
                .centerCrop()).into(imageview)

        title.text = data.title
        description.text = data.description
        chef.isGone = data.chefName?.let {
            chef.text =  resources.getString(R.string.label_chef,it)
        }  == null

        tags.isGone = data.tags?.takeIf { it.isNotEmpty() }?.let {
            tags.text =  resources.getString(R.string.label_tags,it.joinToString(", "))
        } == null
    }

    private fun recipeId() = RecipeFragmentArgs.fromBundle(requireArguments()).recipeId

    private fun initRetryButton(){
        retrybutton.setOnClickListener(this::onRetryClick)
    }

    fun onRetryClick(view: View){
        viewModel.refresh()
        view.isVisible = false
    }

    private fun onMessage(data: String) {
        message.text = data
    }

    private fun onRetry(data: String) {
        message.text = data
        retrybutton.isVisible = true
    }

    private fun cleanMessages(){
        retrybutton.isVisible = false
        message.text = ""
    }

    private fun onLoading(data: Boolean) {
        if(data){
            cleanMessages()
        }
        progressbar.isVisible = data
    }


    /**
     * Created to be able to override in tests
     */
    fun navController() = findNavController()
}