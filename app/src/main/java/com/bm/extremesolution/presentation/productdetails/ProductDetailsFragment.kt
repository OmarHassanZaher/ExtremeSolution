package com.bm.extremesolution.presentation.productdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bm.extremesolution.R
import com.bm.extremesolution.data.model.response.ProductDetailsResponse
import com.bm.extremesolution.data.model.response.ProductsByCategoryResponse
import com.bm.extremesolution.databinding.FragmentProductDetailsBinding
import com.bm.extremesolution.domain.entity.Resource
import com.bm.extremesolution.presentation.baseFragment.BaseFragment
import com.bm.extremesolution.presentation.cart.CartFragment
import com.bm.extremesolution.presentation.home.viewmodel.CategoriesViewModel
import com.bm.extremesolution.presentation.productdetails.viewmodel.ProductDetailsViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment() {
    private var binding: FragmentProductDetailsBinding? = null
    private lateinit var viewModel: ProductDetailsViewModel
    private var id: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = ProductDetailsFragmentArgs.fromBundle(requireArguments()).id
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        initViewModel()
        initListeners()
    }

    private fun initListeners() {
        binding!!.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding!!.addToCartButton.setOnClickListener {
            viewModel.getProductDetailsLiveData().value?.data?.let { product ->

            }
            val action = ProductDetailsFragmentDirections.actionProductDetailsFragmentToCartFragment(id!!)
            findNavController().navigate(action)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ProductDetailsViewModel::class.java]
        viewModel.getProductDetailsLiveData().observe(viewLifecycleOwner, productsIdObserver)
        viewModel.getProductsID(id!!)
    }

    private var productsIdObserver: Observer<Resource<ProductDetailsResponse?>> = Observer {
        when (it.status) {
            Resource.Status.LOADING -> {
            }

            Resource.Status.SUCCESS -> {
                binding!!.title.text = it.data?.title
                binding!!.description.text = it.data?.description
                binding!!.price.text ="Price = ${it.data?.price} $"
                binding!!.rate.text = "Count: ${it.data?.rating?.count} | Rate: ${it.data?.rating?.rate}"
                Glide.with(requireContext()).load(it.data?.image).into(binding!!.image)
            }

            Resource.Status.API_ERROR -> {
                handleError(it.error_msg.toString())
            }

            Resource.Status.DOMAIN_ERROR -> {
                handleError(it.throwable.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}