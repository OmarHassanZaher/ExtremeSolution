package com.bm.extremesolution.presentation.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bm.extremesolution.data.model.response.SearchResponse
import com.bm.extremesolution.domain.entity.Resource
import com.bm.extremesolution.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel  @Inject constructor(
    private val mSearchUseCase: SearchUseCase,
) : ViewModel() {
    private val searchLiveData: MutableLiveData<Resource<SearchResponse?>> = MutableLiveData()

    fun getSearchKey(keyword: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            searchLiveData.postValue(Resource.loading())
            val result = mSearchUseCase.execute(keyword)
            searchLiveData.postValue(result)
        } catch (e: Exception) {
            searchLiveData.postValue(Resource.domainError(e))
        }
    }
    fun getSearchLiveData(): LiveData<Resource<SearchResponse?>> = searchLiveData
}