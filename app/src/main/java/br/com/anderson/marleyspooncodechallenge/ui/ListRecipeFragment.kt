package br.com.anderson.marleyspooncodechallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.anderson.marleyspooncodechallenge.R
import br.com.anderson.marleyspooncodechallenge.di.Injectable


class ListRecipeFragment : Fragment(R.layout.fragment_list_recipe), Injectable{


/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        initObservers()
        loadUsers()
        initSearch()

    }

    override fun onResume() {
        super.onResume()
        fetchUsers()
    }

    private fun initSearch(){
        searchview.setOnQueryTextListener(this)
    }

    private fun loadUsers(){
        viewModel.listLastUsers()
    }

    private fun initObservers(){
        observe(viewModel.dataListLastUsers,this::onLoadDataListUsers)
        observe(viewModel.message,this::onMessage)
        observe(viewModel.loading,this::onLoading)
        observe(viewModel.retry,this::onRetry)
    }

    private fun initRecycleView(){
        adapter = ListUserAdapter()
        adapter.itemOnClick = this::onItemClick
        recycleview.adapter = adapter
        recycleview.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        recycleview.setDivider(R.drawable.divider_recycleview)
    }

    private fun onItemClick(user:User){
        hideKeyboard()
        navController().navigate(ListUserFragmentDirections.actionListUserFragmentToUserDetailFragment(user.username))
    }

    private fun onLoadDataListUsers(data: List<User>) {
        adapter.submitList(data)
    }

    private fun fetchUsers(){
        viewModel.listLastUsers()
    }

    private fun onMessage(data: String) {
        Toast.makeText(requireContext(),data, Toast.LENGTH_LONG).show()
    }

    private fun onRetry(data: String) {
        Toast.makeText(requireContext(),data, Toast.LENGTH_LONG).show()
    }

    private fun onLoading(data: Boolean) {
        progressloading.isVisible = data
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.searchUser(query ?: "")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
       return true
    }*/

    /**
     * Created to be able to override in tests
     */
    fun navController() = findNavController()
}