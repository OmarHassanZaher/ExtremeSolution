package com.bm.extremesolution.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bm.extremesolution.data.model.response.CategoriesResponse
import com.bm.extremesolution.data.model.response.ProductsByCategoryResponse
import com.bm.extremesolution.databinding.FragmentHomeBinding
import com.bm.extremesolution.domain.entity.Resource
import com.bm.extremesolution.presentation.baseFragment.BaseFragment
import com.bm.extremesolution.presentation.home.adapter.CategoriesAdapter
import com.bm.extremesolution.presentation.home.adapter.CategoriesListListener
import com.bm.extremesolution.presentation.home.adapter.ProductListListener
import com.bm.extremesolution.presentation.home.adapter.ProductsAdapter
import com.bm.extremesolution.presentation.home.viewmodel.CategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment(), CategoriesListListener ,ProductListListener{
    private var binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: CategoriesViewModel
    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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
        binding!!.searchBtn.setOnClickListener {
            val action =HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            findNavController().navigate(action)
        }
    }

    private fun initRV() {
        binding!!.categoriesRv.adapter = categoriesAdapter
        categoriesAdapter.setCategories(this)
        binding!!.productsRv.adapter = productsAdapter
        productsAdapter.setProducts(this)


    }

    private fun initViewModel() {
        homeViewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        homeViewModel.getCategoriesLiveData().observe(viewLifecycleOwner, categoriesObserver)
        homeViewModel.getProductsLiveData().observe(viewLifecycleOwner, productsObserver)


    }

    private var categoriesObserver: Observer<Resource<CategoriesResponse?>> = Observer {
        when (it.status) {
            Resource.Status.LOADING -> {
            }

            Resource.Status.SUCCESS -> {

                val categoriesList = it.data
                if (categoriesList != null) {
                    categoriesAdapter.setData(categoriesList)
                }

                homeViewModel.getProducts("electronics")

            }

            Resource.Status.API_ERROR -> {
                handleError(it.error_msg.toString())
            }

            Resource.Status.DOMAIN_ERROR -> {
                handleError(it.throwable.toString())
            }
        }
    }
    private var productsObserver: Observer<Resource<ProductsByCategoryResponse?>> = Observer {
        when (it.status) {
            Resource.Status.LOADING -> {
            }

            Resource.Status.SUCCESS -> {
                val productList = it.data
                if (productList != null) {
                    productsAdapter.setData(productList as List<ProductsByCategoryResponse.ProductsByCategoryResponseItem>)
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

    override fun onCategoriesListClick(category: String) {
        homeViewModel.getProducts(category)
    }

    override fun onProductListClick(id: ProductsByCategoryResponse.ProductsByCategoryResponseItem) {
       val action = HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(id.id!!)
        findNavController().navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}