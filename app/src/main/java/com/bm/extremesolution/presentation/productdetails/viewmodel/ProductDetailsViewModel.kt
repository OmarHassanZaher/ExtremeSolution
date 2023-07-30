package com.bm.extremesolution.presentation.productdetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bm.extremesolution.data.model.response.ProductDetailsResponse
import com.bm.extremesolution.domain.entity.Resource
import com.bm.extremesolution.domain.usecase.ProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel  @Inject constructor(
    private val mProductDetailsUseCase: ProductDetailsUseCase,
) : ViewModel() {
    private val productByIdLiveData: MutableLiveData<Resource<ProductDetailsResponse?>> = MutableLiveData()
    private val allProductDetailsLiveData: MutableLiveData<Resource<List<ProductDetailsResponse>?>> = MutableLiveData()
    init {
        getAllProductDetails()
    }
    fun getProductsID(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            productByIdLiveData.postValue(Resource.loading())
            val result = mProductDetailsUseCase.execute(id)
            productByIdLiveData.postValue(result)
        } catch (e: Exception) {
            productByIdLiveData.postValue(Resource.domainError(e))
        }
    }
  private  fun getAllProductDetails() = viewModelScope.launch(Dispatchers.IO) {
        try {
            allProductDetailsLiveData.postValue(Resource.loading())
            val result = mProductDetailsUseCase.getAllProductDetails()
            allProductDetailsLiveData.postValue(result)
        } catch (e: Exception) {
            allProductDetailsLiveData.postValue(Resource.domainError(e))
        }
    }

    fun getProductDetailsLiveData(): LiveData<Resource<ProductDetailsResponse?>> = productByIdLiveData
    fun getAllProductDetailsLiveData(): LiveData<Resource<List<ProductDetailsResponse>?>> = allProductDetailsLiveData
}