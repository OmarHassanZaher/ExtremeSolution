package com.bm.extremesolution.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bm.extremesolution.R
import com.bm.extremesolution.data.model.response.ProductsByCategoryResponse
import com.bm.extremesolution.data.model.response.SearchResponse
import com.bm.extremesolution.databinding.FragmentHomeBinding
import com.bm.extremesolution.databinding.FragmentSearchBinding
import com.bm.extremesolution.domain.entity.Resource
import com.bm.extremesolution.presentation.baseFragment.BaseFragment
import com.bm.extremesolution.presentation.home.viewmodel.CategoriesViewModel
import com.bm.extremesolution.presentation.search.adapter.SearchAdapter
import com.bm.extremesolution.presentation.search.adapter.SearchListener
import com.bm.extremesolution.presentation.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment() , SearchListener {
    private var binding: FragmentSearchBinding? = null
    private lateinit var viewModel: SearchViewModel
    @Inject
    lateinit var adapter : SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        initViewModel()
        initRV()
        initListeners()
    }

    private fun initListeners() {
        binding!!.searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getSearchKey(query ?: "")
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false

            }

        })
    }

    private fun initRV() {
        binding!!.searchList.adapter=adapter
        adapter.setSearchListener(this)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        viewModel.getSearchLiveData().observe(viewLifecycleOwner, productsObserver)
    }
    private var productsObserver: Observer<Resource<SearchResponse?>> = Observer {
        when (it.status) {
            Resource.Status.LOADING -> {
            }

            Resource.Status.SUCCESS -> {
                val productList = it.data
                if (productList != null) {
                    adapter.setData(productList as List<SearchResponse.productItem>)
                }
            }

            Resource.Status.API_ERROR -> {
                handleError(it.error_msg.toString())
            }

            Resource.Status.DOMAIN_ERROR -> {
                handleError(it.throwable.toString())
            }
        }
    }


    override fun onSearchClick(id: SearchResponse.productItem) {
        val action = SearchFragmentDirections.actionSearchFragmentToProductDetailsFragment(id.id!!)
        findNavController().navigate(action)

    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}