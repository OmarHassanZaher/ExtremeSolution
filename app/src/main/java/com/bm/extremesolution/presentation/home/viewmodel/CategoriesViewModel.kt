package com.bm.extremesolution.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bm.extremesolution.data.local.AppDao
import com.bm.extremesolution.data.model.response.CategoriesResponse
import com.bm.extremesolution.data.model.response.ProductsByCategoryResponse
import com.bm.extremesolution.domain.entity.Resource
import com.bm.extremesolution.domain.usecase.CategoriesUseCase
import com.bm.extremesolution.domain.usecase.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val mCategoriesUseCase: CategoriesUseCase,
    private val mProductsUseCase: ProductsUseCase,
    private val appDao: AppDao
) : ViewModel() {
    private val categoriesLiveData: MutableLiveData<Resource<CategoriesResponse?>> = MutableLiveData()
    private val productsLiveData: MutableLiveData<Resource<ProductsByCategoryResponse?>> = MutableLiveData()

    init {
        getCategories()
    }

    private fun getCategories() = viewModelScope.launch(Dispatchers.IO) {
        try {
            categoriesLiveData.postValue(Resource.loading())
            val result = mCategoriesUseCase.execute()
            categoriesLiveData.postValue(result)
        } catch (e: Exception) {
            categoriesLiveData.postValue(Resource.domainError(e))
        }
    }
    fun getProducts(category: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            productsLiveData.postValue(Resource.loading())
            val result = mProductsUseCase.execute(category)
            productsLiveData.postValue(result)
        } catch (e: Exception) {
            productsLiveData.postValue(Resource.domainError(e))
        }
    }
    fun getProductsLiveData(): LiveData<Resource<ProductsByCategoryResponse?>> = productsLiveData
    fun getCategoriesLiveData(): LiveData<Resource<CategoriesResponse?>> = categoriesLiveData
}