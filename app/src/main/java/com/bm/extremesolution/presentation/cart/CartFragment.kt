package com.bm.extremesolution.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bm.extremesolution.data.model.body.Cart
import com.bm.extremesolution.data.model.response.ProductDetailsResponse
import com.bm.extremesolution.databinding.FragmentCartBinding
import com.bm.extremesolution.domain.entity.Resource
import com.bm.extremesolution.presentation.baseFragment.BaseFragment
import com.bm.extremesolution.presentation.cart.adapter.CartAdapter
import com.bm.extremesolution.presentation.productdetails.viewmodel.ProductDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : BaseFragment() {
    private var binding: FragmentCartBinding? = null
    private var productId: Int? = null
    private lateinit var viewModel: ProductDetailsViewModel
    private var cartList: MutableList<Cart> = mutableListOf()
    @Inject
    lateinit var adapter: CartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productId = CartFragmentArgs.fromBundle(requireArguments()).id
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        initViewModel()
        initRV()
    }

    private fun initRV() {
        binding?.cartRv?.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ProductDetailsViewModel::class.java]
        viewModel.getAllProductDetailsLiveData().observe(viewLifecycleOwner, productObserver)
        viewModel.getProductsID(productId!!)
    }

    private var productObserver: Observer<Resource<List<ProductDetailsResponse>?>> = Observer {
        when (it.status) {
            Resource.Status.LOADING -> {
            }

            Resource.Status.SUCCESS -> {
                cartList.clear()
                cartList.addAll(convertProductDetailsToCart(it.data as List<ProductDetailsResponse>))
                adapter.updateCartList(cartList)
                updateCartTotal()

            }

            Resource.Status.API_ERROR -> {
                handleError(it.error_msg.toString())
            }

            Resource.Status.DOMAIN_ERROR -> {
                handleError(it.throwable.toString())
            }
        }
    }
    fun addToCart(product: ProductDetailsResponse) {
        val existingItem = cartList.find { it.id == product.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            val newItem = Cart(product.id!!, product.title!!, product.price!!)
            cartList.add(newItem)
        }
        updateCartTotal()
    }
    private fun updateCartTotal() {
        var total = 0.0
        for (item in cartList) {
            total += item.price * item.quantity
        }
        binding!!.totalPrice.text = "Total Price: $total" // Update the total price TextView
    }
    private fun convertProductDetailsToCart(productDetailsList: List<ProductDetailsResponse>): MutableList<Cart> {
        val cartList = mutableListOf<Cart>()
        for (product in productDetailsList) {
            val cart = Cart(product.id!!, product.title!!, product.price!!)
            cart.quantity = 1 // Set the initial quantity to 1
            cartList.add(cart)
        }
        return cartList
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}