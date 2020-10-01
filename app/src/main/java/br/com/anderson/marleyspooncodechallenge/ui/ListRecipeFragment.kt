package br.com.anderson.marleyspooncodechallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.anderson.marleyspooncodechallenge.R
import br.com.anderson.marleyspooncodechallenge.adapter.ListRecipeAdapter
import br.com.anderson.marleyspooncodechallenge.di.Injectable
import br.com.anderson.marleyspooncodechallenge.extras.observe
import br.com.anderson.marleyspooncodechallenge.extras.setDivider
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import br.com.anderson.marleyspooncodechallenge.viewmodel.ListRecipeViewModel
import kotlinx.android.synthetic.main.fragment_list_recipe.*
import javax.inject.Inject


class ListRecipeFragment : Fragment(R.layout.fragment_list_recipe), Injectable{

    lateinit var adapter: ListRecipeAdapter
    @Inject
    lateinit var viewModel: ListRecipeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        initrRefresh()
        initRetryButton()
        initObservers()
        loadRecipes()

    }

    override fun onResume() {
        super.onResume()
        loadRecipes()
    }

    private fun loadRecipes(){
        viewModel.listRecipes()
    }

    private fun initObservers(){
        observe(viewModel.dataRecipes,this::onLoadDataListRecipies)
        observe(viewModel.message,this::onMessage)
        observe(viewModel.loading,this::onLoading)
        observe(viewModel.retry,this::onRetry)
        observe(viewModel.clean,this::onClean)
    }

    private fun initRecycleView(){
        adapter = ListRecipeAdapter()
        adapter.itemOnClick = this::onItemClick
        recycleview.adapter = adapter
        recycleview.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        recycleview.setDivider(R.drawable.divider_recycleview)
    }

    private fun onItemClick(recipe:Recipe){
        navController().navigate(ListRecipeFragmentDirections.actionListRecipeFragmentToRecipeFragment(recipe.id))
    }

    private fun onLoadDataListRecipies(data: List<Recipe>) {
        adapter.submitList(data)
    }


    private fun initRetryButton(){
        retrybutton.setOnClickListener(this::onRetryClick)
    }

    fun onRetryClick(view: View){
        viewModel.refresh()
        view.isVisible = false
    }

    private fun initrRefresh(){
        swiperefresh.setOnRefreshListener(this::onRefresh)
    }

    fun onRefresh(){
        viewModel.refresh()
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
        swiperefresh.isRefreshing = data
        progressbar.isVisible = data
    }

    private fun onClean(data: Boolean) {
        if(data)
           adapter.submitList(arrayListOf())
    }

    /**
     * Created to be able to override in tests
     */
    fun navController() = findNavController()
}